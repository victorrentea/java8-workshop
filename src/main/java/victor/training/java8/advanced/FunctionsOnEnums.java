package victor.training.java8.advanced;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import victor.training.java8.advanced.repo.OrderRepo;

import java.util.Map;
import java.util.function.BiFunction;

enum MovieType {
   REGULAR(FunctionsOnEnums::getRegularPrice),
   NEW_RELEASE(FunctionsOnEnums::getNewReleasePrice),
   CHILDREN(FunctionsOnEnums::getChildrenPrice);

   public final BiFunction<FunctionsOnEnums, Integer, Integer> priceLogic;

   MovieType(BiFunction<FunctionsOnEnums, Integer, Integer> priceLogic) {
      this.priceLogic = priceLogic;
   }
}


@Service
public class FunctionsOnEnums {

   public int computePrice(MovieType type, int daysRented) {
//      switch (type) {
//         case REGULAR:
//            return getRegularPrice(daysRented);
//         case NEW_RELEASE:
//            return getNewReleasePrice(daysRented);
//         case CHILDREN:
//            return getChildrenPrice(daysRented);
//         default:
//            throw new NoMatchingCase();
//      }

//      return map.get(type).apply(this, daysRented); // WORKS !

      return type.priceLogic.apply(this, daysRented);
   }

   // we are trying to reference an instance method FROM A STATIC CONTEXT (static map)
   // => you need to TAKE THE INSTANCE of the holder type as the first parameter

   // consider making this instance !
   public static final Map<MovieType, BiFunction<FunctionsOnEnums, Integer, Integer>> map = Map.of(
//       MovieType.CHILDREN, (self, daysRented) -> self.getChildrenPrice(),
//       MovieType.REGULAR, (self, daysRented) -> self.getRegularPrice(daysRented) ///
       MovieType.CHILDREN, FunctionsOnEnums::getChildrenPrice,
       MovieType.REGULAR, FunctionsOnEnums::getRegularPrice,
       MovieType.NEW_RELEASE, FunctionsOnEnums::getNewReleasePrice
   );

   public int getRegularPrice(int daysRented) {
      return daysRented + 1;
   }

   public int getNewReleasePrice(int daysRented) {
      return daysRented * 2;
   }
   @Autowired
   OrderRepo orderRepo; // unreferenceable from anything in enum
   public int getChildrenPrice(int daysRented) {
      return (int) orderRepo.count();
   }

   public static void main(String[] args) {

      System.out.println(new FunctionsOnEnums().computePrice(MovieType.REGULAR, 2));
      System.out.println(new FunctionsOnEnums().computePrice(MovieType.NEW_RELEASE, 2));
      System.out.println(new FunctionsOnEnums().computePrice(MovieType.CHILDREN, 2));
   }

   // we handle kafka messages based on some code -> enum type, JMS, records from file

   public static int getMaxRentedDays(MovieType type) {
      switch (type) {
         case REGULAR:
            return 7;
         case NEW_RELEASE:
            return 4;
         case CHILDREN:
            return 20;

         default:
            throw new IllegalStateException("Unexpected value: " + type);
      }
   }

}


