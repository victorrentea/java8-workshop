package victor.training.java17;

import java.util.Arrays;
import java.util.List;

public class ForWithSideEffect {
   public static void main(String[] args) {

      List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

//      numbers.forEach(n -> System.out.println(n));
      for (Integer number : numbers) {
         System.out.println(number);
      }
   }
}
