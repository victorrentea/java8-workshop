package victor.training.java8.advanced;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Exceptions {

   public static void main(String[] args) {
      List<String> dates = List.of("2020-10-11", "2020-nov-12", "2020-12-01");
      System.out.println(parseDates(dates));
   }

      // TODO Accept if >= 50% valid; otherwise throw exception (Hint: average)
   private static List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      List<LocalDate> dates = dateStrList.stream()
          .map(text -> LocalDate.parse(text, pattern))
          .collect(toList());

      return dates;
   }
}
