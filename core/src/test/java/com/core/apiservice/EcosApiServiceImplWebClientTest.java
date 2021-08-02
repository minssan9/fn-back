package com.core.apiservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.core.config.properties.CoreProperties;
import com.core.ecos.apiservice.EcosApiService;
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
class EcosApiServiceImplWebClientTest {

    @Autowired
    CoreProperties coreProperties;
    @Autowired
    EcosApiService ecosApiServiceImplWebClient;

    @Test
    void retrieveSchema() {
        List<EcosSchema> list = ecosApiServiceImplWebClient.retrieveSchema();
        assertNotNull(list);
    }

    @Test
    void retrieveSchemaDetail() {
        List<EcosSchemaDetail> result = ecosApiServiceImplWebClient.retrieveSchemaDetail();
        assertNotNull(result);
    }

    @Test
    void retrieveDataFromAllSchema() {
    }


}
