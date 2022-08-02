package victor.training.java.advanced;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Component
class PriceService {
   @Value("${new.release.factor}")
   private int newReleaseFactor = 2;

   public int computePrice(MovieType type, int days) {
      return type.priceAlgo.apply(this,days);
   }

   public int computeRegularPrice(int days) {
//      if ()
//         for
//            if
//            if
      return days + 1;
   }
   public int computeNewReleasePrice(int days) {
      return days * newReleaseFactor;
   }
   public int computeEldersPrice(int days) {
      return days / 2;
   }

   public int computeChildrenPrice(int days) {
      return 5;
   }
}

enum MovieType {
   REGULAR(PriceService::computeRegularPrice),
   NEW_RELEASE(PriceService::computeNewReleasePrice),
   CHILDREN(PriceService::computeChildrenPrice),
   ELDERS(PriceService::computeEldersPrice);
   public final BiFunction<PriceService, Integer, Integer> priceAlgo;

   MovieType(BiFunction<PriceService, Integer, Integer> priceAlgo) {
      this.priceAlgo = priceAlgo;
   }
}

@Service
public class Switch {
   @Value("${children.price}")
   private int childrenPrice = 5; // pretend Spring is ON

   // @see tests
//   public static int computePrice(MovieType type, int days) {
//      return type.priceAlgo.apply(days);
//      //      switch (type) {
//      //         case REGULAR:
//      //            return days + 1;
//      //         case NEW_RELEASE:
//      //            return days * 2;
//      //         case CHILDREN:
//      //            return 5;
//      //
//      //         default:
//      //            throw new IllegalStateException("Unexpected value: " + type);
//      //      }
//   }

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