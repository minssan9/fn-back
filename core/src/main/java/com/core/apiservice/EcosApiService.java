package com.core.apiservice;

import com.core.domain.EcosData;
import com.core.domain.EcosSchema;
import com.core.domain.EcosSchemaDetail;
import com.core.dto.EcosDto;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EcosApiService<T> {
//    URI getUrlString(EcosDto ecosDto);
    JsonObject getAPIData( EcosDto ecosDto);
    List<EcosData> saveData(EcosDto ecosDto) ;
    List<EcosSchema> retrieveSchema();
    List<EcosSchemaDetail> retrieveSchemaDetail();
    List<EcosData> retrieveData(EcosDto ecosDto);
}
