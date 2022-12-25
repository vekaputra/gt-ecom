package com.gtda.ecom.auth;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/public")
  public String publicEndpoint() {
    return "public endpoint";
  }

  @GetMapping("/authenticated")
  public String authenticatedEndpoint() {
    return "authenticated endpoint";
  }
}
