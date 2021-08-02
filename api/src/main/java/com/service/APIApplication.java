package com.service;

import com.core.ecos.apiservice.EcosApiService;
import com.core.ecos.domain.EcosSchemaDetail;
import com.core.ecos.dto.EcosDto;
import com.core.ecos.dto.EcosEnumType.CycleType;
import com.core.ecos.repo.EcosSchemaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.util.List;

import static com.core.config.properties.CoreProperties.DATE_STRING_FORMAT;


@SpringBootApplication
@ComponentScan(basePackages = {  "com.core", "com.service"})
//@CrossOrigin(origins = {"*", "http://localhost"})
public class APIApplication implements ApplicationRunner {


    @Autowired
    EcosSchemaRepo ecosSchemaRepo;
    @Autowired
    EcosApiService ecosApiService;

    public static void main(String[] args) {
        SpringApplication.run(APIApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String nowDate = LocalDateTime.now().format(DATE_STRING_FORMAT);
//        ecosApiService.retrieveDataEachSchema(nowDate, nowDate);
//        List<EcosSchema> ecosSchemaList = ecosApiService.retrieveSchema();
        List<EcosSchemaDetail> ecosSchemaDetails = ecosApiService.retrieveSchemaDetail();
        ecosApiService.retrieveData(new EcosDto(nowDate, nowDate, CycleType.MM));
//        ecosSchemaRepo.saveAll(ecosSchemaList);
    }


}
