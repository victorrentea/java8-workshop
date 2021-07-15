package victor.training.java8.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.java8.advanced.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProductRepo extends JpaRepository<Product, Long> {
   List<Product> findByHiddenTrue();
//@Query
   Optional<Product> findByNameContaining(String namePart);

   // 2.3 millions Products in my db
   Stream<Product> streamAllByDeletedTrue();

   Product findExactlyOneById(long l);
}
