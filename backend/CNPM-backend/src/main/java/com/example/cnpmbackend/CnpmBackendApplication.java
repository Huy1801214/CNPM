package com.example.cnpmbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CnpmBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CnpmBackendApplication.class, args);
        System.out.println("Spring Boot Application CNPM-backend started successfully!");
    }

}