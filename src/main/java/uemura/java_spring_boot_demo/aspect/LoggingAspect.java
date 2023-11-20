package uemura.java_spring_boot_demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void withinService() {
    }

    @Around("withinService()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Object[] args = joinPoint.getArgs();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        long elapsedTime = System.currentTimeMillis() - startTime;
        log.info("Class [{}] Entering method [{}] with arguments: {} executed in {} ms", className, methodName, Arrays.toString(args), elapsedTime);

        return result;
    }
}
