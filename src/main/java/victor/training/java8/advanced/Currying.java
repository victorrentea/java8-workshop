package victor.training.java8.advanced;

import io.vavr.Function1;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Currying {

   public static void main(String[] args) {

      int sum = sum(1,2);

      BiFunction<Integer, Integer, Integer> ss = Currying::sum;
      Function<Integer, Integer> sumWith1 = partiallyApply(ss, 1);
      System.out.println(sumWith1.apply(2));
      System.out.println(sumWith1.apply(3));

//      val sum1 = sum 1 _

      Function<Integer, Integer> maxWith0 = partiallyApply(Integer::max, 0);
      System.out.println(maxWith0.apply(-5));
      System.out.println(maxWith0.apply(5));

      Object firstMiracle = (Supplier<Wine>) Wine::new;
      Object firstMiracle2 = (Function1<String,Wine>) (Function<String, Wine>) Wine::new;

   }

   private static <A,B,R> Function<A, R> partiallyApply(BiFunction<B, A, R> function, B firstArg) {
      return secondArg -> function.apply(firstArg, secondArg);
   }

   private static Integer sum(Integer a, Integer b) {
      return a + b;
   }
}

class Wine {
   String type = "RED";
   public Wine() {}

   public Wine(String type) {
      this.type = type;
   }
}
