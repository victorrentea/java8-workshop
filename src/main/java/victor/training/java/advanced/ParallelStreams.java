package victor.training.java.advanced;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static victor.training.java.advanced.tricks.ConcurrencyUtil.sleepq;

@Slf4j
public class ParallelStreams {
   public static void main(String[] args) throws ExecutionException, InterruptedException {
      Enemy.parallelRequest();

      long t0 = System.currentTimeMillis();

      List<Integer> list = IntStream.range(1,100).boxed().collect(toList());

      // or take the IO operation out of stream.
      // Just collect to collection and do IO over the full collection (instead of one by one)
      // not all api's offer that bulk process
      Stream<Integer> stream = list.stream()
          .filter(i -> {
             log.debug("Filter " + i);
             return i % 2 == 0;
          })
          .map(i -> {
             log.debug("Map " + i);
             return restApiCall(i);
          });

//      restApiCall(theWholeList)

      ForkJoinPool pool = new ForkJoinPool(20);
      List<Integer> result =   pool.submit( ()->  stream.collect(toList())  ).get()    ; //terminal operation

      log.debug("Got result: " + result);

      long t1 = System.currentTimeMillis();
      log.debug("Took {} ms", t1 - t0);
   }

   private static int restApiCall(Integer i) {
//      sleepq(100); // do some 'paralellizable' I/O work (DB, REST, SOAP)
      return i * 2;
   }
}

// =========== far away, in a distant Package ...... =============
@Slf4j
class Enemy {
   @SneakyThrows
   public static void parallelRequest() {
      Thread thread = new Thread(Enemy::optimized);
      thread.setDaemon(true); // to exit program
      thread.start();
      Thread.sleep(100);
   }
   public static void optimized() {
      int result = IntStream.range(1, 1000)
          .parallel()
          .map(Enemy::callNetworkOrDB)
          .sum();
      System.out.println(result);
   }

   @SneakyThrows
   public static int callNetworkOrDB(int id) {
      log.debug("Blocking...");
      Thread.sleep(1000);
      return id*2;
   }

}