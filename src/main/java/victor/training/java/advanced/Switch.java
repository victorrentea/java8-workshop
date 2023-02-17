package victor.training.java.advanced;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

enum MovieType {
   REGULAR,
   NEW_RELEASE,
   CHILDREN,
   BABACI,
}

@Service
public class Switch {
   @Value("${children.price}")
   private int childrenPrice = 5; // pretend Spring is ON
   // @see tests
   public static int computePrice(MovieType type, int days) {
      // PROBLEMA switchului este ca atunci cand adaugi un tip nou de film, e posibil sa uiti sa adaugi case: necesar aici
      switch (type) {
         case REGULAR:
            return days + 1;
         case NEW_RELEASE:
            return days * 2;
         case CHILDREN:
            return 5;
         default:
            throw new IllegalStateException("Unexpected value: " + type);
      }
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