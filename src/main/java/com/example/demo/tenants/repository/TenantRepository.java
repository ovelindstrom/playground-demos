package com.example.demo.tenants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.tenants.model.Tenant;

import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID> {
}