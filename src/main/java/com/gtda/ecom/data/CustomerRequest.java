package com.gtda.ecom.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
  private String email;
  private String fullName;
  private String password;
}
