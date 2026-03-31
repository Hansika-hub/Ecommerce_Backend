package com.example.ecommapp.config;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.ecommapp.repository.commerce", mongoTemplateRef = "commerceMongoTemplate")
public class CommerceMongoConfig {

    @Value("${spring.data.mongodb.commerce.uri}")
    private String mongoUri;

    @Bean(name = "commerceDatabaseFactory")
    public MongoDatabaseFactory commerceDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), "commerce_db");
    }

    @Bean(name = "commerceMongoTemplate")
    public MongoTemplate commerceMongoTemplate() {
        return new MongoTemplate(commerceDatabaseFactory());
    }
}
