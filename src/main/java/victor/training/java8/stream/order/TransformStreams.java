package victor.training.java8.stream.order;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;

@Data
class Person {
	private String name;
	private int age;
}
@Component
class OrderMapper {
	public OrderDto toDto(Order order) {
		OrderDto dto = new OrderDto();
		dto.totalPrice = order.getTotalPrice();
		dto.creationDate = order.getCreationDate();
		return dto;
	}
}

@Service
@RequiredArgsConstructor
public class TransformStreams {
	private OrderMapper orderMapper = new OrderMapper();

	/**
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */
	public List<OrderDto> p01_toDtos(List<Order> orders) {

		// caz1: referi metoda de instanta prin numele Clasei . tipic in .filter
		BiFunction<OrderMapper, Order, OrderDto> f2 = OrderMapper::toDto; // metoda de instanta referita prin numele clasei (fara o instanta disponibila)
		OrderDto dto = f2.apply(orderMapper, new Order());

		// caz2: referi metoda de instanta dintr-o instanta pe care o ai deja: tipic in .map
		Function<Order, OrderDto> f = orderMapper::toDto;
		Function<Order, OrderDto> ff = this::toDto;
		OrderDto dto2 = f.apply(new Order());

		// caz3: referi metoda statica
		Function<String,Integer> f3 = Integer::parseInt;
		Function<String, LocalDateTime> f4 = LocalDateTime::parse;
		Function<CharSequence , LocalDateTime> f5 = LocalDateTime::parse;

		// caz 4: referi consturctor
		Supplier<Date> wth = Date::new;
		System.out.println(wth);

		Supplier<Date> wth2 = new Supplier<Date>() {
			@Override
			public Date get() {
				return new Date();
			}
		};
		System.out.println(wth2);

		List<OrderDto> dtos = orders.stream()
			.map(this::toDto)
//			.map(mapper::toDto)
			.collect(toList());
		return dtos;
	}

	public OrderDto toDto(Order order) {
		OrderDto dto = new OrderDto();
		dto.totalPrice = order.getTotalPrice();
		dto.creationDate = order.getCreationDate();
		return dto;
	}


	/**
	 * Note: Order.getPaymentMethod()
	 */
	public Set<PaymentMethod> p02_getUsedPaymentMethods(Customer customer) {
		return customer.getOrders().stream()
			.map(Order::getPaymentMethod)
			.collect(toSet());
	}
	
	/**
	 * When did the customer created orders ?
	 * Note: Order.getCreationDate()
	 */
	public SortedSet<LocalDate> p03_getOrderDatesAscending(Customer customer) {
		return customer.getOrders().stream()
			.map(Order::getCreationDate)
			.collect(toCollection(TreeSet::new));
	}
	
	
	/**
	 * @return a map order.id -> order
	 */
	public Map<Long, Order> p04_mapOrdersById(Customer customer) {
		return customer.getOrders().stream()
			.collect(toMap(Order::getId, identity()));
	}
	
	/** 
	 * Orders grouped by Order.paymentMethod
	 */
	public Map<PaymentMethod, List<Order>> p05_getProductsByPaymentMethod(Customer customer) {
		return customer.getOrders().stream()
			.collect(groupingBy(Order::getPaymentMethod));
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
		return customer.getOrders().stream()
			.flatMap(order -> order.getOrderLines().stream())
			.collect(groupingBy(OrderLine::getProduct, summingLong(OrderLine::getCount)));
		// SUM(LINE.COUNT)    ...  GROUPING BY LINE.PRODUCT_ID
		
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
		return customer.getOrders().stream()
			.mapToLong(order-> order.getTotalPrice().longValue()).sum();
//			.map(Order::getTotalPrice).collect(summingLong(BigDecimal::intValue));
//			.map(Order::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add).longValue();

		// NU:
//		AtomicLong sum = new AtomicLong();
//		customer.getOrders().forEach(order -> {
//			sum.addAndGet(order.getTotalPrice().longValue());
//		});
//		return sum.get();
	}
//	// joc absurd
//	public static Supplier<Integer> method() {
//		long acc = 0; // stack
//		return () -> {
//			return ++ acc; // in jS merge. limbaj js a fost dezvoltat in 10 zile lucratoare.
//		};
//	}
//
//	public static void main(String[] args) {
//		Supplier<Integer> s = method();
//		s.get();
//		s.get();
//		s.get();
//	}
}
