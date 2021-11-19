package victor.training.java.advanced;

import com.google.common.collect.ImmutableList;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
import java.util.Optional;
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

   @Transactional(readOnly = true)
   public void run(String... args) throws Exception {
      productRepo.save(new Product("Tree"));
      // ## --- Streaming queries ---
      productRepo.findAllByDeletedFalse() // imagine 1M products
          .peek(entityManager::detach) // NOSONAR because performance mem leak
          .forEach(System.out::println);

      // Also see JdbcTemplate#queryForStream
//      JdbcTemplate jdbc;
//      jdbc.queryForStream()

//      System.out.println("Search finds: " + productRepo.findByNameContaining("re"));
      System.out.println("Search DOESN'T find: " + productRepo.findByNameContaining("rx"));

      // ## --- Optional Abuse? ---
      // Abuse 1: Optional<> as method argument => SRP violation
      // TODO @see victor.training.java.advanced.Optionals

      // Abuse 2: Optional created and terminated in the same method.
      excessOpt(new DeliveryDto());

      // Abuse 3: **All** callers do .get / .orElseThrow on the Optional<> you return
      Product p2 = productRepo.getExactlyOne(13L);
//       Product p = productRepo.findById(13L).get();


   }


   public static void excessOpt(DeliveryDto dto) {

//      boolean duplicate =
       Optional.ofNullable(dto.recipientPerson)
         .map(OptionalAdvancedApp::existsByName)
          .ifPresentOrElse(b -> System.out.println("OUT: " + b),
              () -> System.out.println("OUTx: false"));
//          .orElse(false);

      // 1 recipient is null > false
      // 2 exists > true
      // 3 not eists > false

//      boolean duplicate = false;
//      if (dto.recipientPerson != null) {
//         duplicate = existsByName(dto.recipientPerson);
//      }

      // more geek:
      boolean duplicate = dto.recipientPerson != null && existsByName(dto.recipientPerson);

      if (duplicate) {
         System.out.println("DUPLICATE person!");
      }
   }

   public void method(DeliveryDto dto) {
      if (dto.recipientPerson == null) {
         System.out.println("OUT: " + existsByName(dto.recipientPerson));
      } else {
         System.out.println("OUTx: false");
      }

      Optional.ofNullable(dto.recipientPerson)
          .map(OptionalAdvancedApp::existsByName)
          .ifPresentOrElse(
              b -> System.out.println("OUT: " + b),
              () -> System.out.println("OUTx: false"));
   }


   private static boolean existsByName(String name) {
      ImmutableList<Integer> s = ImmutableList.copyOf(Stream.of(1, 2, 3, 4, 5)
          .collect(Collectors.toList()));
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


//@Value
//class X {
//   Optional<String> fff;
//}