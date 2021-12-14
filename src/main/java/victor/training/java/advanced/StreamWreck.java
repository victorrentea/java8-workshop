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
      Map<Product, Integer> productCounts = orders.stream()
          .filter(this::isRecent)
          .flatMap(o -> o.getOrderLines().stream())
          .collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));

      List<Product> frequentProducts = productCounts.entrySet().stream()
          .filter(e -> e.getValue() >= 10)
          .map(Entry::getKey)
          .collect(toList());

       List<Product> hiddenProducts = productRepo.findByHiddenTrue();
      return frequentProducts.stream()
          .filter(p -> !p.isDeleted())
          .filter(not(Product::isDeleted)) // :: maniacs
          .filter(Product::isActive)

          // N+1 queries
          .filter(product -> !hiddenProducts.contains(product))
//          .filter(not(hiddenProducts::contains))
          .collect(toList());
   }

   private boolean isRecent(Order order) {// keep it private here as it's specific to my usecase
      return order.getCreationDate().isAfter(LocalDate.now().minusYears(1));
   }
}


