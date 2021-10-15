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

   // intorci intr-o lista datele parsate DOAR daca cel putin jumate sunt valide.
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");


      List<Try<LocalDate>> tries = dateStrList.stream()
          .map(s -> Try.of(()->LocalDate.parse(s, pattern)))
          .collect(toList());

      long success = tries.stream().filter(Try::isSuccess).count();
      long failed = tries.size() -success;// tries.stream().filter(Try::isFailure).count();

      if (success > failed) {
         return tries.stream().map(Try::get).collect(toList());
      } else {
         throw new IllegalArgumentException("BUM!");
      }
//      return dates;
   }

}
