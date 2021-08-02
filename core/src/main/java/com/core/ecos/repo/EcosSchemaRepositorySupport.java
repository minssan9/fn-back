package com.core.ecos.repo;

import static com.core.ecos.domain.QEcosSchema.ecosSchema;

import com.core.ecos.domain.EcosSchema;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class EcosSchemaRepositorySupport extends QuerydslRepositorySupport {

    public EcosSchemaRepositorySupport() {
        super(EcosSchema.class);
    }

    public List<EcosSchema> findDynamicQuery(String pStatCode,String statCode,  String createdDate){
//        return from(ecosData)
//            .where(eqStatCode(statCode), eqItemCode1(itemCode1))
//                .fetch();

        return   from(ecosSchema)
            .where(ecosSchema.pstatcode.eq(pStatCode)
            .and(ecosSchema.statcode.eq(statCode)))
            .limit(100)
//            .select(ecosSchema.statcode)
//            .select(Projections.constructor(EcosDto.class,
//                ecosSchema.statcode,
//                ecosSchema.searchFlag
//                ))
            .fetch();
    }


    private BooleanExpression eqPStatCode(String pStatCode) {
        if (StringUtils.isEmpty(pStatCode)) {
            return null;
        }
        return ecosSchema.statcode.eq(pStatCode);
    }

    private BooleanExpression eqStatCode(String statcode) {
        if (StringUtils.isEmpty(statcode)) {
            return null;
        }
        return ecosSchema.statcode.eq(statcode);
    }

//    private BooleanExpression eqPhoneNumber(String phoneNumber) {
//        if (StringUtils.isEmpty(phoneNumber)) {
//            return null;
//        }
//        return ecosData.phoneNumber.eq(phoneNumber);
//    }
}
