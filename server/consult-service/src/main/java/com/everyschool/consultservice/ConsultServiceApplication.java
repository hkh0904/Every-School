package com.everyschool.consultservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ConsultServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultServiceApplication.class, args);
	}

}
