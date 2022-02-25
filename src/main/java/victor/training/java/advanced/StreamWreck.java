package victor.training.java.advanced;

import lombok.extern.slf4j.Slf4j;
import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.model.OrderLine;
import victor.training.java.advanced.model.Product;
import victor.training.java.advanced.repo.ProductRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

@Slf4j
public class StreamWreck {
   private ProductRepo productRepo;

   public List<Product> getFrequentOrderedProducts(List<Order> orders) {
      List<Product> trendingProducts = trendingProducts(orders);
      if (trendingProducts.size() > 50) {
         log.debug("Multe tata");
      }

      List<Product> hiddenProducts = productRepo.findByHiddenTrue();
      return trendingProducts.stream()
          //produsele recente care au fost comandate mai mult de 10 ori

          .filter(p->!p.isDeleted())
          .filter(Product::isActive) // 100 de produse
          .filter(not(Product::isDeleted))

          .filter(p -> !hiddenProducts.contains(p)) // apelezi 100 x SELECT
          .collect(toList());
   }

   private List<Product> trendingProducts(List<Order> orders) {
      Map<Product, Integer> productToCount = countProducts(orders);

      List<Product> trendingProducts = productToCount.entrySet().stream()
          .filter(e -> e.getValue() >= 10)
          .map(Entry::getKey)
          .collect(toList());
      return trendingProducts;
   }

   private Map<Product, Integer> countProducts(List<Order> orders) {
      return orders.stream()
          .filter(this::isRecent)
          .flatMap(o -> o.getOrderLines().stream())
          .collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));
   }

   private boolean isRecent(Order order) {
      return order.getCreationDate().isAfter(LocalDate.now().minusYears(1));
   }

}


