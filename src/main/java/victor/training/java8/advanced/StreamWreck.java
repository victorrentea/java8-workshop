package victor.training.java8.advanced;

import victor.training.java8.advanced.model.Order;
import victor.training.java8.advanced.model.OrderLine;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.ProductRepo;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

// get the products frequently ordered during the past year


public class StreamWreck {
	private ProductRepo productRepo;

	public List<Product> getFrequentOrderedProducts(List<Order> orders) {
		return orders.stream()
				.filter(o -> o.getCreationDate().isAfter(LocalDate.now().minusYears(1)))

				.map(Order::getOrderLines)
				.flatMap(Collection::stream)

//				.flatMap(o -> o.getOrderLines().stream())

				.collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)))
				.entrySet()
				.stream()
				.filter(e -> e.getValue() >= 10)
				.map(Entry::getKey)
				.filter(p -> !p.isDeleted())
				.filter(p -> !productRepo.findByHiddenTrue().contains(p.getId()))
				.collect(toList());
	}
	public void method(List<Order> orders) {
//		List<String> productName = orders.stream()
//			.map(Order::getOrderLines)
//			.flatMap(Collection::stream)
//			.map(OrderLine::getProduct)
//			.map(Product::getName)
//			.collect(toList());
			List<String> productName = orders.stream()
			.flatMap(order -> order.getOrderLines().stream())
			.map(orderLine -> orderLine.getProduct().getName())
			.collect(toList());
	}
}




