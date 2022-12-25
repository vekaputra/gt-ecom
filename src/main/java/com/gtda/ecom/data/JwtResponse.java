package com.gtda.ecom.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
  private String accessToken;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String email;

  public JwtResponse(String accessToken, Long id, String username, String email) {
    this.accessToken = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
  }
}
