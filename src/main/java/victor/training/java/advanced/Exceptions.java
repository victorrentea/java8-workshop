package victor.training.java.advanced;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class Exceptions {

   // TODO Return parsable dates if >= 50% valid;
   //  otherwise throw IllegalArgumentException (Hint: average) displaying aLl invalid dates
   // Run tests!
   //
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//      int errors = 0; // now, a field !
      List<LocalDate> validDates = dateStrList.stream()
          .map(dateStr -> {
             try {
                return LocalDate.parse(dateStr, pattern);
             } catch (DateTimeParseException e) {
               return null;
             }
          })
          .filter(Objects::nonNull)
          .collect(toList());

      int errors = dateStrList.size() - validDates.size();

      if (validDates.size() >= errors) {
         return validDates;
      } else {
         throw new IllegalArgumentException();
      }
   }
}
