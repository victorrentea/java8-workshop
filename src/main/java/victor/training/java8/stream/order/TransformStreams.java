package victor.training.java8.stream.order;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import sun.reflect.generics.tree.Tree;
import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;


class OrderMapper {
	public OrderDto toDto(Order order) {
		return new OrderDto(order);
	}
}

public class TransformStreams {

	public void methodReferencePlay() {

		// f:N->R, f(x) = sqrt(x)  domeniu = Naturale, Codomeniu = Reale

		int i = Integer.parseInt("1"); // dom=String , codom=int : vom nota -  f(String):int
		Function<String, Integer> f1 = Integer::parseInt;

		LocalDateTime dateTime = LocalDateTime.parse("2020-10-01");// f(String): LocalDateTime
		Function<String, LocalDateTime> parseF = LocalDateTime::parse;

		int aaa = Objects.hashCode("aaa");
		Function<Object, Integer> f2 = Objects::hashCode;

		//// pana aici, simplu: referirea fct statice
		// mai jos: referirea functiilor de instanta (ne stattice ) dintr-o instanta existenta

		Order order1 = new Order();

		Long id = order1.getId(); // order.getId(): Long
//		Supplier<Long> f3 = new Supplier<Long>() {
//			@Override
//			public Long get() {
//				return order.getId();
//			}
//		};
		Supplier<Long> f3 = order1::getId; // f(): Long
		BigDecimal bd = BigDecimal.ONE;
		BigDecimal two = bd.add(bd); // bd.add(BD): BD
		Function<BigDecimal, BigDecimal> f4 = bd::add;

		int i1 = bd.compareTo(two);
		Function<BigDecimal, Integer> f5 =  bd::compareTo; //f(BD):Integer

//		OrderDto orderDto = this.toDto(order1);
////		Function<Order, OrderDto> f6 = this::toDto; // f(Order):OrderDto

		order1.setPaymentMethod(PaymentMethod.CARD);
		Consumer<PaymentMethod> ft = order1::setPaymentMethod; // f(PaymentMethod):void

		// un pic mai greu pana aici. mai jos, horror

		// referirea metodelor de instanta dar fara sa ai vreo instanta in mana.

		Function<Order, Long> f7 = Order::getId; // f(Order):Long
		Long apply = f7.apply(order1); // de ex, cum o chemi


//		BiFunction<TransformStreams, Order, OrderDto> f8_DoamneMaIarta = TransformStreams::toDto; // f(TransformStreams ("this"), Order):OrderDto
//		OrderDto dto = f8_DoamneMaIarta.apply(this, order1);

		BiFunction<BigDecimal, BigDecimal, Integer> f9_haiCaMerge = BigDecimal::compareTo; // f(BigDecimal (e "this"-ul), BigDecimal ("param")):int
//		Integer compareResult = f9_haiCaMerge.apply(bd1, bd2);

		BiConsumer<Order, PaymentMethod> f10 = Order::setPaymentMethod; // f(Order, PaymentMethod):void
		/// ----------- SFARSIT

		Date date = new Date(); // "new" in cazul asta ce fel de fct este:   new():Date
		Supplier<Date> supDate = Date::new; // soc si groaza prima data cand vezi

		Date date1 = new Date(122132); // "new" in cazul asta ce fel de fct este:   new(Long):Date -->

		Function<Long, Date> overloadCuFourDots = Date::new;
		LongFunction<Date> overloadCuFourDotsLong = Date::new; // refera aceelasi constructor.
		// plangi caci cele doua arata la fel, chiar daca indica spre overloaduri diferite ale constructori


	}

	@Autowired // nu ne merge ca rulam fara
	private OrderMapper orderMapper = new OrderMapper();

	/**
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */
	public List<OrderDto> p01_toDtos(List<Order> orders) {
		BiFunction<OrderMapper, Order, OrderDto> b = OrderMapper::toDto;
		Function<Order, OrderDto> f1 = orderMapper::toDto;
		Function<Order, OrderDto> f2 = OrderDto::new;
		return orders.stream()
//			.map(o -> toDto(o))
//			.map(this::toDto)
//			.map(f6)
//			.map(orderMapper::toDto)
			.map(OrderDto::new)
			.collect(toList()); // o operatie pe stream pana in collect merge lasata oneliner
	}


	/**
	 * Note: Order.getPaymentMethod()
	 */
	public Set<PaymentMethod> p02_getUsedPaymentMethods(Customer customer) {
		Map<PaymentMethod, Map<LocalDate, List<Order>>> horror = customer.getOrders().stream().collect(groupingBy(Order::getPaymentMethod, groupingBy(Order::getCreationDate)));
		return customer.getOrders().stream() // Stream<Order>
			.map(Order::getPaymentMethod)
			.collect(toSet());
	}
	
