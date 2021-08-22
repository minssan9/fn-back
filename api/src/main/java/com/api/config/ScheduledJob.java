package com.api.config;

import com.core.ecos.service.EcosBatchService;
import com.core.ecos.dto.EcosDto;
import com.core.ecos.repo.EcosSchemaRepo;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import static com.core.common.properties.StaticProperties.DATE_STRING_FORMAT;

@EnableScheduling
public class ScheduledJob {

    @Autowired
    EcosBatchService ecosBatchService;
    @Autowired
    EcosSchemaRepo ecosSchemaRepo;

    @Scheduled(cron = "0 0 24 * * *")
    void retrieveEcosData() throws Exception{
        String nowDate = LocalDateTime.now().format(DATE_STRING_FORMAT);
        EcosDto ecosDto = new EcosDto();
        ecosDto.setQueryEndDate(nowDate);
        ecosDto.setQueryEndDate( nowDate);

        ecosBatchService.retrieveData(ecosDto);
    }

}
