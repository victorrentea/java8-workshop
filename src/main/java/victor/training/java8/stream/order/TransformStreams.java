package victor.training.java8.stream.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.Order.Status;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

class OrderMapper {
}

public class TransformStreams {
	private OrderMapper orderMapper;

	/**
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */

	public void method(String s) {

	}
	public List<OrderDto> p01_toDtos(List<Order> orders) {
		int a = Integer.parseInt("1");

		Object f0 = (Function<String,Integer>)((String s)-> Integer.parseInt(s)); // avoid
		Function<String,Integer> f1 = s -> Integer.parseInt(s);
		Function<String,Integer> f2 = Integer::parseInt;

		String s = "aa";
		method(s);

		Order myOrder = new Order();
		Status status = myOrder.getStatus();

		Function<Order, Status> f3 = Order::getStatus; // referi o metoda de instanta dar intr-un fel "static"

		Supplier<Status> f4 = myOrder::getStatus;

		Function<Order, OrderDto> f5 = order2 -> new OrderDto(order2);
		BiFunction<OrderMapper, Order, OrderDto> f6 = (orderMapper1, order1) -> new OrderDto(order1);
		OrderDto dto = f6.apply(orderMapper, myOrder);

//		Function<Order, OrderDto> mapFunc = order -> orderMapper.toDto(order);

		return orders.stream()
			.map(OrderDto::new)
			.collect(toList());
	}


	/**
	 * Note: Order.getPaymentMethod()
	 */
	public Set<PaymentMethod> p02_getUsedPaymentMethods(Customer customer) {
		return customer.getOrders().stream().map(Order::getPaymentMethod).collect(toSet());
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
		 return customer.getOrders().stream().collect(toMap(
		 	Order::getId, Function.identity(),(v1,v2) -> v1 ));
	}
	
	/** 
	 * Orders grouped by Order.paymentMethod
	 */
	public Map<PaymentMethod, List<Order>> p05_getProductsByPaymentMethod(Customer customer) {
		return customer.getOrders().stream().collect(groupingBy(Order::getPaymentMethod));
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
		List<OrderLine> allLines = customer.getOrders().stream()
			.flatMap(order -> order.getOrderLines().stream())
			.collect(toList());

		return allLines.stream()
			.collect(groupingBy(OrderLine::getProduct, summingLong(OrderLine::getCount)));

	}
	
	/**
	 * All the unique products bought by the customer, 
	 * sorted by Product.name.
	 */
	public List<Product> p07_getAllOrderedProducts(Customer customer) {
		return customer.getOrders().stream()
			.flatMap(order -> order.getOrderLines().stream())
			.map(OrderLine::getProduct)
			.distinct()
			.sorted(comparing(Product::getName))
			.collect(toList());
//			.distinct() // collecting operators
//			.sorted(createDate) // collecting operators ; trag tot d esus. OOM daca streamul era FFF mare (din tabela/fisier mare)
//			.forEach(System.out::println);
	}
	
	
	/**
	 * The names of all the products bought by Customer,
	 * sorted and then concatenated by ",".
	 * Example: "Armchair,Chair,Table".
	 * Hint: Reuse the previous function.
	 */
	public String p08_getProductsJoined(Customer customer) {
		return p07_getAllOrderedProducts(customer)
			.stream()
			.map(Product::getName)
			.collect(joining(","));
	}
	
	/**
	 * Sum of all Order.getTotalPrice(), truncated to Long.
	 */
	public Long p09_getApproximateTotalOrdersPrice_geek(Customer customer) {
		// TODO +, longValue(), reduce()
		BinaryOperator<BigDecimal> f = (b1, b2) -> b1.add(b2);
		BiFunction<BigDecimal, BigDecimal,BigDecimal> f2 = (b1, b2) -> b1.add(b2);
		BiFunction<BigDecimal, BigDecimal,BigDecimal> f3 = BigDecimal::add;
		return customer.getOrders().stream()
			.map(Order::getTotalPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add)
			.longValue();
	}
	public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
//		return customer.getOrders().stream()
//			.map(Order::getTotalPrice)
//			.mapToLong(BigDecimal::longValue)
//			.sum();
		return customer.getOrders().stream()
			.mapToLong(order -> order.getTotalPrice().longValue())
			.sum();
	}

	public Long p09_getApproximateTotalOrdersPriceJuniorAttempt(Customer customer) {
		AtomicLong totalPrice = new AtomicLong(0);
		customer.getOrders().stream()
			.map(order -> order.getTotalPrice().longValue())
			.forEach(price -> {
				totalPrice.addAndGet(price);
			});
		return totalPrice.longValue();
	}

	// in general for each de evitat cand ai putea returna ceva in loc.

	// programarea clasica (imperativa), de liceu = "FA AIA", SCHIMBA AIA
	// programarea functioanla: CALCULEAZA SI INTOARCE.




}
