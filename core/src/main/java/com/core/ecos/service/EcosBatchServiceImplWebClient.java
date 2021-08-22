package com.core.ecos.service;

import com.core.common.properties.StaticProperties;
import com.core.ecos.domain.EcosSchema;
import com.core.ecos.domain.EcosSchemaDetail;
import com.core.ecos.dto.EcosDto;
import com.core.ecos.domain.EcosData;
import com.core.ecos.dto.EcosEnumType.SearchFlag;
import com.core.ecos.repo.EcosDataRepo;
import com.core.ecos.repo.EcosSchemaDetailRepo;
import com.core.ecos.repo.EcosSchemaRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EcosBatchServiceImplWebClient implements EcosBatchService {

    @Autowired    Gson gson;

    @Autowired    EcosDataRepo ecosDataRepo;
    @Autowired    EcosSchemaRepo ecosSchemaRepo;
    @Autowired    EcosSchemaDetailRepo ecosSchemaDetailRepo;
    @Autowired    StaticProperties staticProperties;

    @Autowired    private WebClient webClient;


    public JsonObject getJsonFromAPI(EcosDto ecosDto) {
            ecosDto.setAuthKey(staticProperties.getECOS_API_KEY());

        String response = webClient.get()
            .uri(staticProperties.getECOS_API_URL() + getUrlString(ecosDto))
            .retrieve()
            .bodyToMono(String.class)
            .block();
        return gson.fromJson(response, JsonObject.class);
    }
 
    @Transactional
    public List<EcosData> getDataFromAPI(EcosDto ecosDto) {
        ecosDto.setServiceName("StatisticSearch");

        JsonObject jsonObject = getJsonFromAPI(ecosDto);
        jsonObject =  jsonObject.get("statisticTableList").getAsJsonObject();
        JsonElement jsonElement = jsonObject.get("row").getAsJsonObject();

        Type listType = new TypeToken<List<EcosData>>() {}.getType();
        List<EcosData> ecosData = gson.fromJson(jsonElement, listType);

//        List<EcosMongoData> ecosMongoData = ecosData.stream().map(data -> (EcosMongoData) data)
//            .collect(Collectors.toList());

//        ecosDataMongoRepo.saveAll(ecosMongoData);
        return ecosDataRepo.saveAll(ecosData);
    }

    @Override
    @Transactional
    public List<EcosSchema> retrieveSchema() {
        EcosDto ecosDto = new EcosDto();
        ecosDto.setServiceName("StatisticTableList");

        JsonObject jsonObject = getJsonFromAPI(ecosDto);
        JsonArray jsonArray =  jsonObject.get("StatisticTableList").getAsJsonObject().get("row").getAsJsonArray();

        Type listType = new TypeToken<List<EcosSchema>>() {}.getType();
        List<EcosSchema> ecosSchemas = gson.fromJson(jsonArray, listType);

        ecosSchemaRepo.deleteAll();;
        ecosSchemas.forEach(ecosSchema -> {
            ecosSchemaRepo.save(ecosSchema);
        });

//        List<EcosMongoSchema> ecosMongoSchemas = ecosSchemas.stream().map(krBankSchema ->
//            new EcosMongoSchema(krBankSchema)
//        ).collect(Collectors.toList());
//        ecosSchemaMongoRepo.saveAll(ecosMongoSchemas);

        return ecosSchemas;
    }

    @Override
    @Transactional
    public List<EcosSchemaDetail> retrieveSchemaDetail() {
        EcosDto ecosDto = new EcosDto();
        Type listType = new TypeToken<List<EcosSchemaDetail>>() {}.getType();
        int totalCount =0;
        ecosDto.setServiceName("StatisticItemList");

        List<EcosSchemaDetail> ecosSchemaDetails = new ArrayList<>();
        List<EcosSchema> ecosSchemas = ecosSchemaRepo.findBySearchFlag( SearchFlag.Y);

        ecosSchemas.forEach(ecosSchema -> {
            ecosDto.setStatisticCode(ecosSchema.getStatcode());

            //삭제하고
            ecosSchemaDetailRepo.deleteByPitemcode(ecosSchema.getStatcode());

            JsonObject jsonObject = getJsonFromAPI(ecosDto);
            jsonObject.get(ecosDto.getServiceName()).getAsJsonObject().get("list_total_count").getAsInt();

            JsonArray jsonArray =  jsonObject.get(ecosDto.getServiceName()).getAsJsonObject().get("row").getAsJsonArray();

            List<EcosSchemaDetail> details = gson.fromJson(jsonArray, listType);
            //새로운거 저장
            ecosSchemaDetails.addAll(details);
        });
        ecosSchemaDetailRepo.saveAll(ecosSchemaDetails);
//        List<EcosMongoSchema> ecosMongoSchemas = ecosSchemas.stream().map(krBankSchema ->
//            new EcosMongoSchema(krBankSchema)
//        ).collect(Collectors.toList());

        return ecosSchemaDetails;
    }

    @Override
    @Transactional
    public List<EcosData> retrieveData(EcosDto ecosDto) {
        List<EcosData> ecosData = new ArrayList<>();

        List<EcosSchemaDetail> ecosSchemaDetails = ecosSchemaDetailRepo.findByCycle(ecosDto.getCycleType().toString());
        ecosSchemaDetails.forEach(i -> {
            EcosDto ecosDto1 = new EcosDto(i);
            ecosDto1.setQueryStartDate(ecosDto.getQueryStartDate());
            ecosDto1.setQueryEndDate(ecosDto.getQueryEndDate());
            ecosData.addAll(getDataFromAPI(ecosDto1));
        });
        List<EcosSchema> ecosSchemas = ecosSchemaRepo.findByCycleAndSearchFlag(ecosDto.getCycleType(), SearchFlag.Y);
        ecosSchemas.forEach(i -> {
            EcosDto ecosDto1 = new EcosDto(i);
            ecosDto1.setQueryStartDate(ecosDto.getQueryStartDate());
            ecosDto1.setQueryEndDate(ecosDto.getQueryEndDate());
            ecosData.addAll(getDataFromAPI(ecosDto1));
        });

        return ecosData;
    }
    public URI getUrlString(EcosDto ecosDto) {
        String uriString =
            "/api/{serviceName}/{authKey}/{requestType}/{language}/{reqStartCount}/{reqEndCount}" +
                "/{statisticCode}/{period}/{queryStartDate}/{queryEndDate}/{option1}";
        return UriComponentsBuilder.fromUriString(uriString)
            .buildAndExpand(new ObjectMapper().convertValue(ecosDto, Map.class))
            .toUri();
    }
}
