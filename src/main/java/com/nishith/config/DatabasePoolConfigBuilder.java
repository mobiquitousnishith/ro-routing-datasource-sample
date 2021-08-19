package com.nishith.config;

import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

@Component
public class DatabasePoolConfigBuilder {

    public HikariDataSource build(DatasourcePoolConfigProperties poolConfig) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPoolName(poolConfig.getPoolName());
        dataSource.setMaximumPoolSize(poolConfig.getMaximumPoolSize());
        dataSource.setMinimumIdle(poolConfig.getMinimumIdle());
        dataSource.setMaxLifetime(poolConfig.getMaxLifetime());
        dataSource.setIdleTimeout(poolConfig.getIdleTimeout());
        dataSource.setConnectionTimeout(poolConfig.getConnectionTimeout());
        return dataSource;
    }
}
