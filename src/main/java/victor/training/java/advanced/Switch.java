package victor.training.java.advanced;


import org.assertj.core.util.TriFunction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;
import java.util.function.Function;

enum MovieType {
   REGULAR(Switch::computeRegularPrice),
   NEW_RELEASE(Switch::computeNewReleasePrice),
   CHILDREN(Switch::computeChildrenPrice),
   BABACI(null)
//   BABACI((t,days) ->days  + 5)
   ;
   public final BiFunction<Switch, Integer,Integer> priceFunction;

   MovieType(BiFunction<Switch, Integer,Integer> priceFunction) {
      this.priceFunction = priceFunction;
   }
}

@Service
public class Switch {
   @Value("${children.price}")
   private int childrenPrice = 5; // pretend Spring is ON

   public static int computePrice(MovieType type, int days) {
      return switch (type) {
         case REGULAR -> days + 1;
         case NEW_RELEASE -> days * 2;
         case CHILDREN -> 5;
         case BABACI -> -1;
      };
      // opinions?
   }
   // @see tests
   //   public int computePrice(MovieType type, int days) {
   //      return type.priceFunction.apply(this,days); // mod geek de a evita sa uiti sa definesti behavior PER ENUM VALUE
   //   }
   //
   public int computeRegularPrice(int days) {
      return days + 1;
   }
   public int computeNewReleasePrice(int days) {
      return days * 2;
   }
   public int computeChildrenPrice(int days) {
      return childrenPrice;
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