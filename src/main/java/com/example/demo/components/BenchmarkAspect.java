package com.example.demo.components;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class BenchmarkAspect {

    @Pointcut("execution(public * *(..))")
    public void anyPublicMethod() {
    }

    @Pointcut("within(com.example.demo.problematic..*)")
    public void inProblematicPackage() {
    }

    @Around("anyPublicMethod() && inProblematicPackage())")
    public Object doBenchmark(ProceedingJoinPoint pjp) throws Throwable {
        long t = System.currentTimeMillis();
        Object retVal = pjp.proceed();

        System.out.printf("Polo! (in %d ms.)%n", System.currentTimeMillis() - t);
        return retVal;
    }

}
