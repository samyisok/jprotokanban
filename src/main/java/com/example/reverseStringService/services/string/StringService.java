package com.example.reverseStringService.services.string;

import org.springframework.stereotype.Service;

@Service
public class StringService {
  public String reverseString(String string) {
    String[] arr = string.split("");
    StringBuilder sb = new StringBuilder();

    for (int i = arr.length - 1; i >= 0; i--) {
      sb.append(arr[i]);
    }

    return sb.toString();
  }
}
