package com.zy.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.zy.webflux.handler.UserHandler;

@Configuration
public class WebFluxConfig {
	
	@Bean
    public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/function/user")
        		.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::getAll)
            .andRoute(RequestPredicates.GET("/function/user/{userId}")
            	.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::getUser)
            .andRoute(RequestPredicates.POST("/function/user")
            	.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::postUser)
            .andRoute(RequestPredicates.PUT("/function/user/{userId}")
            	.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::putUser)
            .andRoute(RequestPredicates.DELETE("/function/user/{userId}")
            	.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::deleteUser);
	}

}
