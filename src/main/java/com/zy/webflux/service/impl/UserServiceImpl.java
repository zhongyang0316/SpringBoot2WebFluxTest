package com.zy.webflux.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zy.webflux.model.User;
import com.zy.webflux.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final Map<Long, User> userDb = new HashMap<Long, User>();
	
	@PostConstruct
	public void init() {
		this.userDb.put(Long.valueOf(1), new User(Long.valueOf(1), "xiaoming", 24));
		this.userDb.put(Long.valueOf(2), new User(Long.valueOf(2), "xiaohong", 25));
	}

	@Override
	public Flux<User> getAll() {
		return Flux.fromIterable(this.userDb.values());
	}

	@Override
	public Mono<User> getUserByUserId(Long userId) {
		return Mono.justOrEmpty(userDb.get(userId));
	}

	@Override
	public Mono<Void> saveUser(Mono<User> monoUser) {
		Mono<User> userMono = monoUser.doOnNext(user -> {
            // do post
			userDb.put(user.getUserId(), user);

            // log on console
            this.logger.info("########### POST:{}" + user);
        });

		return userMono.then();
	}

	@Override
	public Mono<User> putUser(Long userId, Mono<User> monoUser) {
		Mono<User> userMono = monoUser.doOnNext(user -> {
            // reset user.Id
            user.setUserId(userId);

            // do put
            userDb.put(userId, user);

            // log on console
            this.logger.info("########### PUT:{}" + user);
        });

		return userMono;
	}

	@Override
	public Mono<String> deleteUser(Long id) {
		// delete processing
		userDb.remove(id);
        return Mono.just("Delete Succesfully!");
	}

}
