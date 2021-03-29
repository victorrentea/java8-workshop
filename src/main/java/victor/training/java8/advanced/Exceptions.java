package victor.training.java8.advanced;

import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Exceptions {

   // TODO Accept if >= 50% valid; otherwise throw IllegalArgumentException (Hint: average)
   // Run tests
   public List<LocalDate> parseDates(List<String> dateStrList) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      List<Try<LocalDate>> dateTries = dateStrList.stream()
          .map(dateStr -> Try.of(() -> LocalDate.parse(dateStr, pattern)))
          .collect(toList());
//
//      Try<LocalDate> t = Try.ofSupplier(() -> LocalDate.now());
//      Try<?> errorTry = t.flatMap(date -> riskyFunction(date));
//      t.flatMapTry(date -> riskyFunction(date));

      double successRatio = dateTries.stream().mapToDouble(t -> t.isSuccess() ? 1 : 0).average().orElse(0);
      if (successRatio < .5) {
         String errors = dateTries.stream().filter(Try::isFailure).map(t -> t.getCause().getMessage()).collect(joining("\n"));
         throw new IllegalArgumentException("Could not parse: " + errors);
      }


      return dateTries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
   }

//   private Try<?> riskyFunction(LocalDate date) {
//   }


   public void processLines(Supplier<Stream<String>> lineStream) {
      try (Stream<String> lines = lineStream.get()) {
         lines.forEach(System.out::println);
      }
      try (Stream<String> lines = lineStream.get()) {
         lines.forEach(System.out::println);
      }
   }
}
