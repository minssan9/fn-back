//package com.core.config;
//
//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.mongodb.MongoDatabaseFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//@Configuration
//@EnableMongoRepositories(basePackages = "com.core.mongorepo")
//public class MongoConfig {
//
//    @Value("${spring.data.mongodb.host}")
//    private String host;
//    @Value("${spring.data.mongodb.database}")
//    private String database;
//
//    @Bean
//    @Primary
//    public MongoDatabaseFactory mongoDbFactory(){
//        return new SimpleMongoClientDatabaseFactory(mongoClient(), database);
////        return new SimpleMongoClientDatabaseFactory(mongoProperties.getHost(), );
//    }
//
//    public MongoClient mongoClient() {
//        ConnectionString connString= new ConnectionString(host);
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .applyConnectionString(connString)
//                .retryWrites(true)
//                .build();
//        return MongoClients.create(settings);
//    }
//
//    @Bean(name = "mongoTemplate")
//    @Primary
//    public MongoTemplate mongoTemplate()  {
//        return new MongoTemplate(mongoDbFactory());
//    }
//
//
////    @Bean
////    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
////        return new MongoTemplate(mongoDatabaseFactory);
////    }
////
////    @Bean
////    public MongoDatabaseFactory mongoDatabaseFactory(MongoProperties mongoProperties) {
////        return new SimpleMongoClientDatabaseFactory(mongoProperties.getHost());
////    }
//}
