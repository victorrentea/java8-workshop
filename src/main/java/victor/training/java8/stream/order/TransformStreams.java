package victor.training.java8.stream.order;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;

@Service
public class TransformStreams {
	@Autowired
	OrderMapper orderMapper;

	/**
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */
	public List<OrderDto> p01_toDtos(List<Order> orders) {

		Function<Order, OrderDto> convertFunc = order -> orderMapper.convert(order);
		Function<Order, OrderDto> convertFunc2 = orderMapper::convert;

		BiFunction<OrderMapper, Order, OrderDto> convertReferitaStatic =  OrderMapper::convert;
//		convertReferitaStatic.apply(orderMapper, orders);

		Order order = new Order();
		Supplier<Boolean> active = order::isActive;// f():Boolean

		// target typing: acceeasi expresie lambda/meth ref inseamna alte lucruri in fct de tipul variabilei in care o pui
		Function<Order, Boolean> active1 = Order::isActive; // f(Order):Boolean
		Predicate<Order> active2 = Order::isActive; // f(Order):Boolean

//		Object a = Order::isActive; // nu compileaza pentru ca Java nu stie CE tip vrei sa obtii.
		Object a = (Predicate<Order>)Order::isActive; // nu compileaza pentru ca Java nu stie CE tip vrei sa obtii.


		BiFunction<Integer, Integer, String> sumToStr = (i1, i2) -> i1 + i2 + "";

		Supplier<Double> randSup = Math::random;

		Consumer<BigDecimal> tocatorDeBani = bd -> System.out.println(bd);


		Function<String, Integer> parse = s -> Integer.parseInt(s); // String ->Integer
		Function<String, Integer> parse2 = Integer::parseInt; // String ->Integer

		Supplier<Date> dateSupplier = Date::new;
		Function<Long, Date> dateFunc = Date::new;

		return orders.stream()
			.map(OrderDto::new)
			.collect(toList());
		//		return orders.stream().map(this::convert).collect(toList());
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
