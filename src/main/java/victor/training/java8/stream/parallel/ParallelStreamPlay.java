package victor.training.java8.stream.parallel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ParallelStreamPlay {
   public static void main(String[] args) {
      List<Integer> numbers = IntStream.range(1, 11) // IntStream
          .boxed() // Stream<Integer>
          .collect(toList());

      numbers.parallelStream()
          .filter(n -> n % 2 == 1)
          .map(n -> n * n)
          .forEach(System.out::println);
   }
}
