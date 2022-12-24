package com.gtda.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gtda.ecom.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
