package victor.training.java8.advanced;

import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

// prod
public class Exceptions {

   public static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

   // TODO Return parsable dates if >= 50% valid;
   //  otherwise throw IllegalArgumentException (Hint: average)
   // Run tests!
   // LASA CODU ASA DACA PROBLEMA este pe date mici
   public List<LocalDate> parseDates(List<String> dateStrList) {

//      List<LocalDate> validDates = new ArrayList<>();
//      for (String dateStr : dateStrList) {
//         try {
//            validDates.add(parse(pattern, dateStr));
//         } catch (DateTimeParseException e) {
//            // shawarma
////            errors++;
////            if (errors > dateStrList.size() > 0) {
////               throw new IllegalArgumentException("AFARA!");
////            }
//         }
//      }
//      if (dateStrList.size() - validDates.size() > dateStrList.size()/2) {
//         throw new IllegalArgumentException();
//      }


      int maxAllowedErrors = dateStrList.size() / 2;

      Consumer<String> stopWhenTooManyErrors = new Consumer<String>() {
         int errors = 0;
         @Override
         public void accept(String dateStr) {
            if (!isValidDate(dateStr)) {errors++;}
            if (errors > maxAllowedErrors) {
               throw new IllegalArgumentException();
            }
         }
      };
      List<Try<LocalDate>> tries = dateStrList.stream()
//          .peek(stopWhenTooManyErrors)
//          .takeWhile()

          .map(text -> Try.ofCallable(() -> LocalDate.parse(text, PATTERN)))
          .collect(toList());


      // Intr-un singur pass colectezi si ERORI si REZULTATE

      long errors = tries.stream().filter(Try::isFailure).count();
      if (errors > dateStrList.size() / 2) {
         String datesInErrorCSV = tries.stream().filter(Try::isFailure)
             .map(Try::getCause)
             .map(Throwable::getMessage)
             .collect(Collectors.joining());
         throw new IllegalArgumentException(datesInErrorCSV);
      }

      return tries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
   }

   public boolean isValidDate(String dateStr) {
      try {
         LocalDate.parse(dateStr, PATTERN);
         return true;
      } catch (Exception e) {
         return false;
      }

   }

//   private Either<LocalDate, DateTimeParseException> parseCiudat(DateTimeFormatter pattern, String dateStr) {
   private Try<LocalDate> parseCiudat(DateTimeFormatter pattern, String dateStr) {
//      try {
//         return Try.success(LocalDate.parse(dateStr, pattern));
//      } catch (DateTimeParseException e) {
//         return Try.failure(e);
//      }
      return Try.ofCallable(() -> LocalDate.parse(dateStr, pattern));
   }

//   public Either<NID, Passport> getIDDocument() {
//   }
}
