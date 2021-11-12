package victor.training.java8.advanced;


import org.springframework.beans.factory.annotation.Autowired;
import victor.training.java8.advanced.repo.OrderRepo;

enum MovieType {
   REGULAR {
      @Override
      public int computePrice(int daysRented) {
         return daysRented + 1;
      }
   },
   NEW_RELEASE {
      @Override
      public int computePrice(int daysRented) {
         return daysRented * 2; // PROBLEM: LOGIC in enums ====> SHOCK for the first BUG in an enum
      }
   },
   CHILDREN {
      @Override
      public int computePrice(int daysRented) {
//         orderRepo.findByActiveTrue() // PROBLEM: donest compile
         return 5;
      }
   }
//   ,ELDERS // doesn't compike anymore
   ;


   public abstract int computePrice(int daysRented);

}

public class FunctionsOnEnums {
   @Autowired
   OrderRepo orderRepo; // unreferenceable from anything in enum

   public static void main(String[] args) {

      System.out.println(computePrice(MovieType.REGULAR, 2));
      System.out.println(computePrice(MovieType.NEW_RELEASE, 2));
      System.out.println(computePrice(MovieType.CHILDREN, 2));
   }

   // we handle kafka messages based on some code -> enum type, JMS, records from file
   public static int computePrice(MovieType type, int daysRented) {
      switch (type) {
         case REGULAR:
            return daysRented + 1;
         case NEW_RELEASE:
            return daysRented * 2;
         case CHILDREN:
            return 5;
         default:
            throw new NoMatchingCase();
      }
   }

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


class NoMatchingCase extends RuntimeException {

}