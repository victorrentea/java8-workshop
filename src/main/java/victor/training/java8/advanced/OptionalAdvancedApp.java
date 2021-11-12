package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import victor.training.java8.advanced.model.Order;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.ProductRepo;
import victor.training.java8.advanced.repo.custom.CustomJpaRepositoryFactoryBean;

import java.util.Optional;

@RequiredArgsConstructor
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
@SpringBootApplication
public class OptionalAdvancedApp implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(OptionalAdvancedApp.class, args);
   }
   private final ProductRepo productRepo;

   public void run(String... args) throws Exception {
      productRepo.save(new Product("Tree"));
      System.out.println(productRepo.findByNameContaining("re"));
//      System.out.println(productRepo.findByNameContaining("rx")); // finds nothing

      // Optional Abuse?
      // Product p = productRepo.findById(13L);

      excessOpt(new DeliveryDto());

      // Streaming queries
      // productRepo.streamAllByDeletedTrue().forEach(System.out::println);
   }


   public static void excessOpt(DeliveryDto dto) {
      Boolean duplicate = Optional.ofNullable(dto.recipientPerson)
          .map(name -> existsByName(name))
          // ...
          .orElse(false);
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
