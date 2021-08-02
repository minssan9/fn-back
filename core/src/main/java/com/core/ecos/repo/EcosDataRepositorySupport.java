package com.core.ecos.repo;

import static com.core.ecos.domain.QEcosData.ecosData;

import com.core.ecos.domain.EcosData;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class EcosDataRepositorySupport extends QuerydslRepositorySupport {

    public EcosDataRepositorySupport() {
        super(EcosData.class);
    }

    public List<EcosData> findDynamicQuery(String statCode, String itemCode1, String createdDate){
//        return from(ecosData)
//            .where(eqStatCode(statCode), eqItemCode1(itemCode1))
//                .fetch();

        return   from(ecosData)
            .where(ecosData.createdDate.eq(createdDate)
            .and(ecosData.statCode.eq(statCode)))
            .fetch();
    }


    private BooleanExpression eqStatCode(String statCode) {
        if (StringUtils.isEmpty(statCode)) {
            return null;
        }
        return ecosData.statCode.eq(statCode);
    }

    private BooleanExpression eqItemCode1(String itemCode1) {
        if (StringUtils.isEmpty(itemCode1)) {
            return null;
        }
        return ecosData.itemCode1.eq(itemCode1);
    }

//    private BooleanExpression eqPhoneNumber(String phoneNumber) {
//        if (StringUtils.isEmpty(phoneNumber)) {
//            return null;
//        }
//        return ecosData.phoneNumber.eq(phoneNumber);
//    }
}
