package com.batch.tasklet;

import com.core.ecos.apiservice.EcosApiService;
import com.core.ecos.dto.EcosDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
public class EcosJobStep2Tasklet implements Tasklet {

    @Value("#{jobParameters[str]}")
    private String str;

    @Autowired
    EcosApiService ecosApiService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("sampleStep2 start : {}", str);
        String startDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String endDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        log.info("sampleStep1 start : {}", startDate, endDate);
        EcosDto ecosDto = new EcosDto();
        ecosDto.setQueryStartDate(startDate);
        ecosDto.setQueryEndDate(endDate);

        ecosApiService.retrieveData(ecosDto);

        // step2 logic

        return RepeatStatus.FINISHED;
    }
}
