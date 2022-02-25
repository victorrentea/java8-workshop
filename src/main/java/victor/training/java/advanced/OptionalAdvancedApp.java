package victor.training.java.advanced;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.Unchecked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.model.Product;
import victor.training.java.advanced.repo.ProductRepo;
import victor.training.java.advanced.repo.custom.CustomJpaRepositoryFactoryBean;

import javax.persistence.EntityManager;
import java.io.File;
import java.nio.file.Files;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
@SpringBootApplication
public class OptionalAdvancedApp implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(OptionalAdvancedApp.class, args);
   }

   private final ProductRepo productRepo;
   private final EntityManager entityManager;

   @Transactional
   public void run(String... args) throws Exception {
      productRepo.save(new Product("Tree"));

//      List<String> stringuri =  new ArrayList<>();
//      stringuri.stream()

      File f = new File("test.ok.txt");
      try (Stream<String> linesStream = Files.lines(f.toPath())) {
         linesStream.forEach(System.out::println);
      }
//      f.delete();

      // ## --- Streaming queries ---
      productRepo.findAllByDeletedFalse() // imagine 1M products
          .peek(product -> entityManager.detach(product)) // good practice daca intorci Stream<> din met din SPring Data JPA
          .forEach(System.out::println);


      // Also see JdbcTemplate#queryForStream

      // ## --- Optional Abuse? ---
      // Abuse 1: Optional<> as method argument => SRP violation
      // TODO @see victor.training.java.advanced.Optionals

      // Abuse 2: Optional created and terminated in the same method.
      excessOpt(new DeliveryDto());

      // Abuse 3bis: Optional pe getteri de DTO --> JSON

      // Abuse 3: **All** callers do .get / .orElseThrow on the Optional<> you return
       Product p = productRepo.findOneById(13L);


      System.out.println("When search finds: ");
      System.out.println(productRepo.findByNameContaining("re"));
      System.out.println("When search DOESN'T find, repo returns:");
      System.out.println(productRepo.findByNameContaining("rx"));


   }

   public void chemi() {
      Supplier<Stream<String>> supplierDeStreamuri = Unchecked.supplier(() -> Files.lines(new File("test.ok.txt").toPath()));
      method(supplierDeStreamuri);
   }

   public void method(Supplier<Stream<String>> a) {
      try (Stream<String> stringStream = a.get()) {
         stringStream.collect(Collectors.toList());
      }

      a.get().collect(Collectors.toList());
   }

   public static void excessOpt(DeliveryDto dto) {
//      boolean duplicate = Optional.ofNullable(dto.recipientPerson)
//          .map(name -> existsByName(name))
//          .orElse(false);

      boolean duplicate = false;
      if (dto.recipientPerson != null) {
         duplicate = existsByName(dto.recipientPerson);
      }

      if (duplicate) {
         System.out.println("DUPLICATE person!");
      }
   }

   private static boolean existsByName(String name) {
      return true;
   }

   public void schizoMethod(Long customer, Optional<Order> orderOpt) {
      System.out.println("Logic Before");
      if (orderOpt.isPresent()) {
         System.out.println("Logic With order");
      }
      System.out.println("Logic After");

   }
}
