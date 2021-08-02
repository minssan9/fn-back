package com.core.ecos.document;

import com.core.ecos.domain.EcosData;
import javax.persistence.Id;

import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Document
public class EcosMongoData extends EcosData {
        @Id
        private ObjectId _id;

}
