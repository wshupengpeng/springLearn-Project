package com.spring.environment.config;

import com.ctrip.framework.apollo.spring.config.PropertySourcesConstants;
import com.spring.environment.listener.ApolloRefreshListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * @Description: apollo对于@value动态刷新的操作流程
 *         1 通过PropertySourcesProcessor进行动态注入 CompositePropertySource 对象,并通过addFirst加入第一位
 *         2 通过长连接+event事件方式,刷新上一步的CompositePropertySource对象
 *         3 通过update操作使用
 *
 * @Author 01415355
 * @Date 2023/6/2 15:12
 */
@Component
@Slf4j
public class ApolloRefreshValueBeanProcessor implements BeanFactoryPostProcessor, EnvironmentAware {

    private static final String APOLLO_MOCK_PROPERTY = "apollo_mock_property";

    private CompositePropertySource composite = new CompositePropertySource(APOLLO_MOCK_PROPERTY);

    private ConfigurableEnvironment environment;

    @Autowired
    private ApolloRefreshListener apolloRefreshListener;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 1 添加propertysource
        environment.getPropertySources().addFirst(composite);
    }

    @PostConstruct
    public void registerListener(){
        // 2 注册监听器
        apolloRefreshListener.setComposite(composite);
        log.info("register listener success");
    }

}
