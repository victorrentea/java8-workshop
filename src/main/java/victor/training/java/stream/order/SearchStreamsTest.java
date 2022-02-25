package victor.training.java.stream.order;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import victor.training.java.stream.order.entity.Customer;
import victor.training.java.stream.order.entity.Order;
import victor.training.java.stream.order.entity.OrderLine;
import victor.training.java.stream.order.entity.Order.Status;

@TestMethodOrder(MethodName.class)
public class SearchStreamsTest {

	private SearchStreams service = new SearchStreams();

	@Test
	public void p1_getActiveOrders() {
		Order order1 = new Order(Status.ACTIVE);
		Order order2 = new Order(Status.ACTIVE);
		Order order3 = new Order(Status.DRAFT);
		Customer customer = new Customer(order1, order2, order3);
		assertEquals(Arrays.asList(order1, order2), service.p1_getActiveOrders(customer));
	}

	@Test
	public void p2_getOrderById() {
		List<Order> orders = Arrays.asList(new Order(1L), new Order(2L), new Order(3L));
		assertEquals(2L, (long) service.p2_getOrderById(orders, 2L).getId());
	}
	
	@Test
//	@Ignore
	public void p2_getOrderById_whenIdNotFound() {
		List<Order> orders = Arrays.asList(new Order(1L));
		assertEquals(null, service.p2_getOrderById(orders, 1000L));
	}

	@Test
	public void p3_hasActiveOrders_true() {
		Customer customer = new Customer(new Order(Status.INACTIVE), new Order(Status.ACTIVE));
		assertTrue(service.p3_hasActiveOrders(customer));
	}

	@Test
	public void p3_hasActiveOrders_false() {
		Customer customer = new Customer(new Order(Status.INACTIVE));
		assertFalse(service.p3_hasActiveOrders(customer));
	}
	
	@Test
	public void p4_canBeReturned_frue() {
		Order order = new Order(new OrderLine());
		assertTrue(service.p4_canBeReturned(order));
	}
	
	@Test
	public void p4_canBeReturned_false() {
		OrderLine specialOffer = new OrderLine().setSpecialOffer(true);
		Order order = new Order(specialOffer, new OrderLine());
		assertFalse(service.p4_canBeReturned(order));
	}
	
	@Test
	public void p5_getMaxPriceOrder() {
		LocalDate yesterday = now().minusDays(1);
		Order order1 = new Order().setTotalPrice(BigDecimal.ONE).setCreationDate(now());
		Order order2 = new Order().setTotalPrice(BigDecimal.TEN).setCreationDate(yesterday);
		assertEquals(order2, service.p5_getMaxPriceOrder(new Customer(order1, order2)).get());
		// assertEquals(yesterday, service.p5_getMaxPriceOrder(new Customer(order1, order2)).get());
	}
	
	@Test
	public void p5_getMaxPriceOrder_whenNoOrders_returnsNothing() {
		assertTrue(service.p5_getMaxPriceOrder(new Customer()).isEmpty());
		// assertFalse(service.p5_getMaxPriceOrder(new Customer()).isPresent());
	}
	
	@Test
	public void p6_getLast3Orders() {
		Order order1 = new Order().setCreationDate(LocalDate.parse("2016-01-01"));
		Order order2 = new Order().setCreationDate(LocalDate.parse("2016-01-02"));
		Order order3 = new Order().setCreationDate(LocalDate.parse("2016-01-03"));
		Order order4 = new Order().setCreationDate(LocalDate.parse("2016-01-04"));
		
		Customer customer = new Customer(order1, order2, order3, order4);
		assertEquals(Arrays.asList(order4,order3,order2), service.p6_getLast3Orders(customer));
	}
	
	@Test
	public void p6_getLast3Orders_whenOnlyTwoOrders() {
		Order order1 = new Order().setCreationDate(LocalDate.parse("2016-01-01"));
		Order order2 = new Order().setCreationDate(LocalDate.parse("2016-01-02"));
		
		Customer customer = new Customer(order1, order2);
		assertEquals(Arrays.asList(order2, order1), service.p6_getLast3Orders(customer));
	}
	
	@Test
	public void p6_getLast3Orders_whenNoOrders() {
		Customer customer = new Customer();
		assertEquals(Arrays.asList(), service.p6_getLast3Orders(customer));
	}
	

}
