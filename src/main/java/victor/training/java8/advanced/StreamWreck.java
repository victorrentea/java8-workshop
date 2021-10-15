package victor.training.java8.advanced;

import victor.training.java8.advanced.model.Order;
import victor.training.java8.advanced.model.OrderLine;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.ProductRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

// get the products frequently ordered during the past year


public class StreamWreck {
	private ProductRepo productRepo;

	public List<Product> getFrequentOrderedProducts(List<Order> orders) {

		List<Product> frequentProducts = getRecentProductCounts(orders).entrySet().stream()
			.filter(e -> e.getValue() >= 10)
			.map(Entry::getKey)
			.collect(toList());

		if (frequentProducts.size() > 3) { // operatie terminala > streamul este "consumat" de aici in jos
			System.out.println("A cumparat mult fraeru");
		}

		Set<Long> hiddenProductIds = productRepo.findByHiddenTrue().stream().map(Product::getId).collect(toSet());
		return frequentProducts.stream()

			.filter(p -> !p.isDeleted())
			.filter(not(Product::isDeleted))

			.filter(product -> !hiddenProductIds.contains(product.getId()))
			.collect(toList());
	}

	private Map<Product, Integer> getRecentProductCounts(List<Order> orders) {
		return orders.stream()
			.filter(this::isRecent)
			.flatMap(o -> o.getOrderLines().stream())
			.collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));
	}

	private boolean isRecent(Order o) {
		return o.getCreationDate().isAfter(LocalDate.now().minusYears(1));
	}

}


