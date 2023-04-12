package com.ecomerce.android;

import com.ecomerce.android.config.uploadFile.IStorageService;
import com.ecomerce.android.config.uploadFile.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.ecomerce.android.controller.ProductController;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class EcomerceAdrBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomerceAdrBeApplication.class, args);
	}

	@Bean
	CommandLineRunner init(IStorageService storageService) {
		return (args -> {
			storageService.init();
		});
	}

}
