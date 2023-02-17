package victor.training.java.advanced;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

enum MovieType {
   REGULAR(Switch::computeRegularPrice),
   NEW_RELEASE(Switch::computeNewReleasePrice),
   CHILDREN(Switch::computeChildrenPrice),
   BABACI((s,d)->7)
//  {
//      @Override
//      public int computePrice(int days) {
//         return Switch.childrenPrice; // 1) nu compileaza; 2) frate... logica in enum!??!! really ?!
//      }
//   }
//   BABACI,
//   ;
   // Solutia #1
//   public abstract int computePrice(int days); // solutia 1 OMG
   // Solutia #2 FP maniaca
;
   // Solutia #2: 'pointer' din Enum la functia din @Service
   // -bidirectional coupling
   // -BiFunction ??!!🤢 un pic prea geek parca
   // -1 param doar la functie ca sa folosesti standard Function Interfaces din java 8
   // +ok ca te obliga la compilare sa dai behavior
   public final BiFunction<Switch, Integer, Integer> priceFunction;

   MovieType(BiFunction<Switch, Integer, Integer> priceFunction) {
      this.priceFunction = priceFunction;
   }
}

@Service
public class Switch {
   @Value("${children.price}")
   public int childrenPrice = 5; // pretend Spring is ON
   public int computePrice(MovieType type, int days) {
      return type.priceFunction.apply(this,days);
      // PROBLEMA switchului este ca atunci cand adaugi un tip nou de film, e posibil sa uiti sa adaugi case: necesar aici
      //      switch (type) {
      //         case REGULAR:
      //            return computeRegularPrice(days);
      //         case NEW_RELEASE:
      //            return computeNewReleasePrice(days);
      //         case CHILDREN:
      //            return computeChildrenPrice(days);
      //         default:
      //            throw new IllegalStateException("Unexpected value: " + type);
      //      }
   }
   public int computeRegularPrice(int days) {
      return days + 1;
   }
   // @see tests

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