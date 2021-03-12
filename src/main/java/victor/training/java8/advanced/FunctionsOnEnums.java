package victor.training.java8.advanced;


import java.text.SimpleDateFormat;
import java.util.function.Function;


class PriceCalculator {
   public static int computeRegularPrice(int days) {
      return days + 1;
   }
   public static int computeNewReleasePrice(int days) {
      return days * 2;
   }
   public static int computeChildrenPrice(int days) {
      return 5;
   }
}

// 1. Problema cand e multa logica OK - cu referinte la metode statice din enum
// daca factorul "2" trebuie citit dintr-un fisier de proprietati ~ @Value
enum MovieType {
   REGULAR(PriceCalculator::computeRegularPrice),
   NEW_RELEASE(PriceCalculator::computeNewReleasePrice) ,
   CHILDREN(PriceCalculator::computeChildrenPrice)
   ;

   private final Function<Integer, Integer> priceAlgo;

   MovieType(Function<Integer, Integer> priceAlgo) {
      this.priceAlgo = priceAlgo;
   }

   public Function<Integer, Integer> getPriceAlgo() {
      return priceAlgo;
   }
}

public class FunctionsOnEnums {

   public static void main(String[] args) {

//      new SimpleDateFormat().parse("aa");
      System.out.println(computePrice(MovieType.REGULAR, 2));
      System.out.println(computePrice(MovieType.NEW_RELEASE, 2));
      System.out.println(computePrice(MovieType.CHILDREN, 2));
   }

   public static int computePrice(MovieType type, int days) {
      return type.getPriceAlgo().apply(days);
   }


}
