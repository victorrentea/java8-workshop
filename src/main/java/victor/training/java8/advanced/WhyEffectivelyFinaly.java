package victor.training.java8.advanced;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WhyEffectivelyFinaly {
   public static void main(String[] args) {

      List<Integer> numbers = IntStream.range(0, 10).boxed().collect(Collectors.toList());
//
//      int sum = 0;
      AtomicInteger ii = new AtomicInteger();

      numbers.stream().forEach(number -> {
//         sum += number;
         ii.addAndGet(number);
      });

      System.out.println(ii);

      int sum = numbers.stream().mapToInt(i -> i).sum();
      System.out.println(sum);

//      Supplier<Integer> inc = createIncrementer();
//      inc.get();
//      inc.get();
//      inc.get();
//      inc.get();
//   }
//
//   public static Supplier<Integer> createIncrementer() {
//      int x = 0;
//      return new Supplier<Integer>() {
//         @Override
//         public Integer get() {
//            return x + 1;
//         }
//      };
   }

}
