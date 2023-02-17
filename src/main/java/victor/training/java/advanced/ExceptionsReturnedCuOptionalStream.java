package victor.training.java.advanced;

import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class ExceptionsReturnedCuOptionalStream {

   // ** Requirements **
   // Some dates might have invalid format
   // - if >= 50% valid => return the parseable dates,
   // - else => throw IllegalArgumentException
   /* @see tests */
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      List<LocalDate> success = dateStrList.stream()
              .flatMap(text -> tryParse(pattern, text).stream())
              .collect(toList());
      if (success.size() > 0.5 * dateStrList.size()) {
         return success;
      } else {
         throw new IllegalArgumentException("VALEU PREA MULTE ERORI");
      }

//      Optional<LocalDate> opt = tryParse(pattern, "12031-41240-101-2501-");
//      Stream<LocalDate> cumAdica = opt.stream();

   }

   private Optional<LocalDate> tryParse(DateTimeFormatter pattern, String text) {
      try {
         return Optional.of(LocalDate.parse(text, pattern));
      } catch (DateTimeParseException e) {
         return Optional.empty();
      }
   }

}
