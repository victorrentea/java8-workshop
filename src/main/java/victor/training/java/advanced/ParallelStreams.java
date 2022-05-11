package victor.training.java.advanced;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static victor.training.java.advanced.tricks.ConcurrencyUtil.sleepq;

@Slf4j
public class ParallelStreams {
   static int acc;
   public static void main(String[] args) {
      Enemy.parallelRequest();

      long t0 = System.currentTimeMillis();

      List<Integer> list = IntStream.range(1,100).boxed().collect(toList());

      List<Integer> result = list.parallelStream() // poti sa saturezi complet cele N procesoare DACA ....
          .filter(i -> {
             log.debug("Filter " + i);
             return i % 2 == 0;
          })
          .map(i -> {
             log.debug("Map " + i); // de fapt e un call de network. SELECT/ REST/ WSDL
//             restTemplaetget
             acc++;
             sleepq(100); // do some 'paralellizable' I/O work (DB, REST, SOAP)
             return i * 2;
          })
          .collect(toList());
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