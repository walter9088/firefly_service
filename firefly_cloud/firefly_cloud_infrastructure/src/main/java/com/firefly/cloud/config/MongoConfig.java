package com.firefly.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "com.firefly.cloud.repository")
public class MongoConfig {
    
    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
    
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Collections.emptyList());
    }
} 