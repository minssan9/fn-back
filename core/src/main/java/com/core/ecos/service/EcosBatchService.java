package com.core.ecos.service;

import com.core.ecos.domain.EcosData;
import com.core.ecos.domain.EcosSchema;
import com.core.ecos.domain.EcosSchemaDetail;
import com.core.ecos.dto.EcosDto;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EcosBatchService<T> {
    //    URI getUrlString(EcosDto ecosDto);

    List<EcosSchema> retrieveSchema();
    List<EcosSchemaDetail> retrieveSchemaDetail();

    List<EcosData> retrieveData(EcosDto ecosDto);
}
