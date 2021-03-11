package victor.training.java8.stream.order;

import java.util.function.Supplier;

public class DeCeEffectivelyFinal {

   public static Supplier<Integer> method() {
      int x = 0;

//      return () -> x++; // incrementezi ceva de pe stack-ul functiei tale.
      // Poate cand chemi supplierul, funtia ta nu mai e activa

      return () -> x; //totusi merge (hack in JVM)
   }

   public static void main(String[] args) {
      Supplier<Wine> firstMiracle = Wine::new;

      Supplier<Integer> supplier = method();
      // x nu mai e in memorie pentru ca stackframe-ul metodei method() tocmai a fost distrus.
      System.out.println(supplier.get());
      System.out.println(supplier.get());
      System.out.println(supplier.get());
   }


}
class Wine {}
