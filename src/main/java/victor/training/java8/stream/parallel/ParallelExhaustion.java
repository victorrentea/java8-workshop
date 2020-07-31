package victor.training.java8.stream.parallel;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ParallelExhaustion implements Runnable {
   private static final Logger log = LoggerFactory.getLogger(ParallelExhaustion.class);


   @Override
   public void run() {
      long t0 = System.currentTimeMillis();
      List<Object> out = target(IntStream.range(1, 4000).boxed().collect(toList()));
      System.out.println(out.size());
      long t1 = System.currentTimeMillis();
      System.out.println("Other http request out there in the  " + (t1-t0));
   }

   public static void main(String[] args) throws ExecutionException, InterruptedException {
      new Thread(new ParallelExhaustion()).start();

      //***********
      // NEVER use parallelStream to do JDBC/JPA/File/InputStream/Network/syncronized()
      // fun fact: these above are usually what make our app perform bad.
      //***********



      List<Integer> list = IntStream.range(1, 4000).boxed().collect(toList());

//      Customer customer = customerRepo.findById();

      Stream<Integer> stream = list.parallelStream()
          .map(integer -> {
             log.debug("here!");
             try {
                Thread.sleep(20);
             } catch (InterruptedException e) {
                e.printStackTrace();
             }
             return m(integer);
          });

      ForkJoinPool pool = new ForkJoinPool(300);
      List<Integer> out = pool.submit(() -> stream.collect(toList())).get();

      System.out.println(out);
   }

   private static List<Object> target(List<Integer> list) {
      return list.parallelStream()
             .map(ParallelExhaustion::transformItem)
             .collect(toList());
   }

   @SneakyThrows
   private static Integer transformItem(@NonNull Integer integer) {
//      log.info("What worker do I block here !?");
      Thread.sleep(20);
      return m(integer);
   }

   private static Integer m(@NonNull Integer integer) {
      return (int)Math.sqrt(integer * 2);
   }
}

