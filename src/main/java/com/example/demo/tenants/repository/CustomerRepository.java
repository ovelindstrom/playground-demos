// CustomerRepository.java
package com.example.demo.tenants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.tenants.model.Customer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.tenants WHERE c.id = :id")
    Optional<Customer> findByIdWithTenants(@Param("id") Long id);

}