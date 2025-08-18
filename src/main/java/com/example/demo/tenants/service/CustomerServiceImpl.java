package com.example.demo.tenants.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.tenants.model.Customer;
import com.example.demo.tenants.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        // This method should return a list of all customers.
        // For now, we can return an empty list or implement the logic to fetch customers.
        return customerRepository.findAll();
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }


}
