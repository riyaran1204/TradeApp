package com.trade.TradeApp.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MongoConfiguration {

    private Map<String, MongoTemplate> mongoTemplateMap = new HashMap<>();

    public MongoTemplate getMongoTemplate(String database) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        final MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, database);
        mongoTemplateMap.put(database, mongoTemplate);
        return mongoTemplate;


    }

}
