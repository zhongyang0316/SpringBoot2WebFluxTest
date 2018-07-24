package com.zy.webflux.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zy.webflux.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/annotaion/user")
public class AnnotaionUserController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final Map<Long, User> userDb = new HashMap<Long, User>();
	
	@PostConstruct
	public void init() {
		this.userDb.put(Long.valueOf(1), new User(Long.valueOf(1), "xiaoming", 24));
		this.userDb.put(Long.valueOf(2), new User(Long.valueOf(2), "xiaohong", 25));
	}
	
	/**
	 * 获取所有客户
	 * @return
	 */
	@GetMapping("")
    public Flux<User> getAll() {
        return Flux.fromIterable(userDb.entrySet().stream()
            .map(entry -> entry.getValue())
            .collect(Collectors.toList()));
	}
	
	/**
	 * 获取单个客户
	 * @param id
	 * @return
	 */
	@GetMapping("/{userId}")
    public Mono<User> getCustomer(@PathVariable(name = "userId", required = true) Long userId) {
        return Mono.justOrEmpty(userDb.get(userId));
	}
	
	/**
	 * 创建用户
	 * @param user
	 * @return
	 */
	@PostMapping("")
    public Mono<ResponseEntity<String>> postUser(@RequestBody(required = true) User user) {
		userDb.put(user.getUserId(), user);
        logger.info("########### POST:{}" + user);
        return Mono.just(new ResponseEntity<>("Post Successfully!", HttpStatus.CREATED));
	}
	
	/**
	 * 修改用户
	 * @param id
	 * @param user
	 * @return
	 */
	@PutMapping("/{userId}")
    public Mono<ResponseEntity<User>> putCustomer(@PathVariable(name = "userId", required = true) Long userId, 
    		@RequestBody(required = true) User user) {
        user.setUserId(userId);
        userDb.put(userId, user);
        System.out.println("########### PUT:{}" + user);
        return Mono.just(new ResponseEntity<>(user, HttpStatus.CREATED));
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{userId}")
    public Mono<ResponseEntity<String>> deleteMethod(@PathVariable(name = "userId", required = true) Long userId) {
        userDb.remove(userId);
        return Mono.just(new ResponseEntity<>("Delete Successfully!", HttpStatus.ACCEPTED));
	}
}
