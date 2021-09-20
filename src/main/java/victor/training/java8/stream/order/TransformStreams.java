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
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.Data;
import lombok.RequiredArgsConstructor;
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
class Wine{}
class OrderMapper {
	public OrderDto toDto(Order order) {
		OrderDto dto = new OrderDto();
		dto.totalPrice = order.getTotalPrice();
		dto.creationDate = order.getCreationDate();
		return dto;
	}
}
@RequiredArgsConstructor
public class TransformStreams {
	private final OrderMapper orderMapper;

	/**
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */
	public List<OrderDto> p01_toDtos(List<Order> orders) {
		
		List<OrderDto> dtos = new ArrayList<>();
//		for (Order order : orders) {
//			dtos.add(toDto(order));
//		}
		Function<Order, OrderDto> f = orderMapper::toDto;


		Function<String, Integer> f1 = s-> Integer.parseInt(s);
		Function<String, Integer> f1b = Integer::parseInt;

		Function<List<?>, Integer> f3 = list -> list.size();
		Function<List<?>, Integer> f3b = List::size;
		Integer apply = f3.apply(orders);


		// Function, Consumer, Supplier, Predicate,
		Supplier<Integer> f4 = () -> orders.size();
		Integer integer = f4.get();

		Consumer<Wine> beutor =  wine -> bea(wine) ;
		Consumer<Wine> beutorb =  this::bea ;
		beutor.accept(new Wine());

		Person person = new Person();

		Supplier<String> f5 = () -> person.getName();
		Supplier<String> f5b = person::getName;

		Function<Person, String> f6 = pp -> pp.getName();
		Function<Person, String> f6b = Person::getName;
		String apply1 = f6b.apply(person);

		Date date = new Date();
		Supplier<Date> f7 = () -> new Date();
		Supplier<Date> f7b = Date::new;
		Function<Long, Date> f7c = Date::new;



		// target typing : pt orice lambda sau ::, compilatorul trebuie sa stie la ce TIP atribui acea expresie
//		Object o1 = Date::new;
//		Object o2 = () -> new Date();

		BiFunction<OrderMapper, Order, OrderDto> f8 =  OrderMapper :: toDto;// f( OrderMapper, Order  ):OrderDto


		// cand referi o metoda de instanta fara sa referi vreo existenta existenta( eg Person::getName) atunci
		// primul param din functia extrasa va trebui sa fie obiectul pe care invoci acea metoda de instanta

		dtos = orders.stream().map(orderMapper::toDto).collect(toList());
//		dtos = orders.stream().map(this::toDto).collect(toList());
//		dtos = orders.stream().map(order -> toDto(order)).collect(toList());
		return dtos;
		
	}
	void bea(Wine wine) {
		System.out.println("Gal gal!");
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
