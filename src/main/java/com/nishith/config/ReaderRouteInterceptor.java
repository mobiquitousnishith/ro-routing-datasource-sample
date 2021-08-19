package com.nishith.config;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
@Order(0)
@Slf4j
public class ReaderRouteInterceptor {

    @Around("@annotation(transactional)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, Transactional transactional) throws Throwable {
        try {
            if (transactional.readOnly()) {
                ReadWriteRoutingDatasource.setReaderRoute();
                log.debug("Routing to reader copy of the database.");
            } else {
                ReadWriteRoutingDatasource.setWriterRoute();
            }
            return proceedingJoinPoint.proceed();
        } finally {
            ReadWriteRoutingDatasource.clearRoute();
        }
    }
}
