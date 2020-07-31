package victor.training.java8.promises;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bar {
   public static void main(String[] args) {
      Barman barman = new Barman();

      log.debug("Enter the BAR");

      Beer beer = barman.pourBeer();
      Rakia rakia = barman.pourRakia();

      log.debug("I am enjoying " + beer + " and " + rakia);
   }
}

@Slf4j
class Barman {
   @SneakyThrows
   public Beer pourBeer() {
      Thread.sleep(1000);
      return new Beer();
   }
   @SneakyThrows
   public Rakia pourRakia() {
      Thread.sleep(1000);
      return new Rakia();
   }
}
class Beer{}
class Rakia{}