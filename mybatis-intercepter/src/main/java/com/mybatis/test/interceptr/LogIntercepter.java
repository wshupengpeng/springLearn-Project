package com.mybatis.test.interceptr;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserGlobal;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.DataChangeRecorderInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.mybatis.test.entity.OpeateResult;
//import com.mybatis.test.entity.OperationResult;
import com.mybatis.test.event.LogEvent;
import com.mybatis.test.mapper.OpeateResultMapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @creater hpp
 * @Date 2023/8/21-20:43
 * @description: 拦截指定表的字段变更, 并记录一条数据
 */
@Slf4j
@Component
public class LogIntercepter implements InnerInterceptor{

    @Autowired
    @Lazy
    private OpeateResultMapper opeateResultMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {

        return InnerInterceptor.super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        InnerInterceptor.super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    @Override
    public boolean willDoUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        return InnerInterceptor.super.willDoUpdate(executor, ms, parameter);
    }

    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        InnerInterceptor.super.beforeUpdate(executor, ms, parameter);
    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);


        MappedStatement ms = mpSh.mappedStatement();
        final BoundSql boundSql = mpSh.boundSql();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.INSERT || sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE) {
            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            OpeateResult operationResult = null;
            long startTs = System.currentTimeMillis();
            try {
                Statement statement = JsqlParserGlobal.parse(mpBs.sql());
                if (statement instanceof Insert) {
                    operationResult = processInsert((Insert) statement, mpSh.boundSql());
                } else if (statement instanceof Update) {
                    operationResult = processUpdate((Update) statement, ms, boundSql, connection);
                } else if (statement instanceof Delete) {
                    operationResult = processDelete((Delete) statement, ms, boundSql, connection);
                } else {
                    log.info("other operation sql={}", mpBs.sql());
                    return;
                }
            } catch (Exception e) {
                if (e instanceof DataChangeRecorderInnerInterceptor.DataUpdateLimitationException) {
                    throw (DataChangeRecorderInnerInterceptor.DataUpdateLimitationException) e;
                }
                log.error("Unexpected error for mappedStatement={}, sql={}", ms.getId(), mpBs.sql(), e);
                return;
            }
            long costThis = System.currentTimeMillis() - startTs;
            if (operationResult != null) {
//                operationResult.setCost(costThis);
                dealOperationResult(operationResult);
            }
        }

    }

    private OpeateResult processDelete(Delete statement, MappedStatement ms, BoundSql boundSql, Connection connection) {


        return null;

    }

    private OpeateResult processUpdate(Update statement, MappedStatement ms, BoundSql boundSql, Connection connection) {

        return null;
    }


    private OpeateResult processInsert(Insert statement, BoundSql boundSql) {
        OpeateResult operationResult = new OpeateResult();
        List<Column> columns = statement.getColumns();
        log.info("insert table is :{}", statement.getTable().getName());
        for (Column column : columns) {
            log.info("col:{}",column.getColumnName());
        }

        if("t_user".equalsIgnoreCase(statement.getTable().getName())){
//            int i = 1/0;
            operationResult.setOperate("insert");
            operationResult.setTableName(statement.getTable().getName());
            operationResult.setField(statement.getTable().getName());
            publisher.publishEvent(new LogEvent(operationResult));
//            opeateResultMapper.insert(operationResult);

        }
        return null;
    }

    private void dealOperationResult(OpeateResult operationResult) {

    }

    @Override
    public void beforeGetBoundSql(StatementHandler sh) {
        InnerInterceptor.super.beforeGetBoundSql(sh);
    }

    @Override
    public void setProperties(Properties properties) {
        InnerInterceptor.super.setProperties(properties);
    }
}
