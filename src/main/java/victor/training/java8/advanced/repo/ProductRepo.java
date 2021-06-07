package victor.training.java8.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java8.advanced.model.Product;

import java.util.List;
import java.util.stream.Stream;

public interface ProductRepo extends JpaRepository<Product, Long> {
   List<Long> findByHiddenTrue();

   Product findByNameContaining(String namePart);

   Stream<Product> streamAllByDeletedTrue(); // jde-milioane
}
