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

@RequiredArgsConstructor
//@EnableJpaRepositories(repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
@SpringBootApplication
public class OptionalRepoBootApp implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(OptionalRepoBootApp.class, args);
   }
   private final ProductRepo productRepo;

   @Transactional(readOnly=true)
   public void run(String... args) throws Exception {
      productRepo.save(new Product("Tree").setDeleted(true));
      System.out.println(productRepo.findByNameContaining("re"));
//      System.out.println(productRepo.findByNameContaining("rx")); // finds nothing

      // Optional Abuse?
      // Product p = productRepo.findById(13L);


      // Streaming queries
       productRepo.streamAllByDeletedTrue()
           .peek(entityManager::detach) // Hibernatule, nu o tine minte pe asta in Persistence Contenxt (Session) tau
           .forEach(System.out::println);
   }
   private final EntityManager entityManager;
}
