package com.batch.job;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.core.ecos.service.EcosBatchService;
import com.core.ecos.domain.EcosSchema;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EcosJobConfigTest {

    @Autowired
    EcosBatchService ecosBatchService;
    @Test
    void sampleJob() {
    }

    @Test
    void sampleStep1() {
        List<EcosSchema> ecosSchemaList = ecosBatchService.retrieveSchema();
        assertNotNull(ecosSchemaList);
    }

    @Test
    void sampleStep2() {
    }

    @Test
    void sampleStep3() {
    }
}
