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
      int v = switch (type) {
         case REGULAR -> days + 1;
         case NEW_RELEASE -> days * 2;
         case CHILDREN -> 5;
         case BABACI ->  -1;
      };
      return v;
      // opinions?
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