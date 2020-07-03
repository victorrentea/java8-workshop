package victor.training.java8.stream.parallel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

//      List<Integer> list = new ArrayList<>();
      numbers.parallelStream()
          .filter(n -> {
             log.info("Filter " + n);
             return n % 2 == 1;
          })
//          .distinct()
          .sorted()
          .map(n -> {
             log.info("Map " + n);
             return n * n;
          })
          .parallel()
          .sequential()
          .filter(n -> n > 10)
          .findAny() // oricare worker gaseste primu, ala e.
//          .findFirst() // primul in ordinea initiala e intors
         .ifPresent(System.out::println);
//          .forEach(x -> {
//             log.info("Out " + x);
//             list.add(x);
//          });
//          .collect(toList());

//      System.out.println(list);
   }
}
