package com.bone.was;


import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;


public class PasswordEncoderTest {

    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Test
    public void encode() {
        String password = "ddorddor";

        String encPassword = passwordEncoder.encode(password);
        System.out.print(encPassword);

        assertThat(passwordEncoder.matches(password, encPassword)).isTrue();
        assertThat(encPassword).contains("{bcrypt}");
    }
}


