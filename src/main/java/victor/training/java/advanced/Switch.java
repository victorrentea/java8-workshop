package victor.training.java.advanced;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;
import java.util.function.Function;

enum MovieType {
   REGULAR(Switch::computeRegularPrice),
   NEW_RELEASE(Switch::computeNewReleasePrice),
   CHILDREN(Switch::computeChildrenPrice),
   ELDER(null),

   ;
   public final BiFunction<Switch,Integer, Integer> computePriceFunction;

   MovieType(BiFunction<Switch,Integer, Integer> computePriceFunction) {
      this.computePriceFunction = computePriceFunction;
   }
}
@Service
public class Switch {
   @Value("${children.price}")
   private int childrenPrice = 5; // pretend Spring is ON

   // @see tests
   public int computePrice(MovieType type, int days) {
//      switch (type) {
//         case REGULAR: return computeRegularPrice(days);
//         case NEW_RELEASE: return computeNewReleasePrice(days);
//         case CHILDREN: return computeChildrenPrice(days);
//
//         default:
//            throw new IllegalStateException("Unexpected value: " + type);
//      }
      return switch (type) {
         case REGULAR -> computeRegularPrice(days);
         case NEW_RELEASE -> computeNewReleasePrice(days);
         case CHILDREN -> computeChildrenPrice(days);
         case ELDER -> {throw new RuntimeException("");}
      };
//      return type.computePriceFunction.apply(this, days);
   }

   public int computeRegularPrice(int days) {
      return days + 1;
   }

   public int computeChildrenPrice(int days) {
      return childrenPrice;
   }

   public int computeNewReleasePrice(int days) {
      return days * 2;
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