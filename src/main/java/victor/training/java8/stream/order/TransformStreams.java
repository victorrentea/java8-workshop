package victor.training.java8.stream.order;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;

public class TransformStreams {

	
	// @Inject === @Autowired
	private OrderMapper mapper;
	/**
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */
	public List<OrderDto> p01_toDtos(List<Order> orders) {
		
		return orders.stream() // Stream<Order>
//				.map(this::toDto) // Stream<OrderDto>
//				.map(mapper::toDto) // Stream<OrderDto>
				.map(order -> new OrderDto(order)) // Stream<OrderDto>
				.collect(toList());
	}
	
	{

		//Integer.parseInt(s) --> f(String):int
		Function<String, Integer> parseInt = Integer::parseInt;
		
		// **** Metode de instanta ***** referit de pe clasa.
		// toDto --> toDto(TransformStream,Order):OrderDto
		BiFunction<TransformStreams, Order, OrderDto> refLuataDePeClasa = TransformStreams::toDto;
		
		// isActive(Order):Boolean
		Function<Order, Boolean> isAct = Order::isActive;
		Predicate<Order> isActPred = Order::isActive;
		Function<Order, BigDecimal> totalPrice = Order::getTotalPrice;
		Function<Order, List<OrderLine>> getLines = Order::getOrderLines;
		List<OrderLine> list = getLines.apply(new Order());
		
		// **** Metode de instanta ***** referit de pe o instanta existenta.
		Function<Order, OrderDto> refLuataDePeClasa2 = this::toDto;
		Order orderInstance = new Order();
		Supplier<Boolean> unBoolDinNimic = orderInstance::isActive; // f():boolean
		Supplier<List<OrderLine>> getLinesDinNimic = orderInstance::getOrderLines;
		
		Function<Order,OrderDto> mapRef = mapper::toDto; //  f(Order): OrderDto
		BiFunction<OrderMapper, Order, OrderDto> mapRef2 =  OrderMapper::toDto; // f(OrderMapper,Order): OrderDto    sau
		
		PrintStream ps = System.out;
		Consumer<String> println = ps::println; // f(String):void
		System.out.println("a");
		Consumer<Long> printlnl = ps::println; // f(String):void
		System.out.println(1L);
		
		BiConsumer<PrintStream, String> printFaraInstanta = PrintStream::println; // f(PrintStream,String):void
		printFaraInstanta.accept(System.out, "de printat");
		
		
		Date acum = new Date();
		Supplier<Date> maUitLaCeas = Date::new;// f():Date
		
		Date in1970 = new Date(0l);
		Function<Long,Date> aoleu = Date::new;// f(Long):Date
		
		
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

class OrderMapper {
	public OrderDto toDto(Order order) {
		OrderDto dto = new OrderDto();
		dto.totalPrice = order.getTotalPrice(); 
		dto.creationDate = order.getCreationDate();
		return dto;
	}
}








