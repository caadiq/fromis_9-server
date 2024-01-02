package com.beemer.fromis9server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Fromis9ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(Fromis9ServerApplication.class, args);
    }
}