package com.core.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("db-mysql")
@Data
@Component
public class MysqlProperties   {
    String url;
    String username;
    String password;
    String driverClassName;
}
