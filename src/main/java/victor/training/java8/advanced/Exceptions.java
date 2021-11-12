package victor.training.java8.advanced;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Exceptions {

   // TODO Return parsable dates if >= 50% valid; otherwise throw IllegalArgumentException (Hint: average)
   // Run tests!
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");


      final int[] errors = {0}; // FIELD !?!!?
      List<LocalDate> dates = dateStrList.stream()
          .map(text -> {

             try {
                return LocalDate.parse(text, pattern);
             } catch (Exception e) {
                errors[0]++;
             }
          })
          .collect(Collectors.toList());
      //         try {
      //         } catch (Exception e) {
      //            errors++;
      //         }

      if (errors[0] > dateStrList.size() / 2) {
         throw new IllegalArgumentException();
      }

//      List<LocalDate> dates = dateStrList.stream()
//          .map(text -> LocalDate.parse(text, pattern))
//          .collect(toList());

      return dates;
   }
}
