package com.zy.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.zy.webflux"})
public class WebFluxApplication {
	
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(WebFluxApplication.class, args);
		System.out.println(ctx.getId() + " started!");
	}
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebFluxApplication.class);
	}

}
