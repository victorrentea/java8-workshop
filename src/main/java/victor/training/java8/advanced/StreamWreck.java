package victor.training.java8.advanced;

import victor.training.java8.advanced.model.Order;
import victor.training.java8.advanced.model.OrderLine;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.ProductRepo;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

// get the products frequently ordered during the past year


public class StreamWreck {
	private ProductRepo productRepo;

	public List<Product> getFrequentOrderedProducts(List<Order> orders) {
		List<Product> frequentProducts = getRecentOrderedProducts(orders).entrySet().stream()
			.filter(e -> e.getValue() >= 10)
			.map(Entry::getKey)
			.collect(toList());

		List<Product> hiddenProducts = productRepo.findByHiddenTrue();

		return frequentProducts.stream()
				.filter(Product::isActive)
				.filter(p -> !hiddenProducts.contains(p))
				.toList();

	}

	private Map<Product, Integer> getRecentOrderedProducts(List<Order> orders) {
		return orders.stream()
			.filter(this::isRecent)
			.flatMap(o -> o.getOrderLines().stream())
			.collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));
	}

	private boolean isRecent(Order o) {
		return o.getCreationDate().isAfter(LocalDate.now().minusYears(1));
	}
}


