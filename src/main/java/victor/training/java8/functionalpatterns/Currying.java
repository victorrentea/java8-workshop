package victor.training.java8.functionalpatterns;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Currying {

   public static void main(String[] args) {

      BiFunction<Integer, Integer, Integer> sum = Integer::sum;


      Function<Integer, Integer> adunaCu1 = partialApplyOData(sum, 1);

      System.out.println(adunaCu1.apply(2));


      Function<Integer, Integer> maxCu2 = partialApplyOData(Integer::max, 2);

      System.out.println(maxCu2.apply(5));
      System.out.println(maxCu2.apply(1));

//      return a + b
   }

   public static Function<Integer, Integer> partialApplyOData(BiFunction<Integer, Integer, Integer> bi, int arg1) {
      return arg2 -> bi.apply(arg1, arg2);
   }
}
