package victor.training.java.advanced;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
public class Exceptions {

   // ** Requirements **
   // Some dates might have invalid format
   // - if >= 50% valid => return the dates that could be parsed,
   // - else => throw IllegalArgumentException
   /* @see tests */
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      List<Try<LocalDate>> tries = dateStrList.stream()
          .map(dateStr -> Try.of(() -> LocalDate.parse(dateStr, pattern)))
          .collect(toList());

      long errors = tries.stream().filter(Try::isFailure).count();
      long success = tries.stream().filter(Try::isSuccess).count();

      if (success < errors) {
         String message = tries.stream().filter(Try::isFailure)
             .map(Try::getCause)
             .map(Throwable::getMessage)
             .collect(Collectors.joining(","));
         throw new IllegalArgumentException(message);
      }

//      CompletableFuture.fai
//      Mono.failed()
//      Obserable

      return tries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
   }
}
