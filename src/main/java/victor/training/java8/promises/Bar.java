package victor.training.java8.promises;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class Bar {
   public static void main(String[] args) throws ExecutionException, InterruptedException {
      Barman barman = new Barman();

      log.debug("Enter the BAR");

      ExecutorService pool = Executors.newFixedThreadPool(2);

      Future<Beer> futureBeer = pool.submit(barman::pourBeer);
      Future<Rakia> futureRakia = pool.submit(barman::pourRakia);
      log.debug("The waitress left with my order");

      Beer beer = futureBeer.get();
      Rakia rakia = futureRakia.get();

      log.debug("I am enjoying " + beer + " and " + rakia);
   }
}

@Slf4j
class Barman {
   @SneakyThrows
   public Beer pourBeer() {
      log.debug("Pouring beer");
      Thread.sleep(1000);
      return new Beer();
   }
   @SneakyThrows
   public Rakia pourRakia() {
      log.debug("Pouring rakia");
      Thread.sleep(1000);
      return new Rakia();
   }
}
class Beer{}
class Rakia{}