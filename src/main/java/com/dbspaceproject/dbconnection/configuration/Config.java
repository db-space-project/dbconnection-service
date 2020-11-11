package com.dbspaceproject.dbconnection.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class Config {
    public static final String API_MATCHER_PATH = "/dbconnection/**";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        final ServerWebExchangeMatcher apiPathMatcher = pathMatchers(API_MATCHER_PATH);

        http.authorizeExchange().matchers(apiPathMatcher).authenticated()
                .and().httpBasic().disable()
                .csrf().disable()
                .logout().disable()
                .oauth2ResourceServer().jwt().jwkSetUri("http://localhost:8080/auth/realms/db-space-project/protocol/openid-connect/certs");

        return http.build();
    }
}
