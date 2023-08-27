package com.log.autoconfigure;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "log.monitor")
public class LogMonitorProperties {

//    private List<Map<String, LogMonitorLevelEnum>> monitorLevel;

    private LogMonitorLevelEnum level;

}
