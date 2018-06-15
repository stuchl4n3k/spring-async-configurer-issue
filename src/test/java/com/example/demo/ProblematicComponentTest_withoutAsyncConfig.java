package com.example.demo;

import com.example.demo.components.BenchmarkAspect;
import com.example.demo.problematic.ProblematicComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProblematicComponentTest_withoutAsyncConfig {

    @Autowired
    private ProblematicComponent problematicComponent;

    /**
     * {@link BenchmarkAspect} causes {@link ProblematicComponent} to be proxied and correctly kicks in.
     */
    @Test
    public void problematicComponentIsProxied() {
        // Prints:
        // Marco!
        // Polo!
        problematicComponent.doSomething();

        assertThat(problematicComponent, is(instanceOf(ProblematicComponent.class)));
        assertThat(problematicComponent.getClass().getSimpleName(), containsString(
                "ProblematicComponent$$EnhancerBySpringCGLIB"
        ));
    }
}
