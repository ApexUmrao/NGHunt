package com.newgen.raroc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class RarocApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
       SpringApplication.run(RarocApplication.class, args);
       System.out.println("Started");
    }

    @Bean
    public Docket api() {
       return new Docket(DocumentationType.SWAGGER_2)
             .select()
             .apis(RequestHandlerSelectors.any())
             .paths(PathSelectors.any())
             .build();
    }

}