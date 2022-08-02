package victor.training.java.advanced;

import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.model.OrderLine;
import victor.training.java.advanced.model.Product;
import victor.training.java.advanced.repo.ProductRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static java.time.LocalDate.now;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

public class StreamWreck {
   private ProductRepo productRepo;

   public List<Product> getFrequentOrderedProducts(List<Order> orders) {
      Map<Product, Integer> recentProductCounts = orders.stream()
              .filter(StreamWreck::isRecent)
              .flatMap(order -> order.getOrderLines().stream())
              .collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));


      List<Product> frequentProducts = recentProductCounts.entrySet().stream()
              .filter(e -> e.getValue() >= 10)
              .map(Entry::getKey)
              .toList();

      if (frequentProducts.size() > 10) {
         System.out.println(":)");
      }

      List<Product> hiddenProducts = productRepo.findByHiddenTrue();

      return frequentProducts.stream()
          .filter(not(Product::isDeleted))
          .filter(not(hiddenProducts::contains))
          .collect(toList());
   }

   private static boolean isRecent(Order order) {
      return order.getCreationDate().isAfter(now().minusYears(1));
   }
}


