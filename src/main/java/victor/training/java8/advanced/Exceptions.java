package victor.training.java8.advanced;

import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Exceptions {

   // TODO Accept if >= 50% valid;
   //  otherwise throw IllegalArgumentException (Hint: average)
   // Run tests
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//      data or exception

      List<Try<LocalDate>> tries = dateStrList.stream()
          .map(text -> Try.of(() -> LocalDate.parse(text, pattern)))
          .collect(toList());

      long failures = tries.stream().filter(Try::isFailure).count();
      long successes = tries.stream().filter(Try::isSuccess).count();
      if (successes >= failures) {
         return tries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
      }

      String err = tries.stream().filter(Try::isFailure)
          .map(Try::getCause).map(Throwable::getMessage)
          .collect(Collectors.joining(","));

      throw new IllegalArgumentException(err);
   }

}
