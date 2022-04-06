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
          .map(text -> Try.of( ()->  LocalDate.parse(text, pattern) )
//              .mapTry(ldate -> f(ldate))
          )
          .collect(toList());



//      CompletableFuture.failedFuture(new RuntimeException());
//      CompletableFuture.completedFuture("");

      // ques for the un/checked debate, here LocalDate is throwing unchecked exception,
      // would it not have been better if we had a checked exception,
      // that way it would be explicit that some things may fail, wdyt ?

//      Try<LocalDate> t = Try.failure(new RuntimeException());// specialization of Either<A,B>


      long failedDates = tries.stream().filter(Try::isFailure).count();
      if (failedDates > dateStrList.size()/ 2) {
         throw new IllegalArgumentException();
      }
      return tries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
   }
}
