package com.moj.digital.laa;

import com.moj.digital.laa.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.moj.digital.laa.repository")
@EntityScan("com.moj.digital.laa.entity")
@SpringBootApplication
public class PDFeFormsApplication {

    @Autowired
    private PersonRepository personRepository;

    public static void main(String[] args) {
        System.out.println("Application starting");
        SpringApplication.run(PDFeFormsApplication.class, args);
    }
}
