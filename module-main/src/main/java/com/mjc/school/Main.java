package com.mjc.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.util.Collections;


@EntityScan(basePackageClasses = {Main.class, Jsr310JpaConverters.class})
@SpringBootApplication
public class Main extends SpringBootServletInitializer {
    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(Main.class);
        application.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        application.run(args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Main.class);
    }
}
