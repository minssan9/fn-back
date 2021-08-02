package com.core.ecos.repo;

import com.core.ecos.domain.EcosSchemaDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcosSchemaDetailRepo extends JpaRepository<EcosSchemaDetail, Long> {
    List<EcosSchemaDetail> findByStatcode(String statCode);
    List<EcosSchemaDetail> findByCycle(String cycleType);
    List<EcosSchemaDetail> deleteByPitemcode(String pItemCode);
}
