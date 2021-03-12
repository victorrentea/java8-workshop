package victor.training.java8.stream.order;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class BreakPointPeStream {
   public static void main(String[] args) {

      Stream<Integer> stream = IntStream.range(1, 11).boxed();

      Comparator<Integer> compare = Integer::compare;
      List<Integer> list = stream.filter(n -> n % 2 == 0)
          .map(n -> n * 2)
          .sorted(compare.reversed())
          .collect(toList());

      System.out.println(list);
   }
}
