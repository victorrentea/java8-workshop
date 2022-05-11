package victor.training.java.advanced;

import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.model.OrderLine;
import victor.training.java.advanced.model.Product;
import victor.training.java.advanced.repo.ProductRepo;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

public class StreamWreck {
   private ProductRepo productRepo;

   public List<Product> getFrequentOrderedProducts(List<Order> orders) {
      Set<Long> hiddenProductIds = new HashSet<>(productRepo.getHiddenProductIds());
      Map<Product, Integer> recentBoughtProducts = countRecentProducts(orders);
      List<Product> frequentRecentProducts = recentBoughtProducts.entrySet().stream()
              .filter(e -> e.getValue() >= 10)
              .map(Entry::getKey)
              .collect(toList());

      // CR: if  many frequen

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


