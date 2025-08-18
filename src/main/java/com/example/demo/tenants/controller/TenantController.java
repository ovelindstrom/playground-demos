package com.example.demo.tenants.controller;

import com.example.demo.tenants.dto.DtoMapper;
import com.example.demo.tenants.dto.TenantDTO;
import com.example.demo.tenants.model.Customer;
import com.example.demo.tenants.model.Tenant;
import com.example.demo.tenants.repository.TenantRepository;
import com.example.demo.tenants.service.CustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping({"/api/tenants", "/api/tenants/"})
public class TenantController {

    private final TenantRepository tenantRepository;
    private final CustomerService customerService;

    public TenantController(TenantRepository tenantRepository, CustomerService customerService) {
        this.tenantRepository = tenantRepository;
        this.customerService = customerService;
    }

    private TenantDTO addLinks(TenantDTO dto) {
        dto.add(linkTo(methodOn(TenantController.class).getTenant(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(CustomerController.class).getCustomer(dto.getCustomerId())).withRel("customer"));
        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TenantDTO createTenant(@RequestBody TenantDTO tenantDTO) {
        Customer customer = customerService.findCustomerById(tenantDTO.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                        "Customer not found with id: " + tenantDTO.getCustomerId()));
        
        Tenant tenant = DtoMapper.toEntity(tenantDTO, customer);
        customer.addTenant(tenant);
        customerService.saveCustomer(customer);
        
        return addLinks(DtoMapper.toDto(tenant));
    }

    @GetMapping({"/{tenantId}", "/{tenantId}/"})
    public TenantDTO getTenant(@PathVariable UUID tenantId) {
        Tenant tenant = findTenantById(tenantId);
        return addLinks(DtoMapper.toDto(tenant));
    }

    @PutMapping({"/{tenantId}", "/{tenantId}/"})
    public TenantDTO updateTenant(@PathVariable UUID tenantId, @RequestBody TenantDTO tenantDTO) {
        Tenant existingTenant = findTenantById(tenantId);
        
        // Update mutable fields
        existingTenant.setName(tenantDTO.getName());
        existingTenant.setDescription(tenantDTO.getDescription());
        existingTenant.setVersion(tenantDTO.getVersion());
        if (tenantDTO.getTargetConnectorEndpoint() != null) {
            existingTenant.setTargetConnectorEndpoint(
                java.net.URI.create(tenantDTO.getTargetConnectorEndpoint())
            );
        }
        
        tenantRepository.save(existingTenant);
        return addLinks(DtoMapper.toDto(existingTenant));
    }

    @DeleteMapping({"/{tenantId}", "/{tenantId}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTenant(@PathVariable UUID tenantId) {
        Tenant tenant = findTenantById(tenantId);
        Customer customer = tenant.getCustomer();
        if (customer != null) {
            customer.removeTenant(tenant);
            customerService.saveCustomer(customer);
        }
    }

    private Tenant findTenantById(UUID tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                        "Tenant not found with id: " + tenantId));
    }
}