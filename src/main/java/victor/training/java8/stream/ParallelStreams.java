package victor.training.java8.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ParallelStreams {
   private static final Logger log = LoggerFactory.getLogger(ParallelStreams.class);

   public static void main(String[] args) {
      List<Integer> numbers = IntStream.range(0, 10).boxed().collect(toList());

      List<Integer> result = numbers.parallelStream()
          .filter(n -> {
             System.out.println(Thread.currentThread().getName() + " Filtrez " + n);
             return n % 2 == 1;
          })
          .distinct()
          .map(n -> {
             System.out.println(Thread.currentThread().getName() + " Square " + n);
             return n * n;
          })
          .collect(toList());

      System.out.println(Thread.currentThread().getName() + " Result: " + result);

   }
}
