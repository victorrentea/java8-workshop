package victor.training.java8.advanced;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Currying {

   public static void main(String[] args) {

      BiFunction<Integer, Integer, Integer> doiParam = Integer::sum;

      Function<Integer, Integer> aduna1 = aplicaPartial(doiParam, 1);

      System.out.println(aduna1.apply(2));

      System.out.println(aduna1.apply(4));


      Function<BigDecimal, BigDecimal> bigPlus1 = aplicaPartial(BigDecimal::multiply, BigDecimal.valueOf(2));

      System.out.println(bigPlus1.apply(BigDecimal.TEN));
   }

   private static <T,R> Function<T, R> aplicaPartial
            (BiFunction<T, T, R> doiParam, T param1) {
      return n -> doiParam.apply(param1,n);
   }
}
