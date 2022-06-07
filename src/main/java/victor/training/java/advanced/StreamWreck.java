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

import static java.util.stream.Collectors.*;

@Slf4j
public class StreamWreck {
   private ProductRepo productRepo;

   public List<Product> getFrequentOrderedProducts(List<Order> orders) {
//      Mono/Flux
      List<Product> frequentBoughtProducts = getRecentOrderedProducts(orders).entrySet()
              .stream()
              .filter(entry -> entry.getValue() >= 10)
              .map(Entry::getKey)
              .toList();

      if (frequentBoughtProducts.size() > 10) {
         log.error("Hooray");
      }

      List<Product> hiddenProducts = productRepo.findByHiddenTrue();
      return frequentBoughtProducts.stream()
//          .filter(not(Product::isDeleted))
           .filter(Product::isActive)
          .filter(p -> !hiddenProducts.contains(p))
          .collect(toList());
   }

   private Map<Product, Integer> getRecentOrderedProducts(List<Order> orders) {
      return orders.stream()
              .filter(this::isRecent)
              .flatMap(o -> o.getOrderLines().stream())
              .collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));
   }

   private boolean isRecent(Order order) {
      LocalDate oneYearAgo = LocalDate.now().minusYears(1);
      return order.getCreationDate().isAfter(oneYearAgo);
   }

}


