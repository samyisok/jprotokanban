package com.example.reverseStringService.services.string;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
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

  public String fizzBuzz(List<Long> list) {
    Function<Long, String> isFizz = x -> x % 3 == 0 ? "Fizz" : "";
    Function<Long, String> isBuzz = x -> x % 5 == 0 ? "Buzz" : "";
    Function<Long, String> isFizzBuzz = x -> isFizz.apply(x) + isBuzz.apply(x);
    String result =
        list.stream()
            .map(x -> isFizzBuzz.apply(x).equals("") ? String.valueOf(x)
                : isFizzBuzz.apply(x))
            .collect(Collectors.joining(", "));
    return result;
  }

}
