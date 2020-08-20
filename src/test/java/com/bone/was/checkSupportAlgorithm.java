package com.bone.was;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.registry.AlgorithmRegistry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class checkSupportAlgorithm {
    public static void main(String[] args) {
        checkSupportAlgorithm();
    }
    public static void checkSupportAlgorithm() {
        List<Object> supported = new ArrayList<>();
        List<Object> unsupported = new ArrayList<>();
        for (Object algorithm : AlgorithmRegistry.getAllPBEAlgorithms()) {
            try {
                StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
                encryptor.setPassword("somePassword");
                encryptor.setAlgorithm(String.valueOf(algorithm));
                String str = "test";
                String encStr = encryptor.encrypt(str);
                String decStr = encryptor.decrypt(encStr);
                assertThat(str.equals(decStr));
                supported.add(algorithm);
            } catch (EncryptionOperationNotPossibleException e) {
                unsupported.add(algorithm);
            }
        }
        System.out.println(supported);
        System.out.println(unsupported);
    }
}
