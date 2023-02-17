package victor.training.java.advanced.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java.advanced.model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
   Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
//   Page<Order> findByActiveTruePaginated(PageRequest<Order> p); // 1 Mln orders ;)
   // incearca totusi sa eviti sa tragi stream de peste retea. in loc: mai bine paginezi cate 200/1000 o data si aduci in pagini

   // in spate Spring va deschide un ResultSet rs; cu care va itera peste rezultatele acestui query

   List<Order> findByCreationDateAfter(LocalDate when); // generate spring
}
