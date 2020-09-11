package com.example.cookiedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@ComponentScan("com.example")
@SpringBootApplication
public class CookiedemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookiedemoApplication.class, args);
	}
	



}
