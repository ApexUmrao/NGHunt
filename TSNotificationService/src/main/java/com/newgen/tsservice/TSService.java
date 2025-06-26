package com.newgen.tsservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class TSService extends SpringBootServletInitializer {
	
	private static final Logger logger = LogManager.getLogger(TSService.class);

	public static void main(String[] args) {
		logger.info("<<<<<<<<<<<<<< Logger enabled >>>>>>>>>>>>>>");
		SpringApplication.run(TSService.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TSService.class);
	}

}
