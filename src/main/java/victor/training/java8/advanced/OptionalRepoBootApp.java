package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.ProductRepo;

import java.io.BufferedReader;
import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.util.stream.Stream;

@RequiredArgsConstructor
@SpringBootApplication
public class OptionalRepoBootApp implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(OptionalRepoBootApp.class, args);
   }
   private final ProductRepo productRepo;

   @Transactional//(readOnly = true)
   public void run(String... args) throws Exception {
      productRepo.save(new Product("Tree"));

      System.out.println(productRepo.findByNameContaining("re"));
      System.out.println(productRepo.findByNameContaining("rx"));

      try (Stream<Product> productStream = productRepo.streamAllByDeletedFalse()) {
         productStream.forEach(System.out::println);
      }


//      Reader reader;
//      try (BufferedReader bufferedReader = new BufferedReader(reader)) {
//         bufferedReader.lines()
//      }
//      file.delete();
//
//      try (Stream<String> lines = Files.lines(new File("a.txt").toPath())) {
//         lines.filter().map().forEach();
//      }
   }
}
