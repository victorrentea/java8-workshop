package victor.training.java8.promises;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.*;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
public class Bar {
   public static void main(String[] args) throws ExecutionException, InterruptedException {
      Barman barman = new Barman();

      log.debug("Enter the BAR");

      ExecutorService pool = Executors.newFixedThreadPool(2);

      ForkJoinPool ioOperationsPool = new ForkJoinPool(300);
      // DeferredResult from spring will allow you to release the http thread once you configured the COmpletableFutures.

      CompletableFuture<Beer> futureBeer = supplyAsync(barman::pourBeer, ioOperationsPool);
      CompletableFuture<Rakia> futureRakia = supplyAsync(barman::pourRakia, ioOperationsPool);

      log.debug("The waitress left with my order");

      // the http request thread is blocking here for 1 whole second
      // not serving any other incoming request
      // underuse

      CompletableFuture<Buzdugan> futureBuzdugan = futureBeer.thenCombineAsync(futureRakia, Buzdugan::new);

      futureBuzdugan
          .thenApplyAsync(b -> {
             // CPU here
             return b;
          })
          .thenApplyAsync(barman::addIce)
          .thenAcceptAsync(b -> log.debug("I am enjoying " + b));
      log.debug("exit the BAR");

      Thread.sleep(2000); // hack for demo
      // your app won't just die here.
   }
}

@Slf4j
class Barman {
   @SneakyThrows
   public Beer pourBeer() {
      log.debug("Pouring beer");
      Thread.sleep(1000); // web service call, DB query
      return new Beer();
   }
   @SneakyThrows
   public Rakia pourRakia() {
      log.debug("Pouring rakia");
      Thread.sleep(1000);
      return new Rakia();
   }
   @SneakyThrows
   public Buzdugan addIce(Buzdugan buzdugan) {
      log.debug("AAdd ice");
      Thread.sleep(1000);
      return buzdugan;
   }
}
class Beer{}
class Rakia{}
@Data
class Buzdugan {
   private final Beer beer;
   private final Rakia rakia;
}