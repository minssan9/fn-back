package com.core.ecos.service;


import com.core.ecos.domain.EcosData;
import com.core.ecos.domain.EcosSchema;
import com.core.ecos.dto.EcosDto;
import com.core.ecos.repo.EcosDataRepo;
import com.core.ecos.repo.EcosDataRepositorySupport;
import com.core.ecos.repo.EcosSchemaDetailRepo;
import com.core.ecos.repo.EcosSchemaRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
class EcosServiceImpl implements EcosService {
    @Autowired EcosDataRepo ecosDataRepo;
    @Autowired EcosSchemaRepo ecosSchemaRepo;
    @Autowired EcosDataRepositorySupport ecosDataRepositorySupport;
    @Autowired EcosSchemaDetailRepo ecosSchemaDetailRepo;

//    @Autowired EcosDataMongoRepo ecosDataMongoRepo;
//    @Autowired EcosSchemaMongoRepo ecosSchemaMongoRepo;

    @Override
    public List<EcosSchema> getSchema(EcosDto ecosDto) {
//        return ecosSchemaMongoRepo.findAll();
//        ecosDataRepositorySupport.findDynamicQuery()
        return ecosSchemaRepo.findAll();
    }

    @Override
    public List<EcosData> getData(EcosDto ecosDto) {
//        return ecosDataMongoRepo.findAll();
        return ecosDataRepo.findAll();
    }
}
