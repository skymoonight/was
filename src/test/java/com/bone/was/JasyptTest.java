package com.bone.was;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptTest {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword("test1234");

        String url = "jdbc:h2:file:./h2_db";
        String username = "skymoonight";
        String password = "test1234";

        System.out.println("기존 username :: " + username + " | 변경 username :: " + pbeEnc.encrypt(username));
        System.out.println("기존 password :: " + password + " | 변경 password :: " + pbeEnc.encrypt(password));
    }

}


