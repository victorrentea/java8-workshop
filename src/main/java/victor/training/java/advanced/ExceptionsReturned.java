package victor.training.java.advanced;

import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ExceptionsReturned {

   // ** Requirements **
   // Some dates might have invalid format
   // - if >= 50% valid => return the parseable dates,
   // - else => throw IllegalArgumentException
   /* @see tests */
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");


      List<Try<LocalDate>> tries = dateStrList.stream()
              .map(text -> tryParse(pattern, text))
              .collect(toList());

      int success = (int) tries.stream().filter(Try::isSuccess).count();
      if (success > 0.5 * dateStrList.size()) {
         return tries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
      } else {
         String errors = tries.stream()
                 .filter(Try::isFailure)
                 .map(Try::getCause)
                 .map(Throwable::getMessage)
                 .collect(joining(","));
         throw new IllegalArgumentException("VALEU PREA MULTE ERORI : " + errors);
      }
   }
//   private static CompletableFuture<LocalDate> tryParse(DateTimeFormatter pattern, String text) {
   private static Try<LocalDate> tryParse(DateTimeFormatter pattern, String text) {

//      if (asdad) {
//         return Try.failure(new IllegalArgumentException());
//      }
      return Try.of(() -> LocalDate.parse(text, pattern));
   }

   public void pley() {
      Try<LocalDate> t = tryParse(DateTimeFormatter.ISO_DATE, "2012112-ovct1-035");
      LocalDate localDate = t.get();
      Throwable ex = t.getCause();
      System.out.println(t.isSuccess());


      CompletableFuture<LocalDate> cfFailed = CompletableFuture.failedFuture(new RuntimeException());
      CompletableFuture<LocalDate> cfOk = completedFuture(LocalDate.now());
   }
}
