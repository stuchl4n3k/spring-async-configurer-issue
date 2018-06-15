package com.example.async;

import com.example.demo.components.ExceptionHandlerComponent;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.ProxyAsyncConfiguration;

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    /**
     * This explicit dependency causes the {@link ExceptionHandlerComponent} bean (and all its dependencies)
     * to be created early when BeanPostProcessors are registered. This prevents these beans to be processed
     * e.g. by AOP processors.
     * <p>
     *     This is because {@link ProxyAsyncConfiguration} causes this config to be created and its dependencies
     *     injected.
     * </p>
     * <p>
     *     As a workaround, one can add {@link Lazy} to this field.
     * </p>
     */
    @Autowired
    private ExceptionHandlerComponent exceptionHandlerComponent;

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> exceptionHandlerComponent.handle(throwable);
    }
}
