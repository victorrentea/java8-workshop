package victor.training.java8.advanced;

import victor.training.java8.advanced.model.Order;
import victor.training.java8.advanced.model.OrderLine;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.ProductRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

// get the products frequently ordered during the past year


public class StreamWreck {
   private ProductRepo productRepo;

   public List<Product> getFrequentOrderedProducts(List<Order> orders) {
      Map<Product, Integer> recentProductCounts = getRecentProductCounts(orders);

      List<Product> recentProducts = recentProductCounts.entrySet().stream()
          .filter(e -> e.getValue() >= 10)
          .map(Entry::getKey)
          .collect(toList());

      List<Long> hiddenIds = productRepo.findByHiddenTrue();

      return recentProducts.stream()
          .filter(Product::isNotDeleted)
          .filter(p -> !hiddenIds.contains(p.getId()))
          .collect(toList());
   }

   private Map<Product, Integer> getRecentProductCounts(List<Order> orders) {
      return orders.stream()
          .filter(this::duringLastYear)
          .flatMap(o -> o.getOrderLines().stream())
          .collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));
   }

   private boolean duringLastYear(Order o) {
      return o.getCreationDate().isAfter(LocalDate.now().minusYears(1));
   }

   public static void main(String[] args) {
      Stream<Integer> nn = Stream.of(1, 2, 3, 4);
      nn.forEach(System.out::println);
      nn.forEach(System.out::println);
   }
}


