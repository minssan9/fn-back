package com.batch.job;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.core.ecos.apiservice.EcosApiService;
import com.core.ecos.domain.EcosSchema;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EcosJobConfigTest {

    @Autowired
    EcosApiService ecosApiService;
    @Test
    void sampleJob() {
    }

    @Test
    void sampleStep1() {
        List<EcosSchema> ecosSchemaList = ecosApiService.retrieveSchema();
        assertNotNull(ecosSchemaList);
    }

    @Test
    void sampleStep2() {
    }

    @Test
    void sampleStep3() {
    }
}
