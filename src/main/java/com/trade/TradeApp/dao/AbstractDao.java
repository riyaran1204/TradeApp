package com.trade.TradeApp.dao;

import com.trade.TradeApp.configuration.MongoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class AbstractDao<T> {

    @Autowired
    MongoConfiguration mongoConfiguration;

    public static final String MASTER="master";

    protected final MongoOperations getMongoOperations() {
        return mongoConfiguration.getMongoTemplate(MASTER);
    }

    public List<T> findByProperty(String key, Object value, Class<T> clazz)  {
        final Query query = new Query().addCriteria(Criteria.where(key).is(value));
        return find(query, clazz);
    }
    public List<T> find(Query query, Class<T> clazz)  {
        final MongoOperations mongoOperations = getMongoOperations();
        return mongoOperations.find(query, clazz);
    }

    public void save(Object object)  {
        MongoOperations mongoOperations = getMongoOperations();
        mongoOperations.save(object);
    }
}
