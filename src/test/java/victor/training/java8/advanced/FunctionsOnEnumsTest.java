package victor.training.java8.advanced;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsOnEnumsTest {

   // WORKS
   @Test
   void computePriceTest() {
      for (MovieType value : MovieType.values()) {
         try {
            FunctionsOnEnums.computePrice(value, -1);
         } catch (Exception e) {
            if (e instanceof NoMatchingCase) {
               fail();
            }
         }
      }
   }
}