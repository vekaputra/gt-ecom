package com.gtda.ecom.data;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private List<String> errors;

  public ErrorResponse(String error) {
    this.errors = new ArrayList<String>();
    this.errors.add(error);
  }
}
