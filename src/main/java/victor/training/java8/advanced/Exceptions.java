package victor.training.java8.advanced;

import io.vavr.control.Try;

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

      // MONAD as Optional, Stream, Flux, Mono, $ , Observable, CompletableFuture
//      Try<LocalDate> t = Try.of(() -> LocalDate.parse("text", pattern));
//      t.flatMap(date -> Try.of( () -> "a"));


      int errors = 0;
      List<Try<LocalDate>> tries = dateStrList.stream()
          .map(text -> Try.of(() -> LocalDate.parse(text, pattern)))
          .collect(Collectors.toList());

      // practical usages :
      /// MUST when you iterate 2GB of data and extract  a total of 10KB + erorrs.
      // ? beeing geek <<

      // >> Reactive Programming / CompletableFutures

      if (tries.stream().filter(Try::isFailure).count() > dateStrList.size() / 2) {
         throw new IllegalArgumentException();
      }

      return tries.stream().filter(Try::isSuccess).map(Try::get).toList();
   }
}
