package victor.training.java.advanced;

import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

//class MyAggregateType {
//   List<LocalDate> validDates;
//   List<String> invalidDates;
//}
public class Exceptions {

   // TODO Return parsable dates if >= 50% valid;
   //  otherwise throw IllegalArgumentException (Hint: average) displaying aLl invalid dates
   // Run tests!
   //
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");


//      int errors = 0; // now, a field !
      List<Try<LocalDate>> tries = dateStrList.stream()
          .map(dateStr -> Try.of(() -> LocalDate.parse(dateStr, pattern)))
          .collect(toList());

      List<String> errorDates = tries.stream()
          .filter(Try::isFailure)
          .map(Try::getCause)
          .map(Throwable::getMessage)
          .collect(toList());

      int errors = errorDates.size();

      List<LocalDate> validDates = tries.stream()
          .filter(Try::isSuccess)
          .map(Try::get)
          .collect(toList());

      if (validDates.size() >= errors) {
         return validDates;
      } else {
         throw new IllegalArgumentException("Failed for : "  +
                 String.join(", ", errorDates));
      }
   }
}
