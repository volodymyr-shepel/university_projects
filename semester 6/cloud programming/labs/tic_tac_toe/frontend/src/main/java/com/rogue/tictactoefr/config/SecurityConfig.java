package com.rogue.tictactoefr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${aws.cognito.domain}")
    private String awsCognitoDomain;

    @Value("${spring.security.oauth2.client.registration.cognito.clientId}")
    private String clientId;

    @Value("${aws.cognito.logout-uri}")
    private String logoutUri;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .logout(logout -> logout
                        .logoutSuccessUrl(String.format("https://%s.auth.us-east-1.amazoncognito.com/logout?client_id=%s&logout_uri=%s",
                                awsCognitoDomain, clientId, logoutUri))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                )
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/oauth2/authorization/cognito")
                        .defaultSuccessUrl("/", true)
                );

        return http.build();
    }
    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }

}

