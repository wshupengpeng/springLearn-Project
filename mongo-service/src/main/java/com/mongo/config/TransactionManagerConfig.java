package com.mongo.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @creater hpp
 * @Date 2023/9/7-20:45
 * @description:
 */
@Configuration
public class TransactionManagerConfig {

    @Bean("mongoTransaction")
    public MongoTransactionManager mongoTransactionManager(MongoDbFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }


    @Bean("dataSourceTransaction")
    @Primary
    DataSourceTransactionManager transactionManager(DataSource dataSource, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
//        transactionManagerCustomizers.ifAvailable((customizers) -> {
//            customizers.customize(transactionManager);
//        });
        return transactionManager;
    }
}
