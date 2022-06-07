package victor.training.java.advanced;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ExceptionsReturned {

   // ** Requirements **
   // Some dates might have invalid format
   // - if >= 50% valid => return the parseable dates,
   // - else => throw IllegalArgumentException listing all the corrupted dates
   /* @see tests */
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      List<String> errorDates = new ArrayList<>();
      List<LocalDate> dates = dateStrList.stream()
          .map(text -> {
             try {
                return LocalDate.parse(text, pattern);
             } catch(DateTimeParseException e) {
                errorDates.add(text);
                return null;
             }
          })
           .filter(Objects::nonNull)
          .collect(toList());

      if (dates.size() > dateStrList.size()/ 2) {
      return dates;

      } else throw new IllegalArgumentException(errorDates.stream().collect(Collectors.joining(",")));

   }
}
