package uemura.java_spring_boot_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class JavaSpringBootDemoApplication {

    private static final Logger log = LoggerFactory.getLogger(JavaSpringBootDemoApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(JavaSpringBootDemoApplication.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();

        String version = environment.getProperty("info.version");
        log.info("\n\nJava Spring Boot Demo version: {}", version);
    }

}
