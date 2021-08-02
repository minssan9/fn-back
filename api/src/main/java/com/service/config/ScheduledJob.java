package com.service.config;

import static com.core.config.properties.CoreProperties.DATE_STRING_FORMAT;

import com.core.ecos.apiservice.EcosApiService;
import com.core.ecos.dto.EcosDto;
import com.core.ecos.repo.EcosSchemaRepo;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class ScheduledJob {

    @Autowired
    EcosApiService ecosApiService;
    @Autowired
    EcosSchemaRepo ecosSchemaRepo;

    @Scheduled(cron = "0 0 24 * * *")
    void retrieveEcosData() throws Exception{
        String nowDate = LocalDateTime.now().format(DATE_STRING_FORMAT);
        EcosDto ecosDto = new EcosDto();
        ecosDto.setQueryEndDate(nowDate);
        ecosDto.setQueryEndDate( nowDate);

        ecosApiService.retrieveData(ecosDto);
    }

}
