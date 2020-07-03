package victor.training.java8.stream.parallel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ParallelStreamPlay {
   private static final Logger log = LoggerFactory.getLogger(ParallelStreamPlay.class);

   public static void main(String[] args) {
      List<Integer> numbers = IntStream.range(1, 11) // IntStream
          .boxed() // Stream<Integer>
          .collect(toList());

      numbers.parallelStream()
          .filter(n -> {
             log.info("Filter " + n);
             return n % 2 == 1;
          })
          .map(n -> {
             log.info("Map " + n);
             return n * n;
          })
          .forEach(System.out::println);
   }
}
