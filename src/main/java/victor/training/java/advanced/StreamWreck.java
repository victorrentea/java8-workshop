package victor.training.java.advanced;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.model.OrderLine;
import victor.training.java.advanced.model.Product;
import victor.training.java.advanced.repo.ProductRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Slf4j
public class StreamWreck {
   private ProductRepo productRepo;

   public List<Product> getFrequentOrderedProducts(List<Order> orders) {
      List<Product> hiddenProducts = productRepo.findByHiddenTrue();

      Map<Product, Integer> productToCount = orders.stream()
          .filter(this::isRecent)
          .flatMap(o -> o.getOrderLines().stream())
          .collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));

      Stream<Product> trendingProducts = productToCount.entrySet().stream()
          .filter(e -> e.getValue() >= 10)
          .map(Entry::getKey);

      if (trendingProducts.count() > 50) {
         log.debug("Multe tata");
      }

      return trendingProducts
          //produsele recente care au fost comandate mai mult de 10 ori

          .filter(Product::isActive) // 100 de produse
          .filter(p -> !hiddenProducts.contains(p)) // apelezi 100 x SELECT
          .collect(toList());
   }

   private boolean isRecent(Order order) {
      return order.getCreationDate().isAfter(LocalDate.now().minusYears(1));
   }

}


