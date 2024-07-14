package com.example.secondlife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SecondLifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondLifeApplication.class, args);
    }

}
