package victor.training.java8.advanced;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Currying {

   public static void main(String[] args) {

      int sum = sum(1,2);

      Function<Integer, Integer> sumWith1 = partiallyApply(Currying::sum, 1);
      System.out.println(sumWith1.apply(2));
      System.out.println(sumWith1.apply(3));

      Function<Integer, Integer> maxWith0 = partiallyApply(Integer::max, 0);
      System.out.println(maxWith0.apply(-5));
      System.out.println(maxWith0.apply(5));
   }

   private static <A,B,R> Function<A, R> partiallyApply(BiFunction<B, A, R> function, B firstArg) {
      return secondArg -> function.apply(firstArg, secondArg);
   }

   private static Integer sum(Integer a, Integer b) {
      return a + b;
   }
}
