package com.TaskMicro.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return  http
                .csrf(csrf ->
                        csrf.disable()
                )
                .authorizeHttpRequests(autRequest ->
                        autRequest
                                .requestMatchers("/doc/**", "/swagger-ui/**", "/v3/api-docs/**","/**" ,"/swagger-ui.html","/eureka/**" ,"/**","/webjars/**", "/swagger-resources/**").permitAll()
                                .requestMatchers("/h2/**","/h2-console","/api/trainer/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/trainer/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/trainer/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/api/trainer/**").permitAll()
                                .anyRequest().authenticated()

                )
                .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();


    }

}
