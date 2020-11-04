package com.dbspaceproject.dbconnection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
public class DbconnectionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbconnectionServiceApplication.class, args);
	}

}
