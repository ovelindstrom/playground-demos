package com.example.demo.tenants.dto;

import java.net.URI;

import com.example.demo.tenants.model.Customer;
import com.example.demo.tenants.model.Tenant;

public class DtoMapper {
    public static CustomerDTO toDto(Customer customer) {
        if (customer == null) return null;
        
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setShortCode(customer.getShortCode());
        dto.setActive(customer.isActive());
        
        if (customer.getCode() != null) {
            CustomerCodeDTO codeDto = new CustomerCodeDTO();
            codeDto.setContent(customer.getCode().getContent());
            dto.setCode(codeDto);
        }
        
        customer.getTenants().forEach(tenant -> 
            dto.getTenants().add(toDto(tenant))
        );
        
        return dto;
    }

    public static TenantDTO toDto(Tenant tenant) {
        if (tenant == null) return null;
        
        TenantDTO dto = new TenantDTO();
        dto.setId(tenant.getId());
        dto.setName(tenant.getName());
        dto.setDescription(tenant.getDescription());
        dto.setType(tenant.getType());
        dto.setVersion(tenant.getVersion());
        
        if (tenant.getTargetConnectorEndpoint() != null) {
            dto.setTargetConnectorEndpoint(tenant.getTargetConnectorEndpoint().toString());
        }
        
        if (tenant.getCode() != null) {
            TenantCodeDTO codeDto = new TenantCodeDTO();
            codeDto.setContent(tenant.getCode().getContent());
            dto.setCode(codeDto);
        }
        
        if (tenant.getCustomer() != null) {
            dto.setCustomerId(tenant.getCustomer().getId());
        }
        
        return dto;
    }

    public static Customer toEntity(CustomerDTO dto) {
        if (dto == null) return null;
        
        Customer customer = new Customer(
            dto.getName(),
            dto.getShortCode(),
            dto.getNumber()
        );
        
        if (dto.isActive()) {
            customer.activate();
        }
        
        return customer;
    }

    public static Tenant toEntity(TenantDTO dto, Customer customer) {
        if (dto == null) return null;
        
        Tenant tenant = new Tenant(
            dto.getName(),
            dto.getDescription(),
            dto.getType(),
            customer,
            dto.getVersion()
        );
        
        if (dto.getTargetConnectorEndpoint() != null) {
            tenant.setTargetConnectorEndpoint(URI.create(dto.getTargetConnectorEndpoint()));
        }
        
        return tenant;
    }
}