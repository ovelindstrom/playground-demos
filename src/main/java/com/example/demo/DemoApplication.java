// Example Spring Boot Application class (for context)
package com.example.demo;

import com.example.demo.tpch.loader.TplLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class DemoApplication {
	private static final CountDownLatch latch = new CountDownLatch(1);
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		try {
			latch.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

	// Generate a command line runner that runs TplLoader
	@Bean
	CommandLineRunner run() {
		logger.info("### Starting TPL data loading...");
		System.out.println("### Starting TPL data loading...");
		return args -> {
			TplLoader loader = new TplLoader();
			loader.loadData();
		};
	}

}