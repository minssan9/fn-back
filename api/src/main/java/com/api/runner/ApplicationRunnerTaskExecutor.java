package com.api.runner;


import com.core.ecos.service.EcosBatchService;
import com.core.ecos.domain.EcosSchemaDetail;
import com.core.ecos.dto.EcosDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("!test")
@Slf4j
@Component
public class ApplicationRunnerTaskExecutor implements ApplicationRunner {

    @Autowired
    EcosBatchService ecosBatchService;

    @Override
    public void run(ApplicationArguments args) {
        //시작일자
        ecosBatchService.retrieveSchema();
        List<EcosSchemaDetail>  ecosSchemaDetails = ecosBatchService.retrieveSchemaDetail();

        for (EcosSchemaDetail ecosSchemaDetail:ecosSchemaDetails) {
            ecosBatchService.retrieveData(new EcosDto());
        }


    }
}
