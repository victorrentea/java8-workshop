package victor.training.java8.stream.order;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
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
			.filter(order -> order.getId().equals(orderId))
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
	 * @return
	 */
	public Optional<Order> p5_getMaxPriceOrder(Customer customer) {
//		Comparator<Order> comparator = (o1, o2) -> o1.getTotalPrice().compareTo(o2.getTotalPrice());
//		Comparator<Order> comparator = Comparator.comparing(order -> order.getTotalPrice());

		//Optional<Long> age = Optional.ofNullable(customer.getAge());

		return customer.getOrders().stream().max(comparing(Order::getTotalPrice));
	}

	/**
	 * last 3 Orders sorted descending by creationDate
	 */
	public List<Order> p6_getLast3Orders(Customer customer) {
		return customer.getOrders().stream()

			// stateful operators; collect-and-forward
			.sorted(comparing(Order::getCreationDate).reversed())

			// stateful operators; collect-and-forward
//			.distinct()

			// Suppose you have 1 million elements and if reverse is invoked wont it take a performance hit.
			//How does reverse work when streams are working lazily ?
			.limit(3)
			.collect(toList());
	}
	
	
}
