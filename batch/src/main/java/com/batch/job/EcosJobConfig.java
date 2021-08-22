package com.batch.job;

import com.batch.tasklet.EcosJobStep2Tasklet;
import com.core.ecos.service.EcosBatchService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.core.ecos.service.EcosBatchServiceImplREST;
import com.core.ecos.dto.EcosDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class EcosJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job sampleJob() {
        return jobBuilderFactory.get("sampleJob")
                .start(sampleStep1(null))
                    .on("FAILED")
                    .to(sampleStep3())
                    .on("*")
                    .end()
                .from(sampleStep1(null))
                    .on("*")
                    .to(sampleStep2())
                    .next(sampleStep3())
                    .on("*")
                    .end()
                .end()
                .build();
    }

    @Bean
    @JobScope
    public Step sampleStep1(@Value("#{jobParameters[str]}") String str) {
        return stepBuilderFactory.get("sampleStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("sampleStep1 start : {}", str);

                    // step1 logic
                    EcosBatchService ecosBatchService = new EcosBatchServiceImplREST();

                    String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                    String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
                    EcosDto ecosDto = new EcosDto();
                    ecosBatchService.retrieveData(ecosDto);


                    if(false){
                        log.error("ERROR");
                        contribution.setExitStatus(ExitStatus.FAILED);
                    }

                    return RepeatStatus.FINISHED;
                }).build();
    }

    private final EcosJobStep2Tasklet ecosJobStep2Tasklet;
//    @Bean
//    @JobScope
    public Step sampleStep2() {
        return stepBuilderFactory.get("sampleStep2")
                .tasklet(ecosJobStep2Tasklet)
                .build();
    }

    @Bean
    @JobScope
    public Step sampleStep3() {
        return stepBuilderFactory.get("sampleStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info("sampleStep3 start");

                    // step3 logic


                    return RepeatStatus.FINISHED;
                }).build();
    }
}
