package victor.training.java8.advanced;

import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

public class Exceptions {

   // TODO Accept if >= 50% valid; otherwise throw IllegalArgumentException (Hint: average)
   // Run tests
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      List<Try<LocalDate>> tries = dateStrList.stream()
          .map(text -> Try.of(() -> LocalDate.parse(text, pattern)))
          .collect(toList());

      double successRatio = tries.stream().mapToInt(t -> t.isSuccess()?1:0).average().getAsDouble();

      if (successRatio < .5) {
         throw new IllegalArgumentException();
      }

//      Map<Boolean, List<Try<LocalDate>>> collect = tries.stream().collect(partitioningBy(Try::isSuccess));
      return tries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
   }
}
