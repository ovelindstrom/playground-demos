package com.example.demo.tenants.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private boolean isActive = false;

    private String name;

    // These fields are part of CustomerCode, but also directly accessible
    private String shortCode;
    private Integer number;

    @Embedded
    private Code code;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private final Set<Tenant> tenants = new HashSet<>();

    public Customer() {
    }

    /**
     * Constructor for Customer. All values are taken from the Customer Object in the CMDB, .
     *
     * @param name The long name of this customer, i.e. "Acme Corporation AB"
     * @param shortCode The short name of this customer, i.e. "ACME" or "ACM"
     * @param number The customer number, usually 5 digits.
     */
    public Customer(String name, String shortCode, Integer number) {
        this.name = name;
        this.shortCode = shortCode;
        this.number = number;
        this.code = new Code(shortCode, number);
    }

    public UUID getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        isActive = true;
    }
    public void deactivate() {
        isActive = false;
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

    public Code getCode() {
        // Fallback implementation
        if (this.code == null && this.shortCode != null && this.number != null) {
            this.code = new Code(this.shortCode, this.number);
        }
        return code;
    }

    public List<String> getTenancyCodes() {
        List<String> codes = new ArrayList<>();
        for (Tenant tenant : tenants) {
            codes.add(tenant.getCode().getContent());
        }
        return codes;
    }

    public Tenant getTenancy(String tenantKeyCode) {
        for (Tenant tenant : tenants) {
            if (tenant.getCode().getContent().equals(tenantKeyCode)) {
                return tenant;
            }
        }
        return null;
    }

    public Set<Tenant> getTenants() {
        return tenants;
    }

    public void addTenant(Tenant tenant) {
        if (tenant != null) {
            tenant.setCustomer(this); // Set the bi-directional relationship, should exist in the new Tenant.
            System.out.println("Added tenant: " + tenant);
            tenants.add(tenant);
        }
    }

    public void removeTenant(Tenant tenant) {
        if (tenant != null && tenant.getCode() != null) {
            this.tenants.remove(tenant);
            tenant.unlinkCustomer(); // Unset the bi-directional relationship
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", name='" + name + '\'' +
                ", key='" + shortCode + '\'' +
                ", number=" + number +
                ", code=" + code +
                '}';
    }

    // CustomerCode as an @Embeddable class
    @Embeddable
    public static class Code {
        private final String content;

        protected Code() {
            this.content = "UNKNOWN/-1";
        }

        protected Code(String shortCode, int number) {
            if(shortCode == null ) {
                throw new IllegalArgumentException("Customer.Code shortCode cannot be null");
            }
            // Add the validation for 'number' here
            if (number <= 0) { // Using <= 0 to catch zero and negative numbers
                throw new IllegalArgumentException("Customer.Code number must be positive.");
            }
            content = shortCode.toLowerCase() + number;
        }
        public String getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Code that = (Code) o;
            return Objects.equals(content, that.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(content);
        }

        @Override
        public String toString() {
            return "Customer.Code{" +
                    "content ='" + content +
                    '}';
        }
    }
}
