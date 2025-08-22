// Example Spring Boot Application class (for context)
package com.example.demo;

import com.example.demo.tenants.model.Customer;
import com.example.demo.tenants.model.Tenant;
import com.example.demo.tenants.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URI;
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

	@Bean
	@Transactional
	CommandLineRunner run(CustomerRepository customerRepository) {
		return args -> {
			// Create a Customer
			Customer customer1 = new Customer("Acme Corp INC", "ACME", 99101);
			customer1.activate();

			// Create Tenants

			Tenant tenant1 = new Tenant("ACM Test", "Test environment for Acme Group Inc", Tenant.Type.TEST, customer1,
					"24.5.0");
			tenant1.setTargetConnectorEndpoint(URI.create("http://acme-test.aarotest.com"));

			Tenant tenant2 = new Tenant("ACM Prod", "Production environment for Acme Group Inc", Tenant.Type.PROD,
					customer1, "23.3.11");
			tenant2.setTargetConnectorEndpoint(URI.create("http://acme.aarocloud.com"));

			// Add tenants to customer
			customer1.addTenant(tenant1);
			logger.info("After adding tenant1, size: {} ", customer1.getTenants().size());

			customer1.addTenant(tenant2);
			logger.info("After adding tenant2, size: " + customer1.getTenants().size());

			customerRepository.save(customer1);
			logger.info("After save, size: " + customer1.getTenants().size());

			logger.info("Saved Customer: " + customer1);
			customer1.getTenants().forEach((tenant) -> logger.info("  Tenant: " + tenant));

			// Fetch customer and check relationships
			logger.info("\n--- Fetching Customer ---");
			Customer fetchedCustomer = customerRepository.findById(customer1.getId()).orElse(null);
			if (fetchedCustomer != null) {
				logger.info("Fetched Customer: " + fetchedCustomer.getName());
				logger.info("Customer Code: " + fetchedCustomer.getCode());
				logger.info("Customer is active: " + fetchedCustomer.isActive());
				logger.info("Tenancy set size: " + fetchedCustomer.getTenants().size());
				logger.info("Tenancy Codes: " + fetchedCustomer.getTenancyCodes());

				Tenant fetchedTenantDev = fetchedCustomer.getTenancy("ACME-100-dev");
				if (fetchedTenantDev != null) {
					logger.info("Fetched Tenant (Dev): " + fetchedTenantDev.getName());
					logger.info("  Description: " + fetchedTenantDev.getDescription());
					logger.info("  Code: " + fetchedTenantDev.getCode().getContent());
					logger.info("  UUID: " + fetchedTenantDev.getId());
					logger.info("  Type: " + fetchedTenantDev.getType());
					logger.info("  Legacy URI: " + fetchedTenantDev.getTargetConnectorEndpoint());
				}

				// Removing a tenant
				// logger.info("\n--- Removing a Tenant ---");
				// fetchedCustomer.removeTenant(tenant1); // Use the original tenant object to
				// remove
				// customerRepository.save(fetchedCustomer);

				Customer reFetchedCustomer = customerRepository.findById(fetchedCustomer.getId()).orElse(null);
				if (reFetchedCustomer != null) {
					logger.info("Re-fetched Customer tenancy set size: " + reFetchedCustomer.getTenants().size());
					reFetchedCustomer.getTenants().forEach((tenant) -> logger
							.info("  Remaining Tenant: " + tenant.getName() + " with code: " + tenant.getCode()));
				}
			}
		};
	}

}