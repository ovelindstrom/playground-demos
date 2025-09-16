// Example Spring Boot Application class (for context)
package com.example.demo;

import com.example.demo.tpch.loader.TplLoader;
import com.example.demo.tpch.storage.TpchStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class TpchEclipseStoreApplication {
	private static final CountDownLatch latch = new CountDownLatch(1);
	private static final Logger logger = LoggerFactory.getLogger(TpchEclipseStoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TpchEclipseStoreApplication.class, args);

		try {
			latch.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

	// Generate a command line runner that runs TplLoader
	@Bean
	CommandLineRunner run(TpchStorage tpchStorage) {
		return args -> {
			// Only run if 'init' argument is present
			for (String arg : args) {
				if ("init".equalsIgnoreCase(arg)) {
					logger.info("### Starting TPL data loading...");
					TplLoader loader = new TplLoader(tpchStorage);
					loader.loadData();

					// Print out the current memory
					Runtime runtime = Runtime.getRuntime();
					System.out.println("Used memory: " + (runtime.totalMemory() - runtime.freeMemory()) / 1024 + " KB");
					System.out.println("Total memory: " + runtime.totalMemory() / 1024 + " KB");
					System.out.println("Max memory: " + runtime.maxMemory() / 1024 + " KB");
					
				
					
					break;


				}
			}
		};
	}

}