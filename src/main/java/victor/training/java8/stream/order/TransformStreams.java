package victor.training.java8.stream.order;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import victor.training.java8.stream.order.dto.OrderBdt;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;

import static java.util.stream.Collectors.*;


class OrderMapper {
}

public class TransformStreams {

	OrderMapper mapper = new OrderMapper();
	/**
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */
	public List<OrderBdt> p01_toDtos(List<Order> orders) {

//		int i = Integer.parseInt("s");
		Function<String, Integer> parse = Integer::parseInt;
		LocalDate localDate = LocalDate.parse("2019-10-10");
		Function<String, LocalDate> x = LocalDate::parse;

		Function<Order, BigDecimal> c = Order::getTotalPrice;
		Function<Customer, List<Order>> getOrders = Customer::getOrders;
		Function<Order, String> toStr = Order::toString;
		Supplier<Long> millis = System::currentTimeMillis; // prima minune
		BiFunction<String, DateTimeFormatter, LocalDate> parseul2 = LocalDate::parse;

//		System.exit(2);
		Consumer<Integer> chiuveta = System::exit;

		//end of part 1 ------------

		Order o = new Order();
		Supplier<String> toStringPeInstanta = o::toString;
		Supplier<Long> orderId = o::getId;
		Supplier<List<OrderLine>> orderLinesId = o::getOrderLines;

		/// inapoi pe drum

		new Date(); //f():Date
		new Date(12L); //f(Long):date

		Supplier<Date> dateAcum = Date::new;
		Function<Long,Date> dateCandva = Date::new;

		Function<Order, OrderBdt> toBDTDePeInstanta = order2 -> new OrderBdt(order2);
		BiFunction<OrderMapper, Order, OrderBdt> toBDTDePeClasa = (orderMapper, order1) -> new OrderBdt(order1);

		return orders.stream()
				.map(OrderBdt::new)
				.collect(Collectors.toList());
	}



	/**
	 * Note: Order.getPaymentMethod()
	 */
	public Set<PaymentMethod> p02_getUsedPaymentMethods(Customer customer) {
		return customer.getOrders().stream()
				.map(Order::getPaymentMethod)
				.collect(Collectors.toSet());
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
		return customer.getOrders()
				.stream() // stream<Order>
				.collect(toMap(Order::getId, o -> o));
	}
	
	/**
	 * GROUP BY
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

		// SELECT SUM(OL.COUNT) ... GROUP BY OL.PRODUCT_ID
		return allLines.stream()
				.collect(groupingBy(OrderLine::getProduct, summingLong(OrderLine::getCount)));
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
		return null; 
	}
	
	// ----------- IO ---------------
	
	/**
	 * - Read lines from file as Strings. 
	 * - Where do you close the opened file?
	 * - Parse those to OrderLine using the function bellow
	 * - Validate the created OrderLine. Throw ? :S
	 */
	public List<OrderLine> p10_readOrderFromFile(File file) throws IOException {
		
		Stream<String> lines = null; // ??
		//return lines
		//.map(line -> line.split(";")) // Stream<String[]>
		//.filter(cell -> "LINE".equals(cell[0]))
		//.map(this::parseOrderLine) // Stream<OrderLine>
		//.peek(this::validateOrderLine)
		//.collect(toList());
		return null;
		
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
