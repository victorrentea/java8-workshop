package victor.training.java.advanced;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;
import java.util.function.Function;

enum MovieType {
   REGULAR(Switch::computeRegularPrice),
   NEW_RELEASE(Switch::computeNewReleasePrice),
   CHILDREN (Switch::computeChildrenPrice);

   private final BiFunction<Switch, Integer, Integer> computer;
   MovieType(BiFunction<Switch, Integer, Integer> computer) { // or a Function
      this.computer = computer;
   }

   public BiFunction<Switch, Integer, Integer> getComputer() {
      return computer;
   }
}



@Service
public class Switch {
   @Value("${children.price}")
   public int childrenPrice = 5;

   public int computeRegularPrice(int days) {
      return days + 1;
   }

   public int computeNewReleasePrice(int days) {
      return days * 2;
   }

   public int computeChildrenPrice(int days) {
      return childrenPrice;
   }

   // @see tests
   public int computePrice(MovieType type, int days) {
      return type.getComputer().apply(this, days);
//      switch (type) {
//         case REGULAR:
//            return days + 1;
//         case NEW_RELEASE:
//            return days * 2;
//         case CHILDREN:
//            return 5;
//         default:
//            throw new IllegalStateException("Unexpected value: " + type);
//      }
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