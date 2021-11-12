package victor.training.java8.advanced;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Currying {
   // please skip me !
   public static void main(String[] args) {
      BiFunction<Integer, Integer, Integer> biF = (Integer a, Integer b) -> a + b;
      Function<Integer, Integer> add1 = partialApply(biF, 1);


      System.out.println(add1.apply(2));

      Function<Integer, Integer> max2 = partialApply(Integer::max, 2);
      System.out.println(max2.apply(5));
      System.out.println(max2.apply(1));
   }

   public static Function<Integer, Integer> partialApply(BiFunction<Integer, Integer, Integer> f, int arg1) {
      return arg2 -> f.apply(arg1, arg2);
   }
}
