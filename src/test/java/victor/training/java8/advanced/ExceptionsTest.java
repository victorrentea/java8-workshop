package victor.training.java8.advanced;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionsTest {

   private final Exceptions exceptions = new Exceptions();

   @Test
   public void allValid() {
      List<String> dates = List.of("2020-10-11", "2020-11-12", "2020-12-01");
      assertThat(exceptions.parseDates(dates)).containsExactly(
          parse("2020-10-11"), parse("2020-11-12"), parse("2020-12-01"));
   }
   @Test
   public void mostValid() {
      List<String> dates = List.of("2020-10-11", "2020-nov-12", "2020-12-01");
      assertThat(exceptions.parseDates(dates)).containsExactly(
          parse("2020-10-11"), parse("2020-12-01"));
   }
   @Test
   public void mostInvalid() {
      List<String> dates = List.of("2020-10-11", "2020-nov-12", "2020-dec-01");
      assertThrows(IllegalArgumentException.class, () -> exceptions.parseDates(dates));
   }
   @Test
   public void noneValid() {
      List<String> dates = List.of("a");
      assertThrows(IllegalArgumentException.class, () -> exceptions.parseDates(dates));
   }
}