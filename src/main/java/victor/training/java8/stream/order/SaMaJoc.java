package victor.training.java8.stream.order;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class SaMaJoc {


   public static void main(String[] args) {

      List<Integer> numbers = IntStream.range(0, 10_000000).boxed().collect(Collectors.toList());

      long t0 = System.currentTimeMillis();
      Optional<Integer> i1 = numbers.parallelStream()
          .max(Comparator.comparing(i -> {
//             log.info("" + i);
             return i;
          }));
      long t1 = System.currentTimeMillis();

      System.out.println(t1 - t0);

      System.out.println(i1);
   }
}
