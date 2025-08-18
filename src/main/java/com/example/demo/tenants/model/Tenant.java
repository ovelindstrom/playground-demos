// Tenant.java
package com.example.demo.tenants.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer; // For the bi-directional relationship with Customer

    private String name;
    private String description;

    @Embedded
    private Code code;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String version;

    // Storing URI as String for JPA, conversion in getter/setter
    @JdbcTypeCode(SqlTypes.VARCHAR) // Ensure it's stored as VARCHAR
    private String targetConnectorEndpoint;

    public Tenant() {
    }

    public Tenant(String name, String description, Type type, Customer customer, String version) {
        this.name = name;
        this.description = description;
        //this.code = new Code(customer.getCode(), name);
        this.code = new Code(customer.getCode(), customer.getShortCode(), type);
        this.type = type;
        this.version = version;
        this.customer = customer;
    }

    public UUID getId() {
        return id;
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

    public Code getCode() {
        return code;
    }

    public Type getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public URI getTargetConnectorEndpoint() {
        return targetConnectorEndpoint != null ? URI.create(targetConnectorEndpoint) : null;
    }

    public void setTargetConnectorEndpoint(URI legacyURI) {
        this.targetConnectorEndpoint = (legacyURI != null) ? legacyURI.toString() : null;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * To be able to "unset" the customer.
     */
    public void unlinkCustomer() {
        this.customer = null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tenant tenant = (Tenant) o;
        return Objects.equals(code, tenant.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }


    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", code=" + code +
                ", type=" + type +
                ", version='" + version + '\'' +
                ", legacyURI=" + targetConnectorEndpoint +
                '}';
    }

    // TenantKey as an @Embeddable class
    @Embeddable
    public static class Code {
        @Column(name = "tenant_code_content", unique = true) // To be used as the map key in Customer
        private String content;

        protected Code() {
        }

        // Modified constructor to accept CustomerCode and name
        protected Code(Customer.Code customerCode, String name) {
            if (customerCode == null || name == null || name.isEmpty()) {
                throw new IllegalArgumentException("CustomerCode or name cannot be null or empty");
            } else {
                content = customerCode.getContent() + "/" + toSnakeCase(name);
            }
        }

        protected Code(Customer.Code customerCode, String customerShortCode, Tenant.Type type) {
            if (customerCode == null || customerShortCode == null || type == null) {
                throw new IllegalArgumentException("CustomerCode, shortCode or type cannot be null");
            } else {
                content = customerCode.getContent() + "/" + toSnakeCase(customerShortCode + "_" + type.name().toLowerCase());
            }

        }

        public String getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Code code = (Code) o;
            return Objects.equals(content, code.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(content);
        }

        @Override
        public String toString() {
            return "Code{" +
                    "content='" + content + '\'' +
                    '}';
        }
    }

    // TenantType as an enum
    public enum Type {
        TEST,
        SANDBOX,
        HISTORY,
        PROD
    }

    private static final Pattern CAMEL_OR_PASCAL_CASE_PATTERN = Pattern.compile("(?<=[a-z0-9])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])|(?<=[a-zA-Z])(?=[0-9])|(?<=[0-9])(?=[a-zA-Z])");

    protected static String toSnakeCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String intermediate = CAMEL_OR_PASCAL_CASE_PATTERN.matcher(input).replaceAll("_").toLowerCase();
        return intermediate.replaceAll("([-_]){2,}", "_"); // Replace multiple underscores/hyphens with a single one
    }
}
