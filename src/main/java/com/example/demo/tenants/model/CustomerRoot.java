package com.example.demo.tenants.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerRoot {

    private List<Customer> customers = new ArrayList<>();

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    } 
    

}
