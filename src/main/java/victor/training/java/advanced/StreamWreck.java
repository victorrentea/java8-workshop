package victor.training.java.advanced;

import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.model.OrderLine;
import victor.training.java.advanced.model.Product;
import victor.training.java.advanced.repo.ProductRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map.Entry;

import static java.util.stream.Collectors.*;

public class StreamWreck {
   private ProductRepo productRepo;

   public List<Product> getFrequentOrderedProducts(List<Order> orders) {
      return orders.stream()
          .filter(o -> o.getCreationDate().isAfter(LocalDate.now().minusYears(1)))
          .flatMap(o -> o.getOrderLines().stream())
          .collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)))
          .entrySet()
          .stream()
          .filter(e -> e.getValue() >= 10)
          .map(Entry::getKey)
          
          
          .filter(Product::isActive)
          
          
          
          .filter(p -> !productRepo.findByHiddenTrue().contains(p))
          .collect(toList());
   }

}


