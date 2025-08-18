package com.example.demo.tenants.controller;

import com.example.demo.tenants.dto.CustomerDTO;
import com.example.demo.tenants.dto.DtoMapper;
import com.example.demo.tenants.dto.TenantDTO;
import com.example.demo.tenants.model.Customer;
import com.example.demo.tenants.service.CustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping({ "/api/customers", "/api/customers/" })
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    private CustomerDTO addLinks(CustomerDTO dto) {
        dto.add(linkTo(methodOn(CustomerController.class).getCustomer(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(CustomerController.class).listCustomerTenants(dto.getId())).withRel("tenants"));
        dto.add(linkTo(methodOn(CustomerController.class).activateCustomer(dto.getId())).withRel("activate"));
        dto.add(linkTo(methodOn(CustomerController.class).deactivateCustomer(dto.getId())).withRel("deactivate"));
        return dto;
    }

    @GetMapping
    public List<CustomerDTO> listCustomers(@RequestParam(required = false) Boolean active) {
        return customerService.getAllCustomers().stream()
                .filter(customer -> active == null || customer.isActive() == active)
                .map(DtoMapper::toDto)
                .map(this::addLinks)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = DtoMapper.toEntity(customerDTO);
        customerService.saveCustomer(customer);

        return addLinks(DtoMapper.toDto(customer));
    }

    @GetMapping({ "/{customerId}", "/{customerId}/" })
    public CustomerDTO getCustomer(@PathVariable UUID customerId) {
        Customer customer = findCustomerById(customerId);
        return addLinks(DtoMapper.toDto(customer));
    }

    @PutMapping({ "/{customerId}", "/{customerId}/" })
    public CustomerDTO updateCustomer(@PathVariable UUID customerId, @RequestBody CustomerDTO customerDTO) {
        Customer existingCustomer = findCustomerById(customerId);

        // Update only mutable fields
        existingCustomer.setName(customerDTO.getName());
        if (customerDTO.isActive()) {
            existingCustomer.activate();
        } else {
            existingCustomer.deactivate();
        }

        customerService.saveCustomer(existingCustomer);
        return addLinks(DtoMapper.toDto(existingCustomer));
    }

    @PostMapping({ "/{customerId}/activate", "/{customerId}/activate/" })
    public ResponseEntity<Void> activateCustomer(@PathVariable UUID customerId) {
        Customer customer = findCustomerById(customerId);
        customer.activate();
        customerService.saveCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @PostMapping({ "/{customerId}/deactivate", "/{customerId}/deactivate/" })
    public ResponseEntity<Void> deactivateCustomer(@PathVariable UUID customerId) {
        Customer customer = findCustomerById(customerId);
        
        customer.deactivate();
        customerService.saveCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @GetMapping({ "/{customerId}/tenants", "/{customerId}/tenants/" })
    public List<TenantDTO> listCustomerTenants(@PathVariable UUID customerId) {
        Customer customer = findCustomerById(customerId);

        return customer.getTenants().stream()
                .map(DtoMapper::toDto)
                .map(tenantDto -> {
                    tenantDto.add(linkTo(methodOn(TenantController.class).getTenant(tenantDto.getId())).withSelfRel());
                    return tenantDto;
                })
                .collect(Collectors.toList());
    }

    private Customer findCustomerById(UUID customerId) {
        return customerService.findCustomerById(customerId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer not found with id: " + customerId));
    }
}