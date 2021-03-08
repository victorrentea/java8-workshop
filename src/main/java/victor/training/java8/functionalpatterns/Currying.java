package victor.training.java8.functionalpatterns;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Currying {

   public static void main(String[] args) {

      BiFunction<Integer, Integer, Integer> sum = Integer::sum;


      Function<Integer, Integer> adunaCu1 = partialApplySumOData(sum, 1);

      System.out.println(adunaCu1.apply(2));

//      return a + b
   }

   public static Function<Integer, Integer> partialApplySumOData(BiFunction<Integer, Integer, Integer> bi, int arg1) {
      return arg2 -> arg1 + arg2;
   }
}
