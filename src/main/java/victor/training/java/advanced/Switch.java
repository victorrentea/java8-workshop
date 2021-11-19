package victor.training.java.advanced;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;
import java.util.function.Function;

enum MovieType {
   REGULAR(Switch::computeRegularPrice),
   NEW_RELEASE(Switch::computeNewReleasePrice),
   CHILDREN (Switch::computeChildrenPrice),
   ELDERS(null)
   ;

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
   public int computePrice(MovieType type, Integer days) {
//      return type.getComputer().apply(this, days);
//      return switch (type) {
//         case REGULAR -> days + 1;
//         case NEW_RELEASE -> days * 2;
//         case CHILDREN -> 5;
//      };
      switch (type) {
         case REGULAR:
            return days + 1;
         case NEW_RELEASE:
            return days * 2;
         case CHILDREN:
            return 5;
         default:
            throw new MySpecialCaseNotFoundException();
      }
   }

   public void auditDelayReturn(MovieType movieType, int delayDays) {
      switch (movieType) {
         case REGULAR,CHILDREN -> forRegular(delayDays);
         case NEW_RELEASE -> System.out.println("CRITICAL: new release return delayed by " + delayDays);
      }
   }

   private void forRegular(int delayDays) {
      System.out.println("Regular delayed by " + delayDays);
      System.out.println("Regular delayed by " + delayDays);
      System.out.println("Regular delayed by " + delayDays);
      System.out.println("Regular delayed by " + delayDays);
      System.out.println("Regular delayed by " + delayDays);
   }
}


class MySpecialCaseNotFoundException extends RuntimeException {
}