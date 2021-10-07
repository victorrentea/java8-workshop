package victor.training.java8.advanced;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.function.BiFunction;
import java.util.function.Function;

enum MovieType {
   REGULAR(FunctionsOnEnums::computeRegularPrice),
   NEW_RELEASE(FunctionsOnEnums::computeNewReleasePrice),
   CHILDREN(FunctionsOnEnums::computeChildrenPrice);

   public final BiFunction<FunctionsOnEnums, Integer, Integer> priceFunction;

   MovieType(BiFunction<FunctionsOnEnums, Integer, Integer> priceFunction) {
      this.priceFunction = priceFunction;
   }
}

public class FunctionsOnEnums {

   public static void main(String[] args) {
     new FunctionsOnEnums().play();
   }

   private void play() {
      System.out.println(computePrice(MovieType.REGULAR, 2));
      System.out.println(computePrice(MovieType.NEW_RELEASE, 2));
      System.out.println(computePrice(MovieType.CHILDREN, 2));
   }

   public int computePrice(MovieType type, int days) {
//      switch (type) {
//         case REGULAR:
//            return computeRegularPrice(days);
//         case NEW_RELEASE:
//            return computeNewReleasePrice(days);
//         case CHILDREN:
//            return computeChildrenPrice(days);
//         default:
//            throw new IllegalStateException("Unexpected value: " + type);
//      }

      return type.priceFunction.apply(this, days);
   }

//   @Value("${}")
//   int paramFromFile;
//   @Autowired

   public int computeChildrenPrice(int days) {
      return 5;
   }

   public int computeNewReleasePrice(int days) {
      return days * 2;
   }

   public int computeRegularPrice(int days) {
      return days + 1;
   }

}
