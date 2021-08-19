package com.nishith.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ReadWriteRoutingDatasource extends AbstractRoutingDataSource {

    private static final ThreadLocal<Route> ROUTE_CONTEXT = new ThreadLocal<>();

    public static void clearRoute() {
        ROUTE_CONTEXT.remove();
    }

    public static void setReaderRoute() {
        ROUTE_CONTEXT.set(Route.READER);
    }

    public static void setWriterRoute() {
        ROUTE_CONTEXT.set(Route.WRITER);
    }

    @Override
    public Object determineCurrentLookupKey() {
        return ROUTE_CONTEXT.get();
    }

    public enum Route {
        WRITER, READER
    }
}
