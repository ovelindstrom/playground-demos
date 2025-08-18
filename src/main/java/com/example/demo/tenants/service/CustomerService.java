package com.example.demo.tenants.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.tenants.model.Customer;

public interface CustomerService {

    public List<Customer> getAllCustomers();

    public void saveCustomer(Customer customer);

    public Optional<Customer> findCustomerById(UUID customerId);

}
