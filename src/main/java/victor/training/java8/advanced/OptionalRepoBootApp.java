package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.ProductRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Stream;

@RequiredArgsConstructor
@SpringBootApplication
public class OptionalRepoBootApp implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(OptionalRepoBootApp.class, args);
   }
   private final ProductRepo productRepo;

   @Transactional(readOnly = true)
   public void run(String... args) throws Exception {
      productRepo.save(new Product("Tree"));
//      productRepo.findById(1L).get();
      System.out.println(productRepo.findByNameContaining("re"));
      System.out.println(productRepo.findByNameContaining("rx"));


      productRepo.streamAllByDeletedTrue().forEach(System.out::println);// dubios-- keeps a connection open
      // if you insist: @Transaction(readOnly=true) for hibernat!!


      try (Stream<String> lines = new BufferedReader(new FileReader("a")).lines()) {

         lines.forEach(System.out::println);
      }
      // Honestly... if you are sweepimg millions of lines,....
      // why Spring Batch ?!

   }
}
