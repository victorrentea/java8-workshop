package victor.training.java.advanced;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;
import java.util.function.Function;

enum MovieType {
   REGULAR(Switch::regularPrice),
   NEW_RELEASE(Switch::newReleasePrice),
   CHILDREN(Switch::childrenPrice),
   ELDERS((a, days) -> 1)
   ;

   public final BiFunction<Switch, Integer, Integer> priceAlgo;

   MovieType(BiFunction<Switch, Integer,Integer> priceAlgo) {
      this.priceAlgo = priceAlgo;
   }
}

@Service
public class Switch {
   @Value("${children.price}")
   private int childrenPrice; // pretend Spring is ON

   // @see tests
   public int computePrice(MovieType type, int days) {
      return type.priceAlgo.apply(this, days);
//      switch (type) {
//         case REGULAR:
//            return regularPrice(days);
//         case NEW_RELEASE:
//            return newReleasePrice(days);
//         case CHILDREN:
//            return childrenPrice(days);
//         default:
//            throw new IllegalStateException("Unexpected value: " + type);
//      }
   }

   public int childrenPrice(int days) {
      return 5;
   }

   public int newReleasePrice(int days) {
      return days * 2;
   }

   public int regularPrice(int days) {
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