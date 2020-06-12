package victor.training.java8.parallel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class Bar {
   public static void main(String[] args) throws ExecutionException, InterruptedException {
      Bautor.bea();
      Thread.sleep(3000);
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
//      Bere bere = visDeBere.get();
//      Tuica tuica = visDeTuica.get();


//      CompletableFuture.allOf(visDeBere, visDeTuica)
//          .thenRun(() -> log.info("E gataaa!"));


      ForkJoinPool cocktailPool = new ForkJoinPool(2);

//      CompletableFuture<Submarin> visDeSubmarin = visDeBere.thenCombine(visDeTuica, Submarin::new); // ruleaza ::new cockaitlul in main)(
//      CompletableFuture<Submarin> visDeSubmarin = visDeBere.thenCombineAsync(visDeTuica, Submarin::new); // ruleaza ::new pe commonPool
      CompletableFuture<Submarin> visDeSubmarin = visDeBere.thenCombineAsync(visDeTuica, Submarin::new, cocktailPool);
//      Submarin submarin = visDeSubmarin.get(); // NU VA FACE GET pentru a nu bloca main nici macar o secunda.
//      log.info("Beau " + submarin);

      visDeSubmarin.thenAccept(s ->  log.info("Beau " + s));
      log.info("Ies. Eliberez mainul");
   }
}


class Submarin {
   private static final Logger log = LoggerFactory.getLogger(Submarin.class);
   private final Bere bere;
   private final Tuica tuica;

   Submarin(Bere bere, Tuica tuica) {
      this.bere = bere;
      this.tuica = tuica;
      log.info("Fac submarin");
      try {
         Thread.sleep(1000);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

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