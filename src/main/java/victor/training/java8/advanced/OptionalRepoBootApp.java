package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.ProductRepo;

@RequiredArgsConstructor
@SpringBootApplication
public class OptionalRepoBootApp implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(OptionalRepoBootApp.class, args);
   }
   private final ProductRepo productRepo;

   public void run(String... args) throws Exception {
      productRepo.save(new Product("Tree"));
      System.out.println(productRepo.findByNameContaining("re"));
      System.out.println(productRepo.findByNameContaining("rx"));

//      productRepo.streamAllByDeletedTrue().forEach(System.out::println);
   }
}
