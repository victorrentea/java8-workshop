package victor.training.java.advanced;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExceptionsReturnedTest {

   private final ExceptionsReturned exceptionsReturned = new ExceptionsReturned();

   @Test
   public void allValid() {
      List<String> dates = List.of(
          "2020-10-11",
          "2020-11-12",
          "2020-12-01"
      );
      assertThat(exceptionsReturned.parseDates(dates)).containsExactly(
          parse("2020-10-11"), parse("2020-11-12"), parse("2020-12-01"));
   }
   @Test
   public void mostValid() {
      List<String> dates = List.of(
          "2020-10-11",
          "2020-nov-12", // INVALID
          "2020-12-01"
      );
      assertThat(exceptionsReturned.parseDates(dates)).containsExactly(
          parse("2020-10-11"),
           parse("2020-12-01"));
   }
   @Test
   public void mostInvalid() {
      List<String> dates = List.of(
          "2020-10-11",
          "2020-nov-12", // INVALID
          "2020-dec-01" // INVALID
      );
      assertThatThrownBy(() -> exceptionsReturned.parseDates(dates))
          .isInstanceOf(IllegalArgumentException.class)
              .hasMessageContaining("2020-nov-12")
              .hasMessageContaining("2020-dec-01");
   }
   @Test
   public void noneValid() {
      List<String> dates = List.of("a");

      assertThatThrownBy(() -> exceptionsReturned.parseDates(dates))
          .isInstanceOf(IllegalArgumentException.class);
   }
}