package victor.training.java.advanced;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

enum MovieType {
   REGULAR(days -> days + 1),
   NEW_RELEASE(days -> days * 2),
   CHILDREN (days -> 5);

   private final PriceComputer computer;
   @FunctionalInterface
   interface PriceComputer {
      int computePrice(int days);
   }
   MovieType(PriceComputer computer) { // or a Function
      this.computer = computer;
   }
   public PriceComputer getComputer() {
      return computer;
   }
}



@Service
public class Switch {
//   @Value("${children.price}")
//   private int childrenPrice;

   // @see tests
   public static int computePrice(MovieType type, int days) {
      return type.getComputer().computePrice(days);
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