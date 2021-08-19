package com.nishith.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.writer.hikari")
public class WriterDatasourcePoolConfig extends DatasourcePoolConfigProperties {
}
