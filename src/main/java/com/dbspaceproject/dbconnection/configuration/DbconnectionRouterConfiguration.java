package com.dbspaceproject.dbconnection.configuration;

import com.dbspaceproject.dbconnection.handler.DbconnectionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class DbconnectionRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> organizationRoute(DbconnectionHandler dbconnectionHandler) {
        return RouterFunctions
                .route(POST("/dbconnection")
                        .and(contentType(MediaType.APPLICATION_JSON)), dbconnectionHandler::createDbconnection)
                .andRoute(GET("/dbconnection/{id}")
                        .and(accept(MediaType.APPLICATION_JSON)), dbconnectionHandler::getById);
    }
}
