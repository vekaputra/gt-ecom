package com.gtda.ecom.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
  @NotBlank(message = "email must be filled")
  @Email()
  private String email;
  
  @NotBlank(message = "fullName must be filled")
  private String fullName;

  @NotBlank(message = "password must be filled")
  @Size(min = 8, message = "password must be more than 8 characters")
  private String password;
  
  @NotBlank(message = "confirmPassword must be filled")
  @Size(min = 8, message = "confirmPassword must be more than 8 characters")
  private String confirmPassword;
}
