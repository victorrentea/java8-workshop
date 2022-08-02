package victor.training.java.advanced;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class ExceptionsReturned {

   // ** Requirements **
   // Some dates might have invalid format
   // - if >= 50% valid => return the parseable dates,
   // - else => throw IllegalArgumentException
   // I WANT TO SEE ALL THE FAILED DATES>
   /* @see tests */
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      List<String> failedDates = new ArrayList<>();
      List<LocalDate>dates = dateStrList.stream()
          .map(text -> {
             try {
                return LocalDate.parse(text, pattern);
             } catch (Exception e) {
                failedDates.add(text);
                return null;
             }
          })
           .filter(not(Objects::isNull))
          .collect(toList());


      if (dates.size() > dateStrList.size() / 2) {
         return dates;
      } else {
         throw new IllegalArgumentException();
      }
   }
}
