package victor.training.java8.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.custom.CustomJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProductRepo extends CustomJpaRepository<Product, Long> {
   List<Product> findByHiddenTrue();

   @Query("""
      SELECT p 
      FROM Product p 
      WHERE p.name LIKE '%' || ?1 || '%'
      """)
   Optional<Product> findByNameContaining(String namePart);

   Stream<Product> streamAllByDeletedTrue();

   Product findExactlyOneById(long l);
}



