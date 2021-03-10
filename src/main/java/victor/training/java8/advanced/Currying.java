package victor.training.java8.advanced;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Currying {

   public static void main(String[] args) {
      Function<Integer, Integer> add1 = partialApply(Integer::sum, 1);
      System.out.println(add1.apply(2));



      Function<Integer, Integer> max2 = partialApply(Integer::max, 2);
      System.out.println(max2.apply(5));
      System.out.println(max2.apply(1));

   }

   public static Function<Integer, Integer> partialApply(BiFunction<Integer, Integer, Integer> f, int arg1) {
      return arg2 -> f.apply(arg1, arg2);
   }
}
