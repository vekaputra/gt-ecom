package com.gtda.ecom.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtda.ecom.customer.Customer;
import com.gtda.ecom.customer.CustomerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  CustomerRepository customerRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Customer> customer = customerRepository.findFirstByEmail(email);
    if (!customer.isPresent()) {
      new UsernameNotFoundException("User Not Found with email: " + email);
    }

    return UserDetailsImpl.build(customer.get());
  }
}