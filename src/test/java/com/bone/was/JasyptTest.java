package com.bone.was;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptTest {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        pbeEnc.setPassword("bone1234");

//        String url = "jdbc:h2:file:./h2_db";
//        String username = "skymoonight";
        String password = "bone1234";

        //System.out.println("���� username :: " + username + " | ���� username :: " + pbeEnc.encrypt(username));
        System.out.println("���� password :: " + password + " | ���� password :: " + pbeEnc.encrypt(password));
    }

}