	/**
	 * When did the customer created orders ?
	 * Note: Order.getCreationDate()
	 */
	public SortedSet<LocalDate> p03_getOrderDatesAscending(Customer customer) {
		return customer.getOrders().stream()
			//.sorted(comparing(Order::getCreationDate)) // Stream<Order>, da in ordine -- inutila caci colectia finala le sorteaza oricum
			.map(Order::getCreationDate)
			.collect(toCollection(TreeSet::new));
	}
	
	
	/**
	 * @return a map order.id -> order
	 */
	public Map<Long, Order> p04_mapOrdersById(Customer customer) {
//		Map<Long, Order> map = new HashMap<>();
//		map.put(1L, null); // MERGE
//		map.put(null, null); // MERGE

		BiFunction<Integer, Integer, Integer> f = (a,b) -> a+b;
		BinaryOperator<Integer> op = (a,b) -> a+b;

		BiFunction<BigDecimal, BigDecimal, BigDecimal> add = BigDecimal::add;

		customer.getOrders().stream()
//			.collect(toMap(Order::getId, Order::getTotalPrice, (totalInMap, newItemPrice) -> totalInMap.add(newItemPrice))); // ignora una dintre chei. Inghite o intrare
//			.collect(toMap(Order::getId, Order::getTotalPrice, (newItemPrice, totalInMap) -> totalInMap.add(newItemPrice))); // ordinea param de lambda conteaza daca vrei sa transf in ::
			.collect(toMap(Order::getId, Order::getTotalPrice, BigDecimal::add)); // ignora una dintre chei. Inghite o intrare


		return customer.getOrders().stream()
//			.collect(toMap(Order::getId, order -> null)); // crapa cu NPE > DECE ? E un Bug in JDK
//			.collect(toMap(order -> null, order -> order)); // stupoare: asta merge @!!!
//			.collect(toMap(Order::getId, order -> order); // crapa daca exista key duplicate
			.collect(toMap(Order::getId, order -> order, (valueAlreadyInMap, newValue) -> valueAlreadyInMap)); // ignora una dintre chei. Inghite o intrare
	}
	
	/** 
	 * Orders grouped by Order.paymentMethod
	 */
	public Map<PaymentMethod, List<Order>> p05_getProductsByPaymentMethod(Customer customer) {

//		Map<PaymentMethod, List<Order>> map = new HashMap<>();
//		for (Order order : customer.getOrders()) {
//			if (!map.containsKey(order.getPaymentMethod())) {
//				map.put(order.getPaymentMethod(), new ArrayList<>());
//			}
//			map.get(order.getPaymentMethod()).add(order);
//		}


		return customer.getOrders().stream().collect(groupingBy(Order::getPaymentMethod));
	}
	public Map<PaymentMethod, List<LocalDate>> p05_getProductsCreateDateByPaymentMethod(Customer customer) {
		return customer.getOrders().stream()
			.collect(groupingBy(Order::getPaymentMethod, mapping(Order::getCreationDate, toList())));
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
		
	}
	
	/**
	 * All the unique products bought by the customer, 
	 * sorted by Product.name.
	 */
	public List<Product> p07_getAllOrderedProducts(Customer customer) {
		return customer.getOrders().stream() // Stream<Order>
			.flatMap(order -> order.getOrderLines().stream()) // Stream<OrderLine>
			.map(OrderLine::getProduct) // Stream<Product>
			.distinct()
			.sorted(comparing(Product::getName))
			.collect(toList());
	}
	
	
	/**
	 * The names of all the products bought by Customer,
	 * sorted and then concatenated by ",".
	 * Example: "Armchair,Chair,Table".
	 * Hint: Reuse the previous function.
	 */
	public String p08_getProductsJoined(Customer customer) {
		return p07_getAllOrderedProducts(customer).stream() // Stream<Product>
			.map(Product::getName) //Stream<String>
			.collect(joining(","));
	}
	
	/**
	 * Sum of all Order.getTotalPrice(), truncated to Long.
	 */
//	public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
//		return (long) customer.getOrders().stream()
//			.mapToDouble(order -> order.getTotalPrice().doubleValue()) //DoubleStream ,o alta interfata // numeric streams
////			.boxed() // Stream<Double>
////			.mapToDouble(d -> d)
//			.sum();
//	}

	// daca vrei sa nu ai erori de aprox, sumeaza direct BigDecimal-uri
	public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
		BigDecimal bd1 = BigDecimal.ONE;
		BigDecimal bd2 = BigDecimal.ONE;

		BigDecimal two = bd1.add(bd2);

		return customer.getOrders().stream()
			.map(Order::getTotalPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add)
			.longValue();

	}


	public Long p09_getApproximateTotalOrdersPriceCuPlus(Customer customer) {
		long suma = 0;
//		customer.getOrders().stream()
//			.map(Order::getTotalPrice)
//			.forEach(price -> {
//				suma += price.longValue();
//			});
		return suma;
	}


	static Supplier<Integer> createMagicSupplier() {
		int x = 0;
		return () -> x;
	}

	public static void main(String[] args) {
		Supplier<Integer> supplier = createMagicSupplier();

		System.out.println(supplier.get());
		System.out.println(supplier.get());

		throw new RuntimeException();
	}

}
