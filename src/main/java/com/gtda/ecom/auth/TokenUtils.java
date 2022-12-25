package com.gtda.ecom.auth;

import java.util.Random;

public class TokenUtils {
  public static String generateRandomStringToken() {
    int leftLimit = 97; // 'a'
    int rightLimit = 122; // 'z'
    int targetStringLength = 32;
    Random random = new Random();
    StringBuilder buffer = new StringBuilder(targetStringLength);
    for (int i = 0; i < targetStringLength; i++) {
        int randomLimitedInt = leftLimit + (int) 
          (random.nextFloat() * (rightLimit - leftLimit + 1));
        buffer.append((char) randomLimitedInt);
    }
    return buffer.toString();
  }
}
