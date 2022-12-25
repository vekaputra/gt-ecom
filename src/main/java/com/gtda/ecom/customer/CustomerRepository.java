package com.gtda.ecom.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findFirstByEmail(String email);
  Boolean existsByEmail(String email);
}
