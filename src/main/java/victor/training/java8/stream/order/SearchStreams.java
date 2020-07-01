package victor.training.java8.stream.order;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.Order.Status;

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
			.filter(order -> order.getStatus() == Status.ACTIVE)
			.collect(toList());
		// new Interface() { met() } -> poate sa fie convertita in Lambda -> daca :
		//1) interfata are o singura metoda
		// 2) nu ai alte functii/campuri definite
	}
	
	/**
	 * @return the Order in the list with the given id  
	 * - ...Any or ...First ?
	 * - what do you do when you don't find it ? null/throw/Optional ?
	 */
	public Order p2_getOrderById(List<Order> orders, long orderId) {
		return orders.stream()
				.filter(order -> order.getId() == orderId)
//		When it's red , yellow , blue or gray
//		Alt-Enter will save your day.
				.findFirst()
				.orElseThrow(() -> {
					System.out.println("Instantiez exceptie HAhshsblaladshslal");
					return new MyAppException(ErrorCode.NO_ORDER_BY_ID); // @RestControllerAdvice traduce aceste coduri i18n prin messages_EN.properties
				});
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
