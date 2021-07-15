package victor.training.java8.advanced;

import io.vavr.control.Either;
import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Exceptions {

   // TODO Accept if >= 50% valid; otherwise throw IllegalArgumentException (Hint: average)
   // Run tests
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      List<Try<LocalDate>> tries = dateStrList.stream()
          .map(text -> parse(pattern, text))
          .collect(toList());

      double successRatio = tries.stream().mapToInt(t -> t.isSuccess() ? 1 : 0).average().orElse(0);

      if (successRatio >= 0.5) {
         return tries.stream().filter(Try::isSuccess).map(Try::get).collect(Collectors.toList());
      }

      String errors = tries.stream().filter(Try::isFailure).map(Try::getCause).map(Throwable::getMessage).collect(Collectors.joining());

      throw new IllegalArgumentException(errors);

   }

   private Try<LocalDate> parse(DateTimeFormatter pattern, String text) { // never throws, but might reutrn a failed Try
      return Try.ofCallable(   () -> LocalDate.parse(text, pattern)   ) ;
   }
}
