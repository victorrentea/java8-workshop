package victor.training.java8.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.java8.advanced.model.Product;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProductRepo extends JpaRepository<Product, Long> {
   List<Product> findByHiddenTrue();

//@Query(value = """
//                SELECT p.id
//               FROM PRODUCTS p WHERE p.name LIKE ?1
//               """
//    , nativeQuery = true) // JPQL ~HQL --> SQL --> WHERE rulat de DB

   //   @Query("""
//            SELECT p
//            FROM Product p
//            WHERE p.name LIKE ?1""") // JPQL ~HQL --> SQL --> WHERE rulat de DB
   Optional<Long> findByNameContaining2(String namePart);


   Optional<Product> findByNameContaining(String namePart);

   Stream<Product> streamAllByDeletedTrue();// 1MLN // pe sub agata un ResultSert din care va trage date.
   // daca tu nu ai Tx deschisa, conn se elivereaza si nu are CUM sa-ti dea un Stream<>(ResultSet)

   Product findExactlyOneById(long l);
}
