package com.gtda.ecom.customer;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gtda.ecom.data.CustomerRequest;
import com.gtda.ecom.data.SignUpRequest;
import com.gtda.ecom.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "forgot_password_token", nullable = true)
    @JsonIgnore
    private String forgotPasswordToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "verified_at", nullable = true, columnDefinition = "TIMESTAMPTZ")
    private Date verifiedAt;

    public Customer(CustomerRequest req, String hashedPassword) {
        this.email = req.getEmail();
        this.fullName = req.getFullName();
        this.password = hashedPassword;
    }

    public Customer(SignUpRequest req, String hashedPassword) {
        this.email = req.getEmail();
        this.fullName = req.getFullName();
        this.password = hashedPassword;
    }
}