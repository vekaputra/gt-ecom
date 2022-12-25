package com.gtda.ecom.customer;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findFirstByEmail(String email);
  Optional<Customer> findFirstByEmailAndForgotPasswordToken(String email, String forgotPasswordToken);
  @Query(value = "SELECT * FROM customers c WHERE c.last_login_at >= ?1", nativeQuery = true)
  List<Customer> findAllActiveCustomersSince(Date lastLoginAt);
  Boolean existsByEmail(String email);
}
