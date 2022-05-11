package victor.training.java.advanced;

import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.model.OrderLine;
import victor.training.java.advanced.model.Product;
import victor.training.java.advanced.repo.ProductRepo;

import javax.swing.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

@Slf4j
public class StreamWreck {
   private ProductRepo productRepo = Mockito.mock(ProductRepo.class);

   public static void main(String[] args) {
      new StreamWreck().getFrequentOrderedProducts(Collections.emptyList());
   }
   public List<Product> getFrequentOrderedProducts(List<Order> orders) {
      Set<Long> hiddenProductIds = new HashSet<>(/*productRepo.getHiddenProductIds()*/ );
      Map<Product, Integer> recentBoughtProducts = countRecentProducts(orders);
      List<Product> frequentRecentProducts = recentBoughtProducts.entrySet().stream()
              .filter(e -> e.getValue() >= 10)
              .map(Entry::getKey)
              .collect(toList())
              ;

      // CR: if  many frequent products > raise alert
//      if (frequentRecentProducts.count() > 1000) {
//         log.warn("VALEU!");
//      }

      return frequentRecentProducts.stream()
//				.filter(p -> !p.isDeleted())
              .filter(not(Product::isDeleted))
//				.filter(p -> p.isActive())
              .filter(p -> !hiddenProductIds.contains(p.getId()))
              .collect(toList());
   }

   private Map<Product, Integer> countRecentProducts(List<Order> orders) {
      return orders.stream()
              .filter(this::isRecent)
              .flatMap(o -> o.getOrderLines().stream())
              .collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));
   }

   private boolean isRecent(Order o) {
      return o.getCreationDate().isAfter(LocalDate.now().minusYears(1));
   }

}


