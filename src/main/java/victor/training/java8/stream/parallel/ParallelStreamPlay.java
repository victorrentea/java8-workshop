package victor.training.java8.stream.parallel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ParallelStreamPlay {
   private static final Logger log = LoggerFactory.getLogger(ParallelStreamPlay.class);

   public static void main(String[] args) throws ExecutionException, InterruptedException {
      List<Integer> numbers = IntStream.range(1, 10_000) // IntStream
          .boxed() // Stream<Integer>
          .collect(toList());

//      List<Integer> list = new ArrayList<>();
      numbers.parallelStream()
          .filter(n -> {

             // NU!
//             log.info("Filter " + n); // daca esti mai catolic decat papa, .info va scrie un fisier


             // NU
//             try {
//                Thread.sleep(1000); // MMOG
//             } catch (InterruptedException e) {
//                e.printStackTrace();
//             }

             // NU
//             file.read() sau .write()  inputStream.read() de pe input socket HttpServletRequest

             // NU
//             restTemplate.get()

             // NU:
//             jdbc.query()
//              repo.getById()

             // NU
//             syncronized calls

             // Sa dormi intr-unul din cele N-1 (11 la mine) threaduri
             // ==> blochezi toate .parallelStreamuril din TOT JVM.
             // Prafu se alge daca faci lucruri non-CPU
             return n % 2 == 1;
          })
//          .distinct()
          .sorted()
          .map(n -> {
             log.info("Map " + n);
             return n * n;
          })
          .parallel()
//          .sequential()
          .filter(n -> n > 10)
          .findAny() // oricare worker gaseste primu, ala e.
//          .findFirst() // primul in ordinea initiala e intors
         .ifPresent(System.out::println);
//          .forEach(x -> {
//             log.info("Out " + x);
//             list.add(x);
//          });
//          .collect(toList());

//      System.out.println(list);


      ForkJoinPool pool = new ForkJoinPool(100);

      Stream<String> stream = numbers.parallelStream()
          .map(ParallelStreamPlay::callRest);

      List<String> results = pool.submit(() -> stream.collect(toList())).get(); //terminal operation should run in a custom ForkJoinPool
      System.out.println(results);

   }

   public static String callRest(int i) {
      log.info("Calling ws " + i);
      // ai foarte greu acces la date de pe threadul care a startat executia.
      try {
         Thread.sleep(100);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      return "data" + i;
   }
}
