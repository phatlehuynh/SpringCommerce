package com.example.demo.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFiler;
    private final AuthenticationProvider  authenticationProvider;
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/login")
                                .permitAll()
                                .requestMatchers("/api/user/insert")
                                .permitAll()
                                .requestMatchers("/api/user/register")
                                .permitAll()
                                .requestMatchers("/api/products/**")
//                                .hasRole("ADMIN")
                                .permitAll()
                                .requestMatchers("/api/products/insert")
                                .hasRole("ADMIN")
//                                .requestMatchers("/api/products/delete/**")
//                                .hasRole("ADMIN")
                                .requestMatchers("api/auth/**").permitAll()

//                                .requestMatchers("api/users/**").permitAll()
//                                .requestMatchers("api/users").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFiler, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
