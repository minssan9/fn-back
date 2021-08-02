package com.core.ecos.dto;


import com.core.ecos.dto.EcosEnumType.CycleType;
import javax.persistence.AttributeConverter;

public class CycleTypeConverter implements AttributeConverter<CycleType, String> {

    @Override
    public String convertToDatabaseColumn(CycleType attribute) {
        return attribute.getCycle();
    }

    @Override
    public CycleType convertToEntityAttribute(String dbData) {
        return CycleType.parse(dbData);
    }

}
