package com.example.demo.tenants.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CustomerDTO extends RepresentationModel<CustomerDTO> {
    private UUID id;
    private String name;
    private String shortCode;
    private Integer number;
    private boolean isActive;
    private CustomerCodeDTO code;
    private Set<TenantDTO> tenants = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public CustomerCodeDTO getCode() {
        return code;
    }

    public void setCode(CustomerCodeDTO code) {
        this.code = code;
    }

    public Set<TenantDTO> getTenants() {
        return tenants;
    }

    public void setTenants(Set<TenantDTO> tenants) {
        this.tenants = tenants;
    }
}