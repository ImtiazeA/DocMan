package me.imtiaze.docman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 *
 * This is the starting point of the application. Annotating with @SpringBootApplication
 * is same as @Configuration @EnableAutoConfiguration @ComponentScan
 * <br> <br>
 * 1. @EnableAutoConfiguration - Enable auto-configuration of the Spring Application Context,
 * attempting to guess and configure beans that you are likely to need. Auto-configuration
 * classes are usually applied based on your classpath and what beans you have defined.
 * <br> <br>
 * 2. @Configuration - Indicates that a class declares one or more @Bean methods and may be processed by
 * the Spring container to generate bean definitions and service requests for those beans at runtime.
 * <br> <br>
 * 3 - @ComponentScan - The @ComponentScan annotation is used with the @Configuration annotation
 * to tell Spring the packages to scan for annotated components. @ComponentScan also used to specify
 * base packages and base package classes using thebasePackageClasses or basePackages attributes
 * of @ComponentScan.
 * */

@SpringBootApplication
public class DocManApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DocManApplication.class, args);
	}

}
