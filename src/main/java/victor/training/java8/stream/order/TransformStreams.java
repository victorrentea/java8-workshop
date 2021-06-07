package victor.training.java8.stream.order;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;

import static java.util.stream.Collectors.*;

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
//			.map(o -> {  <-- headless function : avoid !
//				OrderDto dto = new OrderDto();
//				dto.totalPrice = order.getTotalPrice();
//				dto.creationDate = order.getCreationDate();
//				return dto;
//			})
			.collect(toList());
		//		return orders.stream().map(this::convert).collect(toList());
	}



	/**
	 * Note: Order.getPaymentMethod()
	 */
	public Set<PaymentMethod> p02_getUsedPaymentMethods(Customer customer) {
		return customer.getOrders().stream()
//			.map(o -> o.getPaymentMethod())
			.map(Order::getPaymentMethod)
			.collect(toSet())
				;
	}
	
	/**
	 * When did the customer created orders ?
	 * Note: Order.getCreationDate()
	 */
	public SortedSet<LocalDate> p03_getOrderDatesAscending(Customer customer) {
		return customer.getOrders().stream()
			.map(Order::getCreationDate)
//			.collect(Collectors.toCollection(() -> new TreeSet<>()));
			.collect(Collectors.toCollection(TreeSet::new));
	}
	
	
	/**
	 * @return a map order.id -> order
	 */
	public Map<Long, Order> p04_mapOrdersById(Customer customer) {
		return customer.getOrders().stream()
				.collect(toMap(Order::getId, o -> o));
	}
	
	/** 
	 * Orders grouped by Order.paymentMethod
	 */
	public Map<PaymentMethod, List<Order>> p05_getProductsByPaymentMethod(Customer customer) {
		return customer.getOrders().stream()
			.collect(Collectors.groupingBy(Order::getPaymentMethod)); // SQL: GROUP BY
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
		
//		List<OrderLine> allLines = new ArrayList<>();
//
//		for (Order order : customer.getOrders()) {
//			allLines.addAll(order.getOrderLines());
//		}

		// ai un stream de parinti, cum obtii un stream din copiilor joinati intre ei?
		List<OrderLine> allLines = customer.getOrders().stream()
			.flatMap(o -> o.getOrderLines().stream()).collect(toList());

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
			.sorted(Comparator.comparing(Product::getName))
			.collect(toList());
	}
	
	
	/**
	 * The names of all the products bought by Customer,
	 * sorted and then concatenated by ",".
	 * Example: "Armchair,Chair,Table".
	 * Hint: Reuse the previous function.
	 */
	public String p08_getProductsJoined(Customer customer) {
		return p07_getAllOrderedProducts(customer).stream()
			.map(Product::getName)
			.collect(joining(","));
	}
	
	/**
	 * Sum of all Order.getTotalPrice(), truncated to Long.
	 */
	public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
		// TODO +, longValue(), reduce()
//		BinaryOperator<BigDecimal> bigDecimalBinaryOperator = (prevSumBD, currentPrice) -> prevSumBD.add(currentPrice);
//		BinaryOperator<BigDecimal> bigDecimalBinaryOperator = BigDecimal::add;
//		BiFunction<BigDecimal, BigDecimal, BigDecimal> bigDecimalBinaryOperatoF = BigDecimal::add;
		return customer.getOrders().stream()
			.map(Order::getTotalPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add)
			.longValue(); // right-fold (FP)
//			.mapToLong(BigDecimal::longValue)
//			.sum();
	}

	public Long p09_getApproximateTotalOrdersPrice_PRAF(Customer customer) {
		long sum = 0L;
		customer.getOrders().stream()
			.map(Order::getTotalPrice)
//			.forEach(repo::save)
//			.forEach(System.out::println);
			.forEach(price -> {
//				sum += price.longValue(); /// NU COMPILEAZA
				System.out.println("Cand s-o facut lambda, suma era " + sum);
			});

		return sum;
	}
	
	// cod absurd:
//
//	public Supplier<Integer> imposibil() {
//		int i = 0;
//		return ()->i++;
//	}
//
//	public void method() {
//		Supplier<Integer> supplier = imposibil();
//
//		System.out.println(supplier.get());
//		System.out.println(supplier.get());
//	}
}
