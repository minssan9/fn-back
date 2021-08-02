package com.core.ecos.apiservice;

import com.core.config.properties.CoreProperties;
import com.core.ecos.document.EcosMongoData;
import com.core.ecos.document.EcosMongoSchema;
import com.core.ecos.domain.EcosData;
import com.core.ecos.domain.EcosSchema;
import com.core.ecos.domain.EcosSchemaDetail;
import com.core.ecos.dto.EcosDto;
import com.core.ecos.dto.EcosEnumType;
import com.core.ecos.dto.EcosResponse;
import com.core.ecos.mongorepo.EcosDataMongoRepo;
import com.core.ecos.mongorepo.EcosSchemaMongoRepo;
import com.core.ecos.repo.EcosDataRepo;
import com.core.ecos.repo.EcosSchemaDetailRepo;
import com.core.ecos.repo.EcosSchemaRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class EcosApiServiceImplREST implements EcosApiService {
    @Autowired    Gson gson;
    @Autowired    RestTemplate restTemplate;
    @Autowired    CoreProperties coreProperties;

    @Autowired    EcosDataMongoRepo ecosDataMongoRepo;
    @Autowired    EcosSchemaMongoRepo ecosSchemaMongoRepo;

    @Autowired    EcosDataRepo ecosDataRepo;
    @Autowired    EcosSchemaRepo ecosSchemaRepo;
    @Autowired    EcosSchemaDetailRepo ecosSchemaDetailRepo;

    @Override
    public JsonObject getJsonFromAPI(EcosDto ecosDto) {
        ecosDto.setAuthKey(coreProperties.getECOS_API_KEY());
        String response = restTemplate.getForEntity(getUrlString(ecosDto), String.class).getBody();
        return gson.fromJson(response, JsonObject.class);
    }

    @Override
    public List<EcosData> getDataFromAPI(EcosDto ecosDto) {
        return null;
    }

    public List<EcosData> retrieveData(EcosDto ecosDto) {
        ecosDto.setServiceName("StatisticSearch");

        EcosResponse ecosDataResponse = gson.fromJson(getJsonFromAPI(ecosDto), EcosResponse.class);
        List<EcosData> ecosDataList = (List<EcosData>) gson.fromJson(ecosDataResponse.getStatisticTableList().getRow().toString(), EcosData.class);
        List<EcosMongoData> ecosMongoData = ecosDataList.stream().map(krBankData -> (EcosMongoData)krBankData).collect(Collectors.toList());

//        ecosDataMongoRepo.saveAll(ecosMongoData);
        return ecosDataRepo.saveAll(ecosDataList);
    }

    public List<EcosData> getDataFromAPI( ) {
        return null;
    }

    public List<EcosSchema> retrieveSchema() {
        EcosDto ecosDto = new EcosDto();
        ecosDto.setServiceName("StatisticTableList");

        EcosResponse ecosSchemaResponse = gson.fromJson(getJsonFromAPI(ecosDto), EcosResponse.class);
        List<EcosSchema> ecosSchemas = (List<EcosSchema>) gson.fromJson(
            ecosSchemaResponse.getStatisticTableList().getRow().toString(), EcosSchema.class);

        List<EcosMongoSchema> ecosMongoSchemas =
            ecosSchemas.stream()
                .map(krBankSchema -> new EcosMongoSchema(krBankSchema))
                .collect(Collectors.toList());

        ecosSchemaMongoRepo.saveAll(ecosMongoSchemas);
        return ecosSchemaRepo.saveAll(ecosSchemas);
    }


    @Override
    public List<EcosSchemaDetail> retrieveSchemaDetail() {
        List<EcosSchema> ecosSchemas = ecosSchemaRepo.findBySearchFlag(EcosEnumType.SearchFlag.Y);
        EcosDto ecosDto = new EcosDto();
        ecosDto.setServiceName("StatisticItemList");

        ResponseEntity<String> response = restTemplate.getForEntity(getUrlString(ecosDto), String.class);
        EcosResponse ecosSchemaResponse = restTemplate.getForObject(getUrlString(ecosDto), EcosResponse.class);
        List<EcosSchemaDetail> ecosSchemasDetails  = (List<EcosSchemaDetail>)gson.fromJson(
            ecosSchemaResponse.getStatisticTableList().getRow().toString(), EcosSchemaDetail.class);
//        List<EcosSchemaDetail> ecosSchemasDetails = gson.fromJson(response.getBody(), EcosResponse.class).getStatisticTableList().getRow();

//        List<EcosMongoSchema> ecosMongoSchemas = ecosSchemas.stream().map(krBankSchema ->
//            new EcosMongoSchema(krBankSchema)
//        ).collect(Collectors.toList());

//        ecosSchemaMongoRepo.saveAll(ecosMongoSchemas);
        return ecosSchemaDetailRepo.saveAll(ecosSchemasDetails);
    }



    @Transactional
    public List<EcosSchemaDetail> retrieveDataFromAllSchema(String startDate, String endDate) {
        List<EcosSchemaDetail> ecosSchemaDetails = ecosSchemaDetailRepo.findAll();

        ecosSchemaDetails.forEach(i-> {
            EcosDto ecosDto = new EcosDto(i);
            ecosDto.setQueryStartDate(startDate);
            ecosDto.setQueryEndDate(endDate);
            retrieveData(ecosDto);
        });
        return ecosSchemaDetails;
    }


    //    http://ecos.bok.or.kr/api/StatisticTableList/sample/xml/kr/1/10/
    //    http://ecos.bok.or.kr/api/StatisticSearch/sample/xml/kr/1/10/010Y002/MM/201101/201101/AAAA11/
    public URI getUrlString(EcosDto ecosDto) {
        ecosDto.setAuthKey(coreProperties.getECOS_API_KEY());
        String uriString =
                ecosDto.getUrl() + "/{serviceName}/{authKey}/{requestType}/{language}/{reqStartCount}/{reqEndCount}" +
                "/{statisticCode}/{period}/{queryStartDate}/{queryEndDate}/{option1}";
        return UriComponentsBuilder.fromUriString(uriString)
                .buildAndExpand(new ObjectMapper().convertValue(ecosDto, Map.class))
                .toUri();
    }
}
