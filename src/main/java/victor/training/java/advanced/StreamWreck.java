package victor.training.java.advanced;

import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.model.OrderLine;
import victor.training.java.advanced.model.Product;
import victor.training.java.advanced.repo.ProductRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

public class StreamWreck {
   private ProductRepo productRepo;

   public List<Product> getFrequentOrderedProducts(List<Order> orders) {
      Map<Product, Integer> productToCount = getProductsToCount(orders);

      List<Product> majorSales = productToCount.entrySet().stream()
          .filter(e -> e.getValue() >= 10)
          .map(Entry::getKey)
          .toList();

      // no call to repo until here !
      List<Product> hiddenProducts = productRepo.findByHiddenTrue();
      return majorSales.stream()
          .filter(p -> !p.isDeleted())
          .filter(Product::isActive)
          .filter(not(Product::isDeleted))

          .filter(not(hiddenProducts::contains))
          .collect(toList());
   }

   private Map<Product, Integer> getProductsToCount(List<Order> orders) {
      return orders.stream()
          .filter(this::isRecent)
          .flatMap(o -> o.getOrderLines().stream())
          .collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));
   }

   private boolean isRecent(Order order) {
      return order.getCreationDate().isAfter(LocalDate.now().minusYears(1));
   }
}


