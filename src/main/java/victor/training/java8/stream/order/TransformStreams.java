package victor.training.java8.stream.order;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.Order.Status;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;

class OrderMapper {
}

public class TransformStreams {
	public String instanceMethod() {
		return "";
	}

	private OrderMapper mapper;
	/**
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */
	public List<OrderDto> p01_toDtos(List<Order> orders) {

		Function<String, Integer> f2 = s -> Integer.parseInt(s);
		Function<String, Integer> f2bis = Integer::parseInt;

		Supplier<Long> f3 = System::currentTimeMillis;

		Order anOrder = new Order();
		Supplier<Status> f4 = anOrder::getStatus;
		Supplier<Customer> f5 = anOrder::getCustomer;

		Function<Order, Status> f6 = Order::getStatus;

//		Function<Order, OrderDto> f1 = this::toDto;

		Supplier<String> f7 = this::instanceMethod;
		Function<TransformStreams, String> f8 = TransformStreams::instanceMethod;

		BiFunction<TransformStreams, List<Order>, List<OrderDto>> f9 = TransformStreams::p01_toDtos;

		BiFunction<OrderMapper, Order, OrderDto> bif = (orderMapper, order1) -> new OrderDto(order1);

		return orders.stream()
			.map(OrderDto::new)
			.collect(toList());
	}



	/**
	 * Note: Order.getPaymentMethod()
	 */
	public Set<PaymentMethod> p02_getUsedPaymentMethods(Customer customer) {
		return null; 
	}
	
	/**
	 * When did the customer created orders ?
	 * Note: Order.getCreationDate()
	 */
	public SortedSet<LocalDate> p03_getOrderDatesAscending(Customer customer) {
		return null; 
	}
	
	
	/**
	 * @return a map order.id -> order
	 */
	public Map<Long, Order> p04_mapOrdersById(Customer customer) {
		return null; 
	}
	
	/** 
	 * Orders grouped by Order.paymentMethod
	 */
	public Map<PaymentMethod, List<Order>> p05_getProductsByPaymentMethod(Customer customer) {
		return null; 
	}
	
	// -------------- MOVIE BREAK :p --------------------
	
	/** 
	 * A hard one !
	 * Get total number of products bought by a customer, across all her orders.
	 * Customer --->* Order --->* OrderLines(.count .product)
	 * The sum of all counts for the same product.
	 * i.e. SELECT PROD_ID, SUM(COUNT) FROM PROD GROUPING BY PROD_ID
	 */
	public Map<Product, Long> p06_getProductCount(Customer customer) {
		
		List<OrderLine> allLines = new ArrayList<>();
		
		for (Order order : customer.getOrders()) {
			allLines.addAll(order.getOrderLines());
		}
		return null; 
		
	}
	
	/**
	 * All the unique products bought by the customer, 
	 * sorted by Product.name.
	 */
	public List<Product> p07_getAllOrderedProducts(Customer customer) {
		return null; 
	}
	
	
	/**
	 * The names of all the products bought by Customer,
	 * sorted and then concatenated by ",".
	 * Example: "Armchair,Chair,Table".
	 * Hint: Reuse the previous function.
	 */
	public String p08_getProductsJoined(Customer customer) {
		return null; 
	}
	
	/**
	 * Sum of all Order.getTotalPrice(), truncated to Long.
	 */
	public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
		// TODO +, longValue(), reduce()
		return null;
	}
}
