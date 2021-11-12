package victor.training.java17;

import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

public class SwitchOnEnum {

   private int someCounter = 100;

   public int computePrice(MovieType type, int daysRented) {

      int vint = switch (daysRented) {
         case 1 -> {
            if (someCounter < 1000) yield 2;
            yield daysRented + 1;
         }
         case 2 -> daysRented * 2;
         case 3 -> 5;
         default -> throw new IllegalStateException("mandatory if switch on NON-enum and switch used as an expression");
      };
      int v = switch (type) {
         case REGULAR -> daysRented + 1;
         case NEW_RELEASE -> daysRented * 2;
         default -> 5;
//         default -> // if you switch on enum the compiler allows you to skip the default,
//         even if your switch is used as an expression
      };

      ///Regardless of what you put after the arrow ->,
      int vv;
      switch (type) {
         case REGULAR:
            vv = daysRented + 1;
            break;
         case NEW_RELEASE:
            vv = daysRented * 2;
            break;
         case CHILDREN:
            vv = 5;
            break;
         default:
      }
      return v;
//      return vv; // doesn't compile !
   }
   public void onlySideEffects(MovieType type) {
      switch (type) {
         case REGULAR, CHILDREN -> System.out.println("not release");
         case NEW_RELEASE -> System.out.println("NEW_RELEASE");
      }
      switch (type) {
         case REGULAR:
         case CHILDREN:
            System.out.println("not release");
            break;
         case NEW_RELEASE:
            System.out.println("NEW_RELEASE");
            break;
      }
   }
//   public int getMaxRentedDays(MovieType type) {
//      if (type == MovieType.NEW_RELEASE) {
//         return 4;
//      }
//   }
}


enum MovieType {
   REGULAR,
   NEW_RELEASE,
   CHILDREN,
   ELDERS
}
