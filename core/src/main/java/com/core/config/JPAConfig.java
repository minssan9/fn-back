package com.core.config;

import com.core.common.properties.MysqlProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Objects;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = {"com.core.*.repo"},
    entityManagerFactoryRef = "mysqlEntityManagerFactory",
    transactionManagerRef = "mysqlTransactionManager")
@EntityScan(basePackages = "com.core.*.domain")
public class JPAConfig {
    @Autowired
    MysqlProperties mysqlProperties;

    @Bean
    @Primary
    public Properties mysqlDBProperties() {
        Properties mysqlProperties = new Properties();
        mysqlProperties.put("jdbcUrl", this.mysqlProperties.getUrl());
        mysqlProperties.put("username", this.mysqlProperties.getUsername());
        mysqlProperties.put("password", this.mysqlProperties.getPassword());
        mysqlProperties.put("driverClassName" , this.mysqlProperties.getDriverClassName());
        return mysqlProperties;
    }

    @Bean(name = "mysqlDataSource")
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig(mysqlDBProperties());
        return new LazyConnectionDataSourceProxy(new HikariDataSource(config));
    }
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
        EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource())
            .packages("com.core.*.domain")
            .persistenceUnit("mysql")
            .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager mysqlTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(mysqlEntityManagerFactory(builder).getObject()));
    }
}
