package com.example.demo.tenants.dto;

import org.springframework.hateoas.RepresentationModel;

import com.example.demo.tenants.model.Tenant;

import java.util.UUID;

public class TenantDTO extends RepresentationModel<CustomerDTO> {
    private UUID id;
    private String name;
    private String description;
    private TenantCodeDTO code;
    private Tenant.Type type;
    private String version;
    private String targetConnectorEndpoint;
    private UUID customerId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TenantCodeDTO getCode() {
        return code;
    }

    public void setCode(TenantCodeDTO code) {
        this.code = code;
    }

    public Tenant.Type getType() {
        return type;
    }

    public void setType(Tenant.Type type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTargetConnectorEndpoint() {
        return targetConnectorEndpoint;
    }

    public void setTargetConnectorEndpoint(String targetConnectorEndpoint) {
        this.targetConnectorEndpoint = targetConnectorEndpoint;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }
}