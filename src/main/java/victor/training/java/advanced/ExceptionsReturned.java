package victor.training.java.advanced;

import io.vavr.control.Try;
import org.aspectj.runtime.CFlow;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ExceptionsReturned {

   // ** Requirements **
   // Some dates might have invalid format
   // - if >= 50% valid => return the parseable dates,
   // - else => throw IllegalArgumentException listing all the corrupted dates
   /* @see tests */
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      List<Try<LocalDate>> tries = dateStrList.stream()
              .map(text ->
                      Try.of(() -> LocalDate.parse(text, pattern))
              )
              .collect(toList());

      CompletableFuture<String> failed =
              CompletableFuture.completedFuture("a")
//              CompletableFuture.failedFuture(new IllegalArgumentException(""))
              ;

      double successRate = tries.stream().mapToDouble(t -> t.isSuccess() ? 1 : 0).average().orElse(1);
      if (successRate > 0.5) {
         return tries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
      } else {
         String message = tries.stream().filter(Try::isFailure).map(Try::getCause).map(Throwable::getMessage).collect(Collectors.joining(","));
         throw new IllegalArgumentException(message);
      }

   }
}
