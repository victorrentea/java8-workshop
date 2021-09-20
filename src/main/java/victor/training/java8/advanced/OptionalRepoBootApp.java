package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.ProductRepo;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.stream.Stream;

@RequiredArgsConstructor
@SpringBootApplication
public class OptionalRepoBootApp implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(OptionalRepoBootApp.class, args);
   }
   private final ProductRepo productRepo;
   private final EntityManager em;

   @Transactional
   public void run(String... args) throws Exception {
      productRepo.save(new Product("Tree").setDeleted(true));
      System.out.println(productRepo.findByNameContaining("re"));
      System.out.println(productRepo.findByNameContaining("rx")); // finds nothing

      // attached/detached

      // Optional Abuse?
      // Product p = productRepo.findById(13L);

      // Streaming queries
       productRepo.streamAllByDeletedTrue()
           .peek(em::detach)
           .forEach(System.out::println);

      // Spring Batch > ia in pagini de cate eg 500 randuri si le scrie. Close connection; open

      try (Stream<String> lines = Files.lines(new File("test.ok.txt").toPath())) {

         lines.forEach(line -> System.out.println("READ: " + line));
      }

      try (FileReader fileReader = new FileReader("a.txt")) {
         fileReader.read();
      }

   }
}
