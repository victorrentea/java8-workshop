package victor.training.java.advanced;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsOnEnumsTest {

   @Test
   void computePrice11() {
      for (MovieType value : MovieType.values()) {
         FunctionsOnEnums.computePrice11(value, 1);
      }
   }
}