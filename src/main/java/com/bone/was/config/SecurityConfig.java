package com.bone.was.config;

//import com.bone.was.Interceptor.AuthInterceptor;
import com.bone.was.Filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//     authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
        http.csrf().disable();
        //http.cors().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/jwt/users/**").permitAll()
//                .anyRequest().permitAll()
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class);

//            .anyRequest().
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
//        http.authorizeRequests().antMatchers("/lights/**").hasRole("USER")
//                .anyRequest().permitAll()
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class);
//        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
//        http
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/jwt/join","/jwt/login").permitAll()
//                //.antMatchers("/lights/**").hasRole("USER")
//                .anyRequest().permitAll()
//                //.antMatchers("/lights/*")
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class);


//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception{
        String encPassword = "$2a$10$3rbfj1WmRaMa92tOoOW2gObALHAi8hwU5XbkNoDBXM9bhIbwJa7b6";

        auth.inMemoryAuthentication()
                .withUser("skymoonight")
                .password("{bcrypt}"+encPassword)
                .roles("Admin");
    }



}
