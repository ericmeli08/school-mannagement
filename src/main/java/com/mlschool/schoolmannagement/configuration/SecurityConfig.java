package com.mlschool.schoolmannagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mlschool.schoolmannagement.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig { 

    private final CustomUserDetailsService userDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                        .requestMatchers("/js/*").permitAll()
                        .requestMatchers("/photo/images/*").permitAll()
                        .requestMatchers("/css/fontawesome/css/*").permitAll()
                        .requestMatchers("/photo/img/*").permitAll()
                        .requestMatchers("/photo/img/icon/*").permitAll()
                        .requestMatchers("/photo/img/curved-images/*").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/sign_up").permitAll()
                        .requestMatchers("/test").permitAll()
                        .requestMatchers("/css/*").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form ->
                            form
                            .loginPage("/login")
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/accueil").permitAll())
                .logout(out ->
                        out
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout")
                            .clearAuthentication(true)
                            .invalidateHttpSession(true).permitAll())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

}
