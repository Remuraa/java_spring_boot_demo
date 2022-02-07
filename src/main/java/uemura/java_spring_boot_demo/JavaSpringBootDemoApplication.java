package uemura.java_spring_boot_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JavaSpringBootDemoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(JavaSpringBootDemoApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(JavaSpringBootDemoApplication.class, args);
		LOGGER.info("\n\nService port: "+ application.getEnvironment().getProperty("local.server.port"));
	}

}
