package com.core.apiservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.core.common.properties.StaticProperties;
import com.core.ecos.service.EcosBatchService;
import com.core.ecos.domain.EcosSchema;
import java.util.List;

import com.core.ecos.domain.EcosSchemaDetail;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class EcosBatchServiceImplWebClientTest {

    @Autowired
    StaticProperties staticProperties;
    @Autowired
    EcosBatchService ecosBatchServiceImplWebClient;

    @Test
    void retrieveSchema() {
        List<EcosSchema> list = ecosBatchServiceImplWebClient.retrieveSchema();
        assertNotNull(list);
    }

    @Test
    void retrieveSchemaDetail() {
        List<EcosSchemaDetail> result = ecosBatchServiceImplWebClient.retrieveSchemaDetail();
        assertNotNull(result);
    }

    @Test
    void retrieveDataFromAllSchema() {
    }


}
