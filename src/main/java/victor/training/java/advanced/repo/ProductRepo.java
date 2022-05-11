package victor.training.java.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java.advanced.model.Product;
import victor.training.java.advanced.repo.custom.CustomJpaRepository;

import java.util.List;
import java.util.stream.Stream;

public interface ProductRepo extends CustomJpaRepository<Product, Long> {
   List<Product> findByHiddenTrue();

   Product findByNameContaining(String namePart);
//   List<Long> getHiddenProductIds();

   Stream<Product> findAllByDeletedFalse();

}
