//package com.bone.was.config;
//
//import io.jsonwebtoken.Jwt;
//import org.jasypt.encryption.StringEncryptor;
//import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
//import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
//import org.jasypt.encryption.pbe.config.SimplePBEConfig;
//import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JasyptConfig {
//
////    private static final String ENCRYPT_KEY = "bone1234";
//
////    @Bean("jasyptStringEncryptor")
////    public StandardPBEStringEncryptor jasyptConfigure(){
////        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
////
////        SimplePBEConfig config = new SimplePBEConfig();
////        config.setPassword(System.getenv("JASYPT_PASSWORD"));
////        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
////        config.setPoolSize(1);
////        encryptor.setConfig(config);
////
////        return encryptor;
////    }
//
//    @Bean("jasyptStringEncryptor")  // (1)
//    public StringEncryptor stringEncryptor() {
//        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
//        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//        config.setPassword(System.getenv("JASYPT_PASSWORD"));  // (2)
//        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
//        config.setKeyObtentionIterations("1000");
//        config.setPoolSize("1");
//        config.setProviderName("SunJCE");
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
//        config.setStringOutputType("base64");
//        encryptor.setConfig(config);
//        return encryptor;
//
//    }
//}
