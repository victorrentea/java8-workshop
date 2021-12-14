package victor.training.java.advanced;

import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

public class Exceptions {

   // ** Requirements **
   // Some dates might have invalid format
   // - if >= 50% valid => return the parseable dates,
   // - else => throw IllegalArgumentException
   /* @see tests */
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      List<Try<LocalDate>> tries = dateStrList.stream()
          .map(text -> Try.of(() -> LocalDate.parse(text, pattern)))
          .collect(toList());

      long successes = tries.stream().filter(Try::isSuccess).count();
      System.out.println(successes);
      if (successes >= dateStrList.size()/2.0) {
         return tries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
      }

      throw new IllegalArgumentException();


   }

//   public CompletableFuture<String> method() {
//      return CompletableFuture.failedFuture(new IllegalArgumentException());
//   }
//   public Mono<String> method() {
//      return Mono.error(new IllegalArgumentException());
//   }


}
