package victor.training.java.stream.order;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.aspectj.weaver.ast.Or;
import victor.training.java.stream.order.entity.Customer;
import victor.training.java.stream.order.entity.Order;
import victor.training.java.stream.order.entity.OrderLine;

public class SearchStreams {
	/**
	 * - shorten/clean it up
	 */
	public List<Order> p1_getActiveOrders(Customer customer) {

		return customer.getOrders().stream()
				.filter(Order::isActive)
				.collect(toList());
	}
	
	/**
	 * @return the Order in the list with the given id, or null if not found
	 * - findAny or findFirst ?
	 */
	public Order p2_getOrderById(List<Order> orders, long orderId) {
		return orders.stream()
						.filter(o-> o.getId()==orderId)
						.findFirst()
						.orElse(null);
	}
	
	/**
	 * @return true if customer has at least one order with status ACTIVE
	 */
	public boolean p3_hasActiveOrders(Customer customer) {
		return customer.getOrders().stream()
						.anyMatch(Order::isActive); // short circuiting
//						.collect(toList()).size() >= 1;
//						.count() >= 1;

	}

	/**
	 * An Order can be returned if it doesn't contain 
	 * any OrderLine with isSpecialOffer()==true
	 */
	public boolean p4_canBeReturned(Order order) {
		return order.getOrderLines().stream().noneMatch(OrderLine::isSpecialOffer);
	}
	
	/**
	 * Return the Order with maximum getTotalPrice.
	 * i.e. the most expensive Order, or null if no Orders
	 * - Challenge: return an Optional<creationDate>
	 */
	public Order p5_getMaxPriceOrder(Customer customer) {

		// Target Typing
		// nu compileaza pt ca javac nu stie ce tip sa construiasca din lamba da => lambdele sunt syntactic sugar
//		Object o = (Order o1, Order o2) ->{

//		Object o = (Comparator<Order>)(Order o1, Order o2) ->{ // ok

		// merge!!
//		BiFunction<Order, Order, Integer > bff=  (Order o1, Order o2) ->{return o1.getTotalPrice().compareTo(o2.getTotalPrice());};

		// merge
		Object o = (Comparator<Order>)(Order o1, Order o2) ->{
			return o1.getTotalPrice().compareTo(o2.getTotalPrice());

		};
//						.max(( o1,  o2) -> o1.getTotalPrice().compareTo(o2.getTotalPrice())		) // sau...

		Function<Order, BigDecimal> f = Order::getTotalPrice;


		return customer.getOrders().stream()
						.max(comparing(Order::getTotalPrice))
//						.max(comparing(Order::getOrderLines, comparing(List::size)))
						.orElse(null);
	}
	
	/**
	 * last 3 Orders sorted descending by creationDate
	 */
	public List<Order> p6_getLatestOrders(Customer customer) {
		return customer.getOrders().stream()
//						.sorted(Collections.reverseOrder(comparing(Order::getCreationDate)))
						.sorted(comparing(Order::getCreationDate).reversed()) // default methods !
						.limit(3)
						.collect(toList());

	}
	
	
}
