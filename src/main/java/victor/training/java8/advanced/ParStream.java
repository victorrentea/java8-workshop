package victor.training.java8.advanced;


import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class ParStream {
   public static void main(String[] args) {

      List<Integer> numbers = IntStream.range(1, 10).boxed().collect(Collectors.toList());

      numbers.parallelStream()
          .forEach(n -> log.info("Vad" + n));
   }
}
