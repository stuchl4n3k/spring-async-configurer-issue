# Spring AsyncConfigurer issue PoC

Shows how adding a Spring configuration implementing `AsyncConfigurer` can break some seemingly unrelated beans.

See the tests in `src/test/java/com/example/demo`.

```java
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
```