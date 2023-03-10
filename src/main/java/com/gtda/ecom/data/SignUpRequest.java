package com.gtda.ecom.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
  @NotBlank()
  @Email()
  private String email;
  
  @NotBlank()
  private String fullName;

  @NotBlank()
  @Size(min = 8)
  private String password;
  
  @NotBlank()
  @Size(min = 8)
  private String confirmPassword;
}
