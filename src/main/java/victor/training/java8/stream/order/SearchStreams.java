package victor.training.java8.stream.order;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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
		// new Interface() { met() } -> poate sa fie convertita in Lambda -> daca :
		//1) interfata are o singura metoda
		// 2) nu ai alte functii/campuri definite
	}
	
	/**
	 * @return the Order in the list with the given id  
	 * - ...Any or ...First ?
	 * - what do you do when you don't find it ? null/throw/Optional ?
	 */
	public Optional<Order> p2_getOrderById(List<Order> orders, long orderId) {
		return orders.stream()
				.filter(order -> order.getId() == orderId)
//		When it's red , yellow , blue or gray
//		Alt-Enter will save your day.
				.findFirst()
				/*.orElseThrow(() -> {
					System.out.println("Instantiez exceptie HAhshsblaladshslal");
					return new MyAppException(ErrorCode.NO_ORDER_BY_ID); // @RestControllerAdvice traduce aceste coduri i18n prin messages_EN.properties
				})*/;

		// userRepo.findByEmail(email); - ar trebui sa crape mereu cu ex?
		// createUser -> sa verifici ca NU e nici unul deja cu emailul userului de creat
	}
	
	/**
	 * @return true if customer has at least one order with status ACTIVE
	 */
	public boolean p3_hasActiveOrders(Customer customer) {
		Predicate<Order> isActive = Order::isActive;
		List<Order> activeOrders = customer.getOrders().stream()
			.filter(isActive)
			.collect(toList());
//		return activeOrders.size() >= 1;

//		return !activeOrders.isEmpty();

//		return customer.getOrders().stream()
//			.filter(Order::isActive)
//			.count() >= 1; // nu mai collect() in nicio lista. Puteau fi  ff f multe elemente.

//		return customer.getOrders().stream()
//			.filter(Order::isActive)
//			.findFirst()
//			.isPresent(); // nu mai traversezi toate elementele, ci te opresti la primul gasit

		return customer.getOrders().stream().anyMatch(Order::isActive); // ce beton arata
	}

	/**
	 * An Order can be returned if it doesn't contain 
	 * any OrderLine with isSpecialOffer()==true
	 */
	public boolean p4_canBeReturned(Order order) {
//		return ! order.getOrderLines().stream().filter(o -> o.isSpecialOffer()).findFirst().isPresent();
//		return !order.getOrderLines().stream().anyMatch(o -> o.isSpecialOffer());

		Optional<OrderLine> oneSpecialOffer = order.getOrderLines().stream().filter(OrderLine::isSpecialOffer).findFirst();
//		if (oneSpecialOffer.isPresent()) {
//			metodaDacaE(oneSpecialOffer.get());
//		}
		oneSpecialOffer.ifPresent(so -> {
			metodaDacaE(so);
			metodaDacaE(so);
			metodaDacaE(so);
		});



//		order.getOrderLines().stream().forEach(e -> {
//			if (n-am gasit ) {
//				fac cu elem
//			} else {
//
//			}
//
//		});

		return order.getOrderLines().stream().noneMatch(OrderLine::isSpecialOffer);
	}

	private void metodaDacaE(OrderLine oneSpecialOffer) {
		System.out.println("Nu poti returna orderul pentru ca ai urmatoarele order lines: " + oneSpecialOffer);
		System.out.println("Nu poti returna orderul pentru ca ai urmatoarele order lines: " + oneSpecialOffer);
		System.out.println("Nu poti returna orderul pentru ca ai urmatoarele order lines: " + oneSpecialOffer);
		System.out.println("Nu poti returna orderul pentru ca ai urmatoarele order lines: " + oneSpecialOffer);
		System.out.println("Nu poti returna orderul pentru ca ai urmatoarele order lines: " + oneSpecialOffer);
		System.out.println("Nu poti returna orderul pentru ca ai urmatoarele order lines: " + oneSpecialOffer);
	}

	// ---------- select the best ------------
	
	/**
	 * The Order with maximum getTotalPrice. 
	 * i.e. the most expensive Order, or null if no Orders
	 * - Challenge: return an Optional<creationDate>
	 * @return
	 */
	public Optional<LocalDate> p5_getMaxPriceOrder(Customer customer) {

		// lambda style
//		Comparator<Order> orderComparator = (o1, o2) -> o1.getTotalPrice().compareTo(o2.getTotalPrice());

//		Comparator<Order> orderComparator = Comparator.comparing(Order::getTotalPrice);

		return customer.getOrders().stream()
			.max(comparing(Order::getTotalPrice))
			.map(o -> o.getCreationDate());
	}
	
	/**
	 * last 3 Orders sorted descending by creationDate
	 */
	public List<Order> p6_getLast3Orders(Customer customer) {

		return customer.getOrders().stream()
			.sorted(Comparator.comparing(Order::getCreationDate).reversed())
			.limit(3)
//			.skip(1)
			.collect(toList());

	}
	
	
}
