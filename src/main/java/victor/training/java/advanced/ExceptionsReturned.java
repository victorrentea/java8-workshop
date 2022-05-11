package victor.training.java.advanced;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ExceptionsReturned {

   // ** Requirements **
   // Some dates might have invalid format
   // - if >= 50% valid => return the parseable dates,
   // - else => throw IllegalArgumentException
   /* @see tests */
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      List<LocalDate> dates = dateStrList.stream()
          .map(text -> LocalDate.parse(text, pattern))
          .collect(toList());

      return dates;
   }
}
