package victor.training.java8.stream.order;

import static java.math.BigDecimal.ZERO;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;

public class TransformStreams {

	// @Autowired ne prefacem
	AltaClasa altaClasa = new AltaClasa();
	/**
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */
	public List<OrderDto> p01_toDtos(List<Order> orders) {
		BiFunction<AltaClasa, Order, OrderDto> wtf = AltaClasa::toDto; // f(AltaClasa, Order):OrderDto
		Function<Order, OrderDto> given = altaClasa::toDto;
		return orders.stream()
//				.map(altaClasa::toDto)// sau..
				.map(OrderDto::new)
				.collect(toList());
	}
	
	

	public static class AltaClasa {
		// @Autowired private UnRepo repo;
		public OrderDto toDto(Order order) {
			OrderDto dto = new OrderDto();
			//merge si cotrobaie prin baza, foloseste un Repo injectat
			dto.totalPrice = order.getTotalPrice(); 
			dto.creationDate = order.getCreationDate();
			return dto;
		}
	}
	
	/**
	 * Note: Order.getPaymentMethod()
	 */
	public Set<PaymentMethod> p02_getUsedPaymentMethods(Customer customer) {
		return customer.getOrders().stream()
				.map(Order::getPaymentMethod) // f(Order):PaymentMethod
				.collect(toSet()); 
	}
	
	/**
	 * When did the customer created orders ?
	 * Note: Order.getCreationDate()
	 */
	public SortedSet<LocalDate> p03_getOrderDatesAscending(Customer customer) {
		return customer.getOrders().stream()
				.map(Order::getCreationDate)
				.collect(Collectors.toCollection(TreeSet::new)); 
	}
	
	
	/**
	 * @return a map order.id -> order
	 */
	public Map<Long, Order> p04_mapOrdersById(Customer customer) {
		return customer.getOrders().stream()
				.collect(Collectors.toMap(Order::getId, identity()));  
	}
	
	/** 
	 * Orders grouped by Order.paymentMethod
	 * SQL : GROUP BY
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
		
		return customer.getOrders().stream() // Stream<Order>
			.flatMap(order -> order.getOrderLines().stream()) // Stream<OrderLine>
			.collect(groupingBy(OrderLine::getProduct, // imparte caprele in multimi (caprarii)
					// (capraria este o multime de capre care au acelasi payment method)
					Collectors.summingLong( // cum colectez caprele din fiecare multime
							OrderLine::getCount // extrag din capra un Long (capra e OrderLine)
							)));
		
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
				.sorted()
				.collect(joining(",")); 
	}
	
	/**
	 * Sum of all Order.getTotalPrice(), truncated to Long.
	 */
	public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
//		return customer.getOrders().stream()
//				.map(Order::getTotalPrice)
//				.collect(reducing(ZERO, BigDecimal::add))
//				.longValue(); 
		return customer.getOrders().stream()
				.mapToLong(order -> order.getTotalPrice().longValue())
				.sum(); 
	}
	
	// ----------- IO ---------------
	
	/**
	 * - Read lines from file as Strings. 
	 * - Where do you close the opened file?
	 * - Parse those to OrderLine using the function bellow
	 * - Validate the created OrderLine. Throw ? :S
	 */
	public List<OrderLine> p10_readOrderFromFile(File file) throws IOException {
		
		try (Stream<String> lines = Files.lines(file.toPath())) {
			return lines
				.map(line -> line.split(";")) // Stream<String[]>
				.filter(cell -> "LINE".equals(cell[0]))
				.map(this::parseOrderLine) // Stream<OrderLine>
				.peek(this::validateOrderLine)
				.collect(toList());
		}
		
	}
	
	private OrderLine parseOrderLine(String[] cells) {
		return new OrderLine(new Product(cells[1]), Integer.parseInt(cells[2]));
	}
	
	private void validateOrderLine(OrderLine orderLine) {
		if (orderLine.getCount() < 0) {
			throw new IllegalArgumentException("Negative items");			
		}
	}
	
	
	// TODO print cannonical paths of all files in current directory
	// use Unchecked... stuff
}
