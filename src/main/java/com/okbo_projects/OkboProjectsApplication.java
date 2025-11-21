package com.okbo_projects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OkboProjectsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OkboProjectsApplication.class, args);
    }

}
