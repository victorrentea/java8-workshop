package victor.training.java8.advanced;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WhyEffectivelyFinaly {
   public static void main(String[] args) {

      List<Integer> numbers = IntStream.range(0, 10).boxed().collect(Collectors.toList());


      AtomicInteger integer = new AtomicInteger();
      Integer sum = numbers.stream()
          .filter(n -> n % 2 == 1)

          .mapToInt(n -> n).sum(); // FP

//          .reduce(0, Integer::sum); // FP

//          .forEach(n -> integer.addAndGet(n)); // hacking around the effectively final and changing on the heap

//          .forEach(n -> sum += n); // not compiling

      System.out.println(integer.get());

      Supplier<Integer> seqSupplier = method();
      // the stack of method () WAS DESTROYED at this line
      System.out.println(seqSupplier.get());
      System.out.println(seqSupplier.get());
      System.out.println(seqSupplier.get());
   }

   public static Supplier<Integer> method() {
       int x = 0;
       return () -> x; // ++ on what varaible ? !!
   }

}
