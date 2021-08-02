package com.core.ecos.service;

import com.core.ecos.domain.EcosData;
import com.core.ecos.domain.EcosSchema;
import com.core.ecos.dto.EcosDto;
import org.springframework.stereotype.Service;

import java.util.List;

//@Slf4j
@Service
public
interface EcosService {
    List<EcosSchema> getSchema(EcosDto ecosDto);
    List<EcosData> getData(EcosDto ecosDto );
}
