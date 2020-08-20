package com.bone.was;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEncryptableProperties
@EnableScheduling
@SpringBootApplication
public class WasApplication {

    public static void main(String[] args) {

        SpringApplication.run(WasApplication.class, args);

    }

}
