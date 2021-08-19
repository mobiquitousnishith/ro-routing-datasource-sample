package com.nishith.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DatasourceConfig {

    @Bean(name = "writerDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.writer")
    public DataSource primaryDataSource(WriterDatasourcePoolConfig poolConfig, DatabasePoolConfigBuilder databasePoolConfigBuilder) {
        return databasePoolConfigBuilder.build(poolConfig);
    }

    @Bean(name = "readerDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.reader")
    public DataSource replicaDataSource(ReaderDatasourcePoolConfig poolConfig, DatabasePoolConfigBuilder databasePoolConfigBuilder) {
        return databasePoolConfigBuilder.build(poolConfig);
    }

    @Bean
    @Primary
    public DataSource dataSource(
            @Qualifier("writerDatasource") DataSource writerDatasource,
            @Qualifier("readerDatasource") DataSource readerDatasource) {
        final ReadWriteRoutingDatasource routingDataSource = new ReadWriteRoutingDatasource();

        final Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(ReadWriteRoutingDatasource.Route.WRITER, writerDatasource);
        targetDataSources.put(ReadWriteRoutingDatasource.Route.READER, readerDatasource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(writerDatasource);

        return routingDataSource;

    }
}