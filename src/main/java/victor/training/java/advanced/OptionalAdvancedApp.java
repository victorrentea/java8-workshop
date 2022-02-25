package victor.training.java.advanced;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.model.Product;
import victor.training.java.advanced.repo.ProductRepo;
import victor.training.java.advanced.repo.custom.CustomJpaRepositoryFactoryBean;

import javax.persistence.EntityManager;
import java.util.Optional;

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

      // Abuse 3: **All** callers do .get / .orElseThrow on the Optional<> you return
      // Product p = productRepo.findById(13L);


      System.out.println("When search finds: ");
      System.out.println(productRepo.findByNameContaining("re"));
      System.out.println("When search DOESN'T find, repo returns:");
      System.out.println(productRepo.findByNameContaining("rx"));

   }


   public static void excessOpt(DeliveryDto dto) {
      boolean duplicate = Optional.ofNullable(dto.recipientPerson)
          .map(name -> existsByName(name))
          .orElse(false);
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
