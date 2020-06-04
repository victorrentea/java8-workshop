package victor.training.java8.stream.order;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.Callable;
import java.util.function.*;
import java.util.stream.Stream;

import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;

@Service
class OrderMapper {
	// @Autowired alte dep;
	public OrderDto toDto(Order order) {
		OrderDto dto = new OrderDto();
		dto.totalPrice = order.getTotalPrice();
		dto.creationDate = order.getCreationDate();
		return dto;
	}
}

public class TransformStreams {
	@Autowired
	private OrderMapper orderMapper = new OrderMapper();
	/**
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */
	public List<OrderDto> p01_toDtos(List<Order> orders) {

		Function<String, Integer> f1 = String::length; // f(string):int
		Supplier<Integer> f2 = "abc"::length; // f():int
		Function<String, Integer> f3 = Integer::parseInt; // f(String):Integer
		Function<Order, Long> f4 = Order::getId; // f(Order):Long
		Order order1 = new Order();
		Supplier<Long> f5 = order1::getId;// f():Long    () -> order1.getId();
		Callable<Long> f6 = order1::getId;// f():Long

//		/*Function<Order, OrderDto> f7 =*/ OrderMapper::toDto; // (Order) -> OrderDto <--- asta daca toDto este statica

		BiFunction<OrderMapper, Order, OrderDto> f7 = OrderMapper::toDto; // (OrderMapper, Order) -> OrderDto -->
		Function<Order, OrderDto> f8 = orderMapper::toDto; // (Order) -> OrderDto

		Function<List<Order>, List<OrderDto>> f9 = this::p01_toDtos; // (List<Order>) -> List<OrderDto>
		BiFunction<TransformStreams, List<Order>, List<OrderDto>>  f10 = TransformStreams::p01_toDtos; //

		BiFunction<Integer, Integer, Integer> f11 = Math::max; //
		Function<Order, BigDecimal> f12 = Order::getTotalPrice; // (Order) -> BigDecimal
		Supplier<BigDecimal> f13 = order1::getTotalPrice; // () -> BigDecimal
		Consumer<BigDecimal> f14 = order1::setTotalPrice; // (BigDecimal) -> nimic
		BiConsumer<Order, BigDecimal> f15 = Order::setTotalPrice; // (Order,BigDecimal) -> nimic

		BigDecimal zero = BigDecimal.ZERO;
		BigDecimal ten = BigDecimal.TEN;
		BigDecimal unshpe = zero.add(ten);
		BiFunction<BigDecimal, BigDecimal, BigDecimal> f16 = BigDecimal::add; // (BD,BD) -> BD
		Function<BigDecimal, BigDecimal> f17 = ten::add; //



		return orders.stream().map(f8).collect(toList());
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

		// TODO check the number of lines is >= 2

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
