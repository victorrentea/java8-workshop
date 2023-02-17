package victor.training.java.stream.order;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.*;
import java.util.stream.Collectors;

import victor.training.java.stream.order.dto.OrderDto;
import victor.training.java.stream.order.entity.Customer;
import victor.training.java.stream.order.entity.Order;
import victor.training.java.stream.order.entity.OrderLine;
import victor.training.java.stream.order.entity.Product;
import victor.training.java.stream.order.entity.Order.PaymentMethod;

public class TransformStreams {

	/**
	 * Transform all entities to DTOs.
	 * use .map
	 */
	public List<OrderDto> p01_toDtos(List<Order> orders) {

		// REFERIREA unei metoda de instana printr-un mod STATIC TransformStreams::
		// nu merge pentru ca fct referita asa primeste ca prim parametru INSTANTA pe care ruleaza
//		Function<Order, OrderDto> nu= TransformStreams::toDto;

		BiFunction<TransformStreams, Order, OrderDto> da= TransformStreams::toDto;
		OrderDto r = da.apply(this, new Order());

		Function<Order, OrderDto> ff= this::toDto;
		OrderDto r2 = ff.apply(new Order());

		String firstName = "a";
		Supplier<Integer> s = firstName::length; // f():int

		Predicate<Order> o = Order::isActive;

		Consumer<Object> c = System.out::println;
		orders.forEach(System.out::println);

		Function<Order, OrderDto> omg = OrderDto::new;
		return orders.stream()
//						.map(this::toDto)
						.map(omg)
						.collect(Collectors.toList());
		
	}

	private OrderDto toDto(Order order) {
		OrderDto dto = new OrderDto();
		dto.totalPrice = order.getTotalPrice();
		dto.creationDate = order.getCreationDate();
		return dto;
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
