package com.netpoint.clinicapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ClinicappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicappApplication.class, args);
	}

}
