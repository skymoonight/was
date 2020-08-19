package com.bone.was;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableEncryptableProperties
@EnableScheduling
//@EnableResourceServer
@SpringBootApplication
public class WasApplication {

    public static void main(String[] args) {

        SpringApplication.run(WasApplication.class, args);

    }

//    @Bean("jasyptStringEncryptor")
//    public StandardPBEStringEncryptor jasyptConfigure(){
//        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//        SimplePBEConfig config = new SimplePBEConfig();
//        config.setAlgorithm("PBEWithMD5AndDES");
//        config.setPoolSize(1);
//        encryptor.setConfig(config);
//
//        return encryptor;
//    }
}
