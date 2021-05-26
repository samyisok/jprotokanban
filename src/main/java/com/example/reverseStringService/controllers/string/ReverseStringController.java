package com.example.reverseStringService.controllers.string;

import java.util.Map;
import com.example.reverseStringService.services.string.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ReverseStringController {

  @Autowired
  StringService stringService;

  @GetMapping("/")
  public Map<String, String> check() {
    return Map.of("check", "ok");
  }

  @PostMapping("/string/reverse")
  public Map<String, String> reverseString(
      @RequestBody Map<String, String> inputStringMap) {

    String str = stringService.reverseString(inputStringMap.getOrDefault("string", ""));

    return Map.of("output", str);
  }
}
