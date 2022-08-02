package victor.training.java.advanced;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static victor.training.java.advanced.tricks.ConcurrencyUtil.sleepq;

@Slf4j
public class ParallelStreams {
   public static void main(String[] args) throws ExecutionException, InterruptedException {
//      Enemy.parallelRequest();

      long t0 = System.currentTimeMillis();

      List<Integer> list = IntStream.range(1, 100).boxed().toList();

      Stream<Integer> stream = list.parallelStream()
              .filter(i -> {
                 log.debug("Filter " + i);
                 return i % 2 == 0;
              })
              .map(i -> {
                 log.debug("Map " + i);
                 sleepq(100); // what exactly takes this time?
                 // NETWORKING: SELECT, GET, POST, WSDL call, RMI < 95% of cases your are here
                 // HEAVY CPU: generatign PDF, rendering a chart, video compression, asymmetric signatures verification

                 // do some 'paralellizable' I/O work (DB, REST, SOAP)
                 return i * 2;
              });

      ForkJoinPool myPool = new ForkJoinPool(3); // Evil syntax

      List<Integer> result =   myPool.submit(  () -> stream.collect(toList()) ).get();
      log.debug("Got result: " + result);

      long t1 = System.currentTimeMillis();
      log.debug("Took {} ms", t1 - t0);
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