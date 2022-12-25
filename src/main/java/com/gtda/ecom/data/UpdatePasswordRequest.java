package com.gtda.ecom.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordRequest {
  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 8)
  private String password;
  
  @NotBlank
  @Size(min = 8)
  private String confirmPassword;
}
