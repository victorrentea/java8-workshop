package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.ProductRepo;

import javax.persistence.EntityManager;
import java.io.File;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.util.stream.Stream;

@RequiredArgsConstructor
@SpringBootApplication
public class OptionalRepoBootApp implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(OptionalRepoBootApp.class, args);
   }
   private final ProductRepo productRepo;

//   @Bean
//   public TimedAspect timedAspect() {
//      return new TimedAspect(meterRegistry);
//   }

   @Transactional
   public void run(String... args) throws Exception {
      productRepo.save(new Product("Tree"));
      System.out.println(productRepo.findByNameContaining("re"));
      System.out.println(productRepo.findByNameContaining("rx"));

      // Optional Abuse
//      Product product = productRepo.findById(13L).get(); // spring data 2.
//      Product p2 = productRepo.findById(13L).orElseThrow(() -> new IllegalArgumentException("Dragutz"));
//      Product p3 = productRepo.findExactlyOneById(13L); // mai convenabil pt ca nu ma obliga peste tot sa fac .get()
      productRepo.streamAllByDeletedTrue()
          .peek(em::detach)
          .forEach(System.out::println);

      Stream<String> lines = Files.lines(new File("data.txt").toPath());


      lines.map(s -> parse(s)).forEach(productRepo::save);
   }

   public Product parse(String s) {
      return null;
   }

   private final EntityManager em;
}
