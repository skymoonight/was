package com.bone.was.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception{
        String encPassword = "$2a$10$3rbfj1WmRaMa92tOoOW2gObALHAi8hwU5XbkNoDBXM9bhIbwJa7b6";

        auth.inMemoryAuthentication()
                .withUser("skymoonight")
                .password("{bcrypt}"+encPassword)
                .roles("User");
    }


}
