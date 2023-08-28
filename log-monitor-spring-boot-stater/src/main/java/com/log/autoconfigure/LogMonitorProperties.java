package com.log.autoconfigure;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "log.monitor")
@Configuration
@Slf4j
@Data
public class LogMonitorProperties {

//    private List<Map<String, LogMonitorLevelEnum>> monitorLevel;

    private List<LogMonitorLevelEnum> levels;

    @PostConstruct
    public void print(){
        log.info("level:{}",levels);
    }

}
