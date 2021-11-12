package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.ProductRepo;
import victor.training.java8.advanced.repo.custom.CustomJpaRepositoryFactoryBean;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.util.Optional;

@RequiredArgsConstructor
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
@SpringBootApplication
public class OptionalRepoBootApp implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(OptionalRepoBootApp.class, args);
   }
   private final ProductRepo productRepo;
   private final EntityManager entityManager;

   @Transactional/*(readOnly = true)*/
   public void run(String... args) throws Exception {
      Long productId = productRepo.save(new Product("Tree")).getId();
      productRepo.save(new Product("Boom").setDeleted(true)    ).getId();


      Product product = productRepo.findById(productId).get();


      productRepo.streamAllByDeletedTrue()
          // avoids Hirnate piling up copies of all entities given to you
          .peek(e -> entityManager.detach(e)) // side effects BEAH but necessary for adults (performance)

          // Strange:
          //.map(e-> { entityManager.detach(e); return e;}) // map = pure functions changing data. UNEXPECTED SIDE EFFECT

          .forEach(System.out::println);

      System.out.println(productRepo.findByNameContaining("re"));
      System.out.println(productRepo.findByNameContaining("NOT FOUD")); // throws now
   }

   // I don't fully agree either, Rob ;)
   //Let me explain: If the theoretical idea's behind Optional were supported fully, and Optional was commonplace, the automatic wrapping of nullables would be more sensible.
   //(As it stands, it's definitely pretty abusive)


    //Problems:
}
