package com.core.service;



import static com.core.common.properties.StaticProperties.DATE_STRING_FORMAT;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.core.ecos.service.EcosBatchService;
import com.core.ecos.domain.EcosData;
import com.core.ecos.dto.EcosEnumType.CycleType;
import com.core.ecos.domain.EcosSchemaDetail;
import com.core.ecos.dto.EcosDto;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
@RunWith(SpringRunner.class)
class EcosServiceImplTest {

    @Autowired
    EcosBatchService ecosBatchService;

    @Test
    void getAPIKOSPI() {
        WebClient webClient = WebClient.create("http://ecos.bok.or.kr/api");
        String queryDate = "20210210";
        EcosDto ecosDto = new EcosDto("064Y001", "0001000", "", "", queryDate, queryDate, CycleType.DD, 1L, 1000L);

        String ecosDataResponseMono  = webClient.get()
            .uri(uriBuilder ->
                uriBuilder.path(ecosDto.getServiceName())
                .queryParams(ecosDto.toMultiValueMap())
                .build()
            )
            .retrieve()
            .bodyToMono(String.class)
            .block();
        System.out.println( ecosDataResponseMono);
        assertNotNull(ecosDataResponseMono);
    }

    @Test
    void batchKOSDAQ() {
        String queryDate = "20210210";
        List<EcosData> ecosData = ecosBatchService.retrieveData(
                new EcosDto("064Y001", "0001000", "", "", queryDate, queryDate, CycleType.DD, 1L, 1000L)
        );
        assertNotNull(ecosData);
    }

    @Test
    void saveAllBySchema() {
        String todayString = LocalDateTime.now().minusDays(2L).format(DATE_STRING_FORMAT);
        EcosDto ecosDto = new EcosDto();
        ecosDto.setQueryStartDate(todayString);
        ecosDto.setQueryEndDate(todayString);

        List<EcosSchemaDetail> ecosData = ecosBatchService.retrieveData(ecosDto);
        assertNotNull(ecosData);
    }

    @Test
    void batchSchema() {
        ecosBatchService.retrieveSchema();
    }
}
