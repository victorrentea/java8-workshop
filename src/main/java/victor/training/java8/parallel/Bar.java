package victor.training.java8.parallel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Bar {
   public static void main(String[] args) throws ExecutionException, InterruptedException {
      Bautor.bea();
   }
}

class Bautor {
   private static final Logger log = LoggerFactory.getLogger(Bautor.class);

   public static void bea() throws ExecutionException, InterruptedException {
      log.info("Vreau sa beau");
      // PREREQ:
      // sunt reordonabile fara compilation sau functional failures
      // sunt thread-safe : pot fi chemate in paralel

      // java7 style
//      ExecutorService executorService = Executors.newFixedThreadPool(3);
//      Future<bere>  = executorService.submit(Barman::toarnaBere);

      CompletableFuture<Bere> visDeBere = CompletableFuture.supplyAsync(() -> Barman.toarnaBere());
      CompletableFuture<Tuica> visDeTuica = CompletableFuture.supplyAsync(Barman::toarnaTuica);
      log.info("A plecat fata cu comanda");
      Bere bere = visDeBere.get();
      Tuica tuica = visDeTuica.get();
//      Bere bere = Barman.toarnaBere();
//      Tuica tuica = Barman.toarnaTuica();
      log.info("Beau " + bere + " cu " + tuica);
   }
}



class Barman {
   private static final Logger log = LoggerFactory.getLogger(Barman.class);
   public static Bere toarnaBere() {
      log.info("Torn bere");
      try {
         Thread.sleep(1000);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      log.info("Torn gata");
      return new Bere();
   }

   public static Tuica toarnaTuica() {
      log.info("Torn tuica");
      try {
         Thread.sleep(1000);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      log.info("Torn gata");
      return new Tuica();
   }
}

class Bere {}
class Tuica {}