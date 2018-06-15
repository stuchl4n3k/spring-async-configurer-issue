package com.example.demo.components;

import com.example.demo.problematic.ProblematicComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerComponent {

    private final ProblematicComponent problematicComponent;

    public void handle(Throwable throwable) {
        problematicComponent.doSomething();
        throwable.printStackTrace();
    }
}
