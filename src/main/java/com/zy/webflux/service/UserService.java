package com.zy.webflux.service;

import com.zy.webflux.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
	
	Flux<User> getAll();
	
	Mono<User> getUserByUserId(Long userId);
	
	Mono<Void> saveUser(Mono<User> monoUser);
	
	Mono<User> putUser(Long userId, Mono<User> monoUser);
	
	Mono<String> deleteUser(Long id);

}
