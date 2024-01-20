package com.sportsmatch;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@OpenAPIDefinition
@EnableWebMvc
public class SportsmatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsmatchApplication.class, args);
    }

}
