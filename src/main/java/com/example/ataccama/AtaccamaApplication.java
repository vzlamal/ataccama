package com.example.ataccama;

import com.example.ataccama.service.DatabaseConnectorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.beans.BeanProperty;

@SpringBootApplication
public class AtaccamaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtaccamaApplication.class, args);
    }

    @Bean
    public DatabaseConnectorService databaseConnectorService() {
        return new DatabaseConnectorService();
    }

}
