package victor.training.java8.stream.order;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;

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
				.filter(order -> order.getStatus() == Order.Status.ACTIVE)
				.collect(toList());
	}
	
	/**
	 * @return the Order in the list with the given id  
	 * - ...Any or ...First ?
	 * - what do you do when you don't find it ? null/throw/Optional ?
	 */
	public Order p2_getOrderById(List<Order> orders, long orderId) {
		Optional<Order> orderOpt = orders.stream()
			.filter(order -> order.getId() == orderId)
			.findFirst();

		if (orderOpt.isPresent()) {
			System.out.println("Am gasit order " + orderOpt.get());
			System.out.println("XXxx");
		}
		orderOpt.ifPresent(o -> System.out.println("Am gasit order " + o));
		orderOpt.ifPresent(o -> metodaCuNumeCuviincios(o));


		return orders.stream()
			.filter(order -> order.getId() == orderId)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Nu am gasit order cu id " + orderId));
		// ar fi putut sa arunce si exceptie, dar ar fi fost prea agresiv:
		// nu ai fi putut folosi metoda asta in liniste. Poate tu chiar vrei sa vezi daca ai vren elem inauntru.
	}

	private void metodaCuNumeCuviincios(Order o) {
		System.out.println("Am gasit order " + o);
		System.out.println("XXxx");
	}

	/**
	 * @return true if customer has at least one order with status ACTIVE
	 */
	public boolean p3_hasActiveOrders(Customer customer) {
		return true; 
	}

	/**
	 * An Order can be returned if it doesn't contain 
	 * any OrderLine with isSpecialOffer()==true
	 */
	public boolean p4_canBeReturned(Order order) {
		return true; // order.getOrderLines().stream() 
	}
	
	// ---------- select the best ------------
	
	/**
	 * The Order with maximum getTotalPrice. 
	 * i.e. the most expensive Order, or null if no Orders
	 * - Challenge: return an Optional<creationDate>
	 */
	public Order p5_getMaxPriceOrder(Customer customer) {
		return null; 
	}
	
	/**
	 * last 3 Orders sorted descending by creationDate
	 */
	public List<Order> p6_getLast3Orders(Customer customer) {
		return null; 
	}
	
	
}
