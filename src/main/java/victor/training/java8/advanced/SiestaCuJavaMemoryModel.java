package victor.training.java8.advanced;

import java.util.Random;
import java.util.function.Supplier;

public class SiestaCuJavaMemoryModel {

   public static void main(String[] args) {
      new SiestaCuJavaMemoryModel().method();
   }

   public void method() {
      Supplier<Integer> supplier = createSupplier();
      System.out.println(supplier.get());
      System.out.println(supplier.get());
   }
//   int n = 2; // Heap
   public Supplier<Integer> createSupplier() {
      int n = 2; // Stack
      return () ->  n;
   }
}
// Heap vs Stack
