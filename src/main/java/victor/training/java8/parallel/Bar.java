package victor.training.java8.parallel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bar {
   public static void main(String[] args) {
      Bautor.bea();
   }
}

class Bautor {
   private static final Logger log = LoggerFactory.getLogger(Bautor.class);

   public static void bea() {
      log.info("Vreau sa beau");
      Bere bere = Barman.toarnaBere();
      Tuica tuica = Barman.toarnaTuica();
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