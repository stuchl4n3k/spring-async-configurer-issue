package com.example.demo;

import com.example.async.AsyncConfig;
import com.example.demo.components.ExceptionHandlerComponent;
import com.example.demo.problematic.ProblematicComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(AsyncConfig.class)
public class ProblematicComponentTest_withAsyncConfig {

    @Autowired
    private ProblematicComponent problematicComponent;

    /**
     * {@link AsyncConfigurerSupport} in {@link AsyncConfig} causes {@link ExceptionHandlerComponent} and thus
     * {@link ProblematicComponent} too to be created early, before AOP bean post-processors are available.
     */
    @Test
    public void problematicComponentIsProxied() {
        // Prints:
        // Marco!
        problematicComponent.doSomething();

        assertThat(problematicComponent, is(instanceOf(ProblematicComponent.class)));
        // Fails:
        assertThat(problematicComponent.getClass().getSimpleName(), containsString(
                "ProblematicComponent$$EnhancerBySpringCGLIB"
        ));
    }
}
