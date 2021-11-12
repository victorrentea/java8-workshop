package victor.training.java8.advanced;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Exceptions {

   // TODO Return parsable dates if >= 50% valid; otherwise throw IllegalArgumentException (Hint: average)
   // Run tests!
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      int errors = 0;
      List<LocalDate> dates = new ArrayList<>();
      for (String text : dateStrList) {
         try {
            dates.add(LocalDate.parse(text, pattern));
         } catch (Exception e) {
            errors++;
         }
      }

      if (errors > dateStrList.size() / 2) {
         throw new IllegalArgumentException();
      }

//      List<LocalDate> dates = dateStrList.stream()
//          .map(text -> LocalDate.parse(text, pattern))
//          .collect(toList());

      return dates;
   }
}
