package victor.training.java8.futures;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class Bar {
   public static void main(String[] args) throws ExecutionException, InterruptedException {
      new Beutor(new Barman()).bea();
   }
}

class Beutor {
   private static final Logger log = LoggerFactory.getLogger(Beutor.class);

   static ExecutorService barmani = Executors.newFixedThreadPool(2);

   private final Barman barman;
   public Beutor(Barman barman) {
      this.barman = barman;
   }
   public void bea() throws ExecutionException, InterruptedException {
      log.info("Vin la bar");


      Future<Bere> futureBere = barmani.submit(barman::toarnaBere);
      Future<Vodka> futureVodka = barmani.submit(barman::toarnaVodka);
      log.info("A plecat fata cu comanda");

      Bere bere = futureBere.get();
      // pana aici main doarme 1 sec <--- problema
      Vodka vodka = futureVodka.get(); // lina asta o executa instantaneu, pentru ca deja e gata vodka, ca dureaza tot 1 sec sa o toarne


      log.info("Savurez " + bere + " cu " + vodka);
      log.info("Plec acas");
   }
}

// emulam un serviciu [web] extern, care dureaza timp
class Barman {
   private static final Logger log = LoggerFactory.getLogger(Barman.class);
   @SneakyThrows
   public Bere toarnaBere() {
      log.info("Torn Bere");
      Thread.sleep(1000);
      log.info("Am turnat Bere");
      return new Bere();
   }
   @SneakyThrows
   public Vodka toarnaVodka() {
      log.info("Torn Vodka");
      Thread.sleep(1000);
      log.info("Am turnat Vodka");
      return new Vodka();
   }
}

@Data
class Bere {}
@Data
class Vodka {}