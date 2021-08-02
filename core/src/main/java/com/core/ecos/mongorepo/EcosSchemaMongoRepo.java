package com.core.ecos.mongorepo;

import com.core.ecos.document.EcosMongoSchema;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface EcosSchemaMongoRepo extends MongoRepository<EcosMongoSchema, ObjectId> {
//    EcosDataMongo

}
