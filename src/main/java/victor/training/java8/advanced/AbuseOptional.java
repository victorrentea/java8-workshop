package victor.training.java8.advanced;

import victor.training.java8.advanced.model.Product;

import java.util.Optional;

public class AbuseOptional {

   public static void main(String[] args) {

//      One reason, I guess, for this is the lack of support for functions working on multiple Optionals?

      Optional<Product> op1 = Optional.empty();
      Optional<Product> op2 = Optional.empty();


      Optional<Integer> resultOpt = op1.flatMap(p1 -> op2.map(p2 -> workWithBoth(p1, p2)));

      if (op1.isPresent() && op2.isPresent()) {
         workWithBoth(op1.get(), op2.get());
      }

      // -----------------------------------------
      // was NOT in the CORE LOGIC, but peripheral : DTO
      Product p = null; // could be null

      String ok = Optional.ofNullable(p).map(p1 -> doWithProduct(p1)).orElse("");

      // Starting to smell....:
      Optional<String> pOpt = Optional.ofNullable(p)
          .map(p1 -> doWithProduct(p1));
      pOpt
          .map(String::toUpperCase)
          .ifPresent(AbuseOptional::save);
//          .orElse(""); // ABUSE! WHY?
      pOpt.ifPresent(System.out::println);


//      pOpt.map(xxx -> func)
      // more maintainable.
      String result = "";
      if (p != null) {
         result = doWithProduct(p);
         save(result.toUpperCase());
         System.out.println(p);
      }

      // -----------------------------------------





      // DRY   KISS       OCP   <<<<



      // Optional Abuse?
      // Product p = productRepo.findById(13L);

      // Streaming queries
      // productRepo.streamAllByDeletedTrue().forEach(System.out::println);
   }

   private static void save(String toUpperCase) {

   }

   private static String doWithProduct(Product p1) {
      return "Stuff";
   }

   private static Integer workWithBoth(Product product, Product product1) {
      return 1;
   }

   Optional<Product> productBad;

   public void method(Optional<Product> productBad, long customerId) { // SRP
      if (productBad.isPresent()) {
         //stuff in
      }
      //stuff after
   }

   public void methodNoProduct(long customerId) { // SRP
      //stuff after
   }

   public void methodWithProduct(Product product, long customerId) { // SRP
      //stuff in
      methodNoProduct(customerId);
   }

   {
      method(Optional.empty(), 1L);
      // refactor to
      methodNoProduct(1L);

      method(Optional.of(new Product("prod")), 1L);
      methodWithProduct(new Product("prod"), 1L);

   }
}
