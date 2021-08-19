package com.nishith.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.reader.hikari")
public class ReaderDatasourcePoolConfig extends DatasourcePoolConfigProperties {
}
