package victor.training.java8.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java8.advanced.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProductRepo extends JpaRepository<Product, Long> {
   List<Product> findByHiddenTrue();

   Optional<Product> findByNameContaining(String namePart);

   Stream<Product> streamAllByDeletedTrue();

   Product findExactlyOneById(long l);
}
