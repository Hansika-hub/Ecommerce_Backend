package com.example.ecommapp.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.ecommapp.repository.product", mongoTemplateRef = "productMongoTemplate")
public class ProductMongoConfig {

    @Value("${spring.data.mongodb.product.uri}")
    private String mongoUri;

    @Primary
    @Bean(name = "productDatabaseFactory")
    public MongoDatabaseFactory productDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), "product_db");
    }

    @Primary
    @Bean(name = "productMongoTemplate")
    public MongoTemplate productMongoTemplate() {
        return new MongoTemplate(productDatabaseFactory());
    }
}
