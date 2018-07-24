package com.zy.webflux.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.zy.webflux.model.User;
import com.zy.webflux.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {
	
	@Autowired
	private UserService userService;
	
	/**
     * GET ALL Users
     */
    public Mono<ServerResponse> getAll(ServerRequest request) {
        // fetch all customers from repository
        Flux<User> customers = userService.getAll();

        // build response
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(customers, User.class);
    }
    
    /**
     * GET a User by ID
     */
    public Mono<ServerResponse> getUser(ServerRequest request) {
        // parse path-variable
        long customerId = Long.valueOf(request.pathVariable("userId"));

        // build notFound response 
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        // get customer from repository 
        Mono<User> customerMono = userService.getUserByUserId(customerId);

        // build response
        return customerMono
            .flatMap(customer -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromObject(customer)))
            .switchIfEmpty(notFound);
    }
    
    /**
     * POST a User
     */
    public Mono<ServerResponse> postUser(ServerRequest request) {
        Mono<User> customer = request.bodyToMono(User.class);
        return ServerResponse.ok().build(userService.saveUser(customer));
    }
    
    /**
     * PUT a User
     */
    public Mono<ServerResponse> putUser(ServerRequest request) {
        // parse id from path-variable
        long customerId = Long.valueOf(request.pathVariable("userId"));

        // get customer data from request object
        Mono<User> customer = request.bodyToMono(User.class);

        // get customer from repository 
        Mono<User> responseMono = userService.putUser(customerId, customer);

        // build response
        return responseMono
            .flatMap(cust -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromObject(cust)));
    }
    
    /**
     * DELETE a User
     */
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        // parse id from path-variable
        long customerId = Long.valueOf(request.pathVariable("userId"));

        // get customer from repository 
        Mono<String> responseMono = userService.deleteUser(customerId);

        // build response
        return responseMono
            .flatMap(strMono -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromObject(strMono)));
    }

}
