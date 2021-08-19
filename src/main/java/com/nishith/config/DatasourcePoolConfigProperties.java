package com.nishith.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatasourcePoolConfigProperties {

    protected long connectionTimeout;

    protected long idleTimeout;

    protected int maximumPoolSize;

    protected int minimumIdle;

    protected String poolName;

    protected long maxLifetime;
}
