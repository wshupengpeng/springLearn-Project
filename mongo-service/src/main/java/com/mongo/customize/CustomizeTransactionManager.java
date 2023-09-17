package com.mongo.customize;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @creater hpp
 * @Date 2023/9/7-21:02
 * @description:
 */
@Component(value = "customizeTransactionManager")
public class CustomizeTransactionManager implements PlatformTransactionManager {

    @Resource(name = "mongoTransaction")
    private MongoTransactionManager mongoTransactionManager;

    @Resource(name = "dataSourceTransaction")
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Override
    public TransactionStatus getTransaction(TransactionDefinition transactionDefinition) throws TransactionException {
        TransactionStatus transaction = mongoTransactionManager.getTransaction(transactionDefinition);
        TransactionStatus transaction1 = dataSourceTransactionManager.getTransaction(transactionDefinition);

        return null;
    }

    @Override
    public void commit(TransactionStatus transactionStatus) throws TransactionException {
        mongoTransactionManager.commit(transactionStatus);
        dataSourceTransactionManager.commit(transactionStatus);
    }

    @Override
    public void rollback(TransactionStatus transactionStatus) throws TransactionException {
        mongoTransactionManager.rollback(transactionStatus);
        dataSourceTransactionManager.rollback(transactionStatus);
    }


    @Data
    public static final class CustomizeTransactionStatus implements TransactionStatus{

        private final Map<PlatformTransactionManager, TransactionStatus> transactionStatuses = Collections
                .synchronizedMap(new HashMap<PlatformTransactionManager, TransactionStatus>());

        @Override
        public boolean hasSavepoint() {
            return false;
        }

        @Override
        public void flush() {

        }

        @Override
        public Object createSavepoint() throws TransactionException {
            return null;
        }

        @Override
        public void rollbackToSavepoint(Object o) throws TransactionException {

        }

        @Override
        public void releaseSavepoint(Object o) throws TransactionException {

        }

        @Override
        public boolean isNewTransaction() {
            return false;
        }

        @Override
        public void setRollbackOnly() {

        }

        @Override
        public boolean isRollbackOnly() {
            return false;
        }

        @Override
        public boolean isCompleted() {
            return false;
        }
    }
}
