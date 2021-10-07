package victor.training.java8.advanced;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.EnumMap;
import java.util.function.BiFunction;
import java.util.function.Function;

enum MovieType {
   REGULAR(FunctionsOnEnums::computeRegularPrice),
   NEW_RELEASE(FunctionsOnEnums::computeNewReleasePrice),
   CHILDREN(FunctionsOnEnums::computeChildrenPrice),
   ELDERS(null);

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
      return switch (type) {
         case REGULAR -> computeRegularPrice(days);
         case NEW_RELEASE -> computeNewReleasePrice(days);
         case CHILDREN -> computeChildrenPrice(days);
         case ELDERS -> 0;
      };
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
