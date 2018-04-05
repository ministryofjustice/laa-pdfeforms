package com.moj.digital.laa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PDFeFormsApplication {

    public static void main(String[] args) {
        log.debug("PDFeFormsApplication Starting..");
        SpringApplication.run(PDFeFormsApplication.class, args);
    }
}
