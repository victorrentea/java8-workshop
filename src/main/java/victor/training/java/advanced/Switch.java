package victor.training.java.advanced;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Function;

enum MovieType {
   REGULAR(Switch::regularPrice),
   NEW_RELEASE(Switch::newReleasePrice),
   CHILDREN(Switch::childrenPrice),
   ELDERS(null)
   ;

   private final Function<Integer, Integer> priceFunction;
   MovieType(Function<Integer, Integer> priceFunction) {
      this.priceFunction = priceFunction;
   }

   public Function<Integer, Integer> getPriceFunction() {
      return priceFunction;
   }
}

@Service
public class Switch {
   @Value("${children.price}")
   private int childrenPrice = 5; // pretend Spring is ON

   // @see tests
   public static int computePrice(MovieType type, int days) {
//      return type.getPriceFunction().apply(days);

      // in Java 17, this does not compile because of missing branch
//      return switch (type) {
//         case REGULAR -> regularPrice(days);
//         case NEW_RELEASE -> newReleasePrice(days);
//         case CHILDREN -> childrenPrice(days);
//      };

      switch (type) {
         case REGULAR:
            return regularPrice(days);
         case NEW_RELEASE:
            return newReleasePrice(days);
         case CHILDREN:
            return childrenPrice(days);
         default:
            throw new IllegalArgumentException();
      }
      // opinions?
   }

   public static int childrenPrice(int days) {
      return 5;
   }

   public static int newReleasePrice(int days) {
      return days * 2;
   }

   public static int regularPrice(int days) {
      return days + 1;
   }

   public void auditDelayReturn(MovieType movieType, int delayDays) {
      switch (movieType) {
         case REGULAR:
            System.out.println("Regular delayed by " + delayDays);break;
         case NEW_RELEASE:
            System.out.println("CRITICAL: new release return delayed by " + delayDays);break;
      }
   }
}


class MySpecialCaseNotFoundException extends RuntimeException {
}