package victor.training.java8.stream.order;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.Order.Status;
import victor.training.java8.stream.order.entity.OrderLine;

public class SearchStreams {
	
	/**
	 * FIRST OF ALL: Add the following "Favourite" static imports:
	 * Eclipse: Window > Preferences > type "Favo" > Favorites >
	 * 					> New Type > Browse > and type the class name for:
		java.util.stream.Collectors
	 */
	
	/**
	 * - shorten/clean it up
	 */
	public List<Order> p1_getActiveOrders(Customer customer) {
		return customer.getOrders().stream()
			.filter(Order::isActive)
			.collect(toList());
	}
	
	/**
	 * @return the Order in the list with the given id  
	 * - ...Any or ...First ?
	 * - what do you do when you don't find it ? null/throw/Optional ?
	 */
	public Optional<Order> p2_getOrderById(List<Order> orders, long orderId) {
		return orders.stream()
			.filter(order -> order.getId() == orderId)
			.findFirst();
	}
	
	/**
	 * @return true if customer has at least one order with status ACTIVE
	 */
	public boolean p3_hasActiveOrders(Customer customer) {
		return customer.getOrders().stream().anyMatch(Order::isActive);
	}

	/**
	 * An Order can be returned if it doesn't contain 
	 * any OrderLine with isSpecialOffer()==true
	 */
	public boolean p4_canBeReturned(Order order) {
		return order.getOrderLines().stream().noneMatch(OrderLine::isSpecialOffer);
	}
	
	// ---------- select the best ------------
	
	/**
	 * The Order with maximum getTotalPrice. 
	 * i.e. the most expensive Order, or null if no Orders
	 * - Challenge: return an Optional<creationDate>
	 */
	public Order p5_getMaxPriceOrder(Customer customer) {
//		Comparator<Order> comparator = (o1, o2) -> o1.getTotalPrice().compareTo(o2.getTotalPrice());
		return customer.getOrders().stream()
			.max(comparing(Order::getTotalPrice))
			.orElse(null);
	}
	
	/**
		// ORDER BY CREATION_DATE DESC LIMIT 3
	 * last 3 Orders sorted descending by creationDate
	 */
	public List<Order> p6_getLast3Orders(Customer customer) {
		Comparator<Order> comparing = comparing(Order::getCreationDate);
		return customer.getOrders()
			.stream()
			.sorted(comparing.reversed())
			.limit(3)
			.collect(toList());
	}
	
	
}
