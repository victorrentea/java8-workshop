package victor.training.java8.advanced;


import org.springframework.beans.factory.annotation.Value;

import java.util.function.BiFunction;
import java.util.function.Function;

enum MovieType {
   REGULAR(FunctionsOnEnums::computeRegularPrice),
   NEW_RELEASE(FunctionsOnEnums::computeNewReleasePrice),
   CHILDREN(FunctionsOnEnums::computeChildrenPrice)
   ;
   private BiFunction<FunctionsOnEnums, Integer,Integer> priceComputing;

   MovieType(BiFunction<FunctionsOnEnums, Integer,Integer> priceComputing) {
      this.priceComputing = priceComputing;
   }
   public BiFunction<FunctionsOnEnums, Integer, Integer> getPriceComputing() {
      return priceComputing;
   }
}

public class FunctionsOnEnums {
//   Map<MovieType, Function<Integer, Integer>>

   public static void main(String[] args) {
      FunctionsOnEnums obj = new FunctionsOnEnums();
      System.out.println(obj.computePrice(MovieType.REGULAR, 2));
      System.out.println(obj.computePrice(MovieType.NEW_RELEASE, 2));
      System.out.println(obj.computePrice(MovieType.CHILDREN, 2));
   }

   public int computePrice(MovieType type, int days) {
      return type.getPriceComputing().apply(this, days);
   }

   public int computeChildrenPrice(int days) {
      return 5;
   }

   {
//      Object o = (Function<Integer,Integer>) FunctionsOnEnums::computeChildrenPrice;
      Object o = (BiFunction<FunctionsOnEnums, Integer,Integer>) FunctionsOnEnums::computeNewReleasePrice;
      BiFunction<FunctionsOnEnums, Integer,Integer> o2 =  FunctionsOnEnums::computeNewReleasePrice;
   }

   @Value("${new.release.price.factor}")
   int factor;

   public int computeNewReleasePrice(int days) {
      return days * factor;
   }

   public int computeRegularPrice(int days) {
      return days + 1;
   }

}
