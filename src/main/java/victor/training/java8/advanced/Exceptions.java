package victor.training.java8.advanced;

import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Exceptions {

   // TODO Return parsable dates if >= 50% valid; otherwise throw IllegalArgumentException (Hint: average)
   // Run tests!
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      List<LocalDate> dates = dateStrList.stream()
          .map(text -> LocalDate.parse(text, pattern))
          .collect(toList());

      return dates;
   }
}
