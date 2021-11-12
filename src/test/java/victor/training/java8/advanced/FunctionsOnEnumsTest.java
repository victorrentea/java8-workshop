package victor.training.java8.advanced;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsOnEnumsTest {

   // WORKS
   @Test
   void computePriceTest() {
      for (MovieType value : MovieType.values()) {
         try {
            new FunctionsOnEnums().computePrice(value, -1);
         } catch (Exception e) {
            if (e instanceof NoMatchingCase) {
               fail();
            }
         }
      }
   }
   @Test
   void computePriceTest2() {
      for (MovieType value : MovieType.values()) {
         Assertions.assertThat(FunctionsOnEnums.map.get(value)).isNotNull();
      }
   }
}