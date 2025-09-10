package com.example.mybatis_ex.config;

//MyBatis
// 자바 기반의 퍼시스턴스 프레임워크예요.
// 쉽게 말하면 **데이터베이스와 자바 객체(클래스)**를 연결해주는 도구예요.


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Bean
  protected SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
    httpSecurity.authorizeHttpRequests(auth ->auth.anyRequest().permitAll());
    return httpSecurity.build();
  }
}