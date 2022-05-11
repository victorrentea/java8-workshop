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
import java.sql.ResultSet;
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

   @Transactional // => 2GB in RAM
   public void run(String... args) throws Exception {
      productRepo.save(new Product("Tree"));
      // ## --- Streaming queries ---

      productRepo.findAllByDeletedFalse()
              .peek(e -> entityManager.detach(e)) // leak fara asta !
              // imagine 1M products
             .forEach(System.out::println);
      // Also see JdbcTemplate#queryForStream

      // ## --- Optional Abuse? ---
      // Abuse 1: Optional<> as method argument => SRP violation
      mare(1,2,Optional.of("pret"));
      cuPret("pret");
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

   private void mare(int id, int i1, Optional<String> pret) {
      System.out.println(id);
      System.out.println(i1);
   }
   private void cuPret(String p) {
         System.out.println("LOgica cu pret " + p);
   }


   public static void excessOpt(DeliveryDto dto) {
      // deschid optional in fct si il termin tot in fct mea

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
