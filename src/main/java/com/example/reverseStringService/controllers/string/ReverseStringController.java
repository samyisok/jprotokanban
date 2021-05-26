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

  @GetMapping("/checkauth")
  public Map<String, String> checkauth() {
    return Map.of("check", "auth");
  }

  @PostMapping("/string/reverse")
  public Map<String, String> reverseString(
      @RequestBody Map<String, String> inputStringMap) {

    String str = stringService.reverseString(inputStringMap.getOrDefault("string", ""));

    return Map.of("output", str);
  }
}
