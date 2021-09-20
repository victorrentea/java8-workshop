package victor.training.java8.advanced;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

// prod
public class Exceptions {

   // TODO Return parsable dates if >= 50% valid;
   //  otherwise throw IllegalArgumentException (Hint: average)
   // Run tests!
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      List<LocalDate> validDates = new ArrayList<>();
      for (String dateStr : dateStrList) {
         try {
            validDates.add(LocalDate.parse(dateStr, pattern));
         } catch (DateTimeParseException e) {
            // shawarma
         }
      }
      if (dateStrList.size() - validDates.size() > dateStrList.size()/2) {
         throw new IllegalArgumentException();
      }

      return validDates;
   }
}
