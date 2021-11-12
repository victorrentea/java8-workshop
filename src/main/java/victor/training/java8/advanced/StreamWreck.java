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
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

// get the products frequently ordered during the past year


public class StreamWreck {
	private ProductRepo productRepo;

	public List<Product> getFrequentOrderedProducts(List<Order> orders) {
		List<Product> frequentProducts = getFrequentProducts(orders);

		// 100 products
		List<Product> products = productRepo.findByHiddenTrue();
		return frequentProducts.stream()
			.filter(p -> !p.isDeleted())
			.filter(not(Product::isDeleted))
			.filter(Product::isActive)

			.filter(p -> !products.contains(p))
			.collect(toList());
	}

	private List<Product> getFrequentProducts(List<Order> orders) {
		Map<Product, Integer> productItems = orders.stream()
			.filter(this::isRecent)
			.flatMap(o -> o.getOrderLines().stream())
			.collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));

		return productItems.entrySet().stream()
			.filter(e -> e.getValue() >= 10)
			.map(Entry::getKey)
			.collect(toList());
	}

	private boolean isRecent(Order o) {
		return o.isAfter(LocalDate.now().minusYears(1));
	}

}


