package victor.training.java.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.model.Product;
import victor.training.java.advanced.repo.custom.CustomJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProductRepo extends CustomJpaRepository<Product, Long> {
   List<Product> findByHiddenTrue();

   // crapa daca nu gaseste
   Product findByNameContaining(String namePart);



   // da optional.empty daca nu gaseste.
//   @Query("SELECT .... WHERE ... LAST....")
   Optional<Order> findLastOrderByCustomer(long customerId);

   Stream<Product> findAllByDeletedFalse();

}
