package victor.training.java.advanced;

import io.vavr.control.Either;
import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class ExceptionsReturned {

   // ** Requirements **
   // Some dates might have invalid format
   // - if >= 50% valid => return the parseable dates,
   // - else => throw IllegalArgumentException reporting the dates in error
   /* @see tests */
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//       List<Either<LocalDate, String>> collect = dateStrList.stream()
//               .map(text -> {
//                   try {
//                       return Either.<LocalDate, String>left(LocalDate.parse(text, pattern));
//                   } catch (DateTimeParseException e) {
//                       return Either.<LocalDate, String>right(text);
//                   }
//               })
//               .collect(toList());

       List<Try<LocalDate>> tries = dateStrList.stream()
               .map(string -> Try.of(   () -> LocalDate.parse(string, pattern)    ))
               .collect(toList());

       // ienvitabil in procesarea asincrona nonblocanta cu CompletableFuture, Reactive programming
//       Mono
//       Flux
//       Observable
       CompletableFuture<Object> promiseFailed = CompletableFuture.failedFuture(new IllegalArgumentException("Ex returnata"));


       if (tries.stream().filter(Try::isSuccess).count() > dateStrList.size()/ 2) {
         return tries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
      }
       String mesaj = tries.stream().filter(Try::isFailure)
               .map(Try::getCause)
               .map(Throwable::getMessage)
               .collect(Collectors.joining("" +
               "\n"));
       throw new IllegalArgumentException("PREA MULTE VARZA: " + mesaj);
//      return dates;
   }


   // inca imperativ
//   public List<LocalDate> parseDates(List<String> dateStrList) {
//      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//      List<String> errors = new ArrayList<>();
//      List<LocalDate> dates = dateStrList.stream()
//          .map(text -> {
//             try {
//                return LocalDate.parse(text, pattern);
//             } catch (DateTimeParseException e) {
//                errors.add(text);
//                return null;
//             }
//          })
//           .filter(not(Objects::isNull))
//          .collect(toList());
//
//      if (dates.size() > dateStrList.size()/ 2) {
//         return dates;
//      }
//      throw new IllegalArgumentException("PREA MULTE VARZA: " + errors);
////      return dates;
//   }
}
