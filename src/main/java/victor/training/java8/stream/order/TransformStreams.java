package victor.training.java8.stream.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

class OrderMapper {
   public OrderDto convert(Order order) {

      return new OrderDto(order);
   }
}

public class TransformStreams {
   private final OrderMapper mapper = new OrderMapper();

   /**
    * Transform all entities to DTOs.
    * Discussion:.. Make it cleanest!
    */
   public List<OrderDto> p01_toDtos(List<Order> orders) {

      Function<String, Integer> f = s -> Integer.parseInt(s);
      Function<String, Integer> f3 = Integer::parseInt;


      Function<Integer, Integer> f5 = Math::abs;
      MyOwn o = Math::abs; // target typing.
      MyOwn of = a -> Math.abs(a); // target typing.
//		Object oo = (String a) -> Math.abs(a); // does not compile

      Function<Double, Double> f6 = Math::abs;

//		f(Order):LocalDate
      Function<Order, LocalDate> f9 = Order::getCreationDate;
//		Order o = new Order();
//		f():LocalDate
//		Supplier<Order> s = o::getCreationDate;

      Function<Order, OrderDto> f1 = order -> mapper.convert(order);
      Function<Order, OrderDto> f2 = mapper::convert;

//		f(OrderMapper, Order):OrderDto ---
      BiFunction<OrderMapper, Order, OrderDto> ff = OrderMapper::convert;


      Function<Order, OrderDto> ctr = OrderDto::new;
      Supplier<OrderDto> ctr2 = OrderDto::new;

      Consumer<Order> println = System.out::println;
      orders.stream().filter(Order::isActive).forEach(println);


      Map<Boolean, List<Order>> collect = orders.stream().collect(partitioningBy(Order::isActive));
//		collect.get(true)  <--- ACTIVE Orders


      return orders.stream()
//			.map(mapper::convert)
          .map(OrderDto::new)
          .collect(toList());
   }

   /**
    * Note: Order.getPaymentMethod()
    */
   public Set<PaymentMethod> p02_getUsedPaymentMethods(Customer customer) {
      return customer.getOrders().stream().map(Order::getPaymentMethod).collect(toSet());
   }

   /**
    * When did the customer created orders ?
    * Note: Order.getCreationDate()
    */
   public SortedSet<LocalDate> p03_getOrderDatesAscending(Customer customer) {
      return customer.getOrders().stream().map(Order::getCreationDate).collect(toCollection(TreeSet::new));
   }

   /**
    * @return a map order.id -> order
    */
   public Map<Long, Order> p04_mapOrdersById(Customer customer) {
//		BinaryOperator<Order> merger = (a,b) -> b;
      return customer.getOrders().stream().collect(toMap(Order::getId, order -> order/*,
			merger*/));
   }

   /**
    * Orders grouped by Order.paymentMethod
    */
   public Map<PaymentMethod, List<Order>> p05_getProductsByPaymentMethod(Customer customer) {
      return customer.getOrders().stream().collect(groupingBy(Order::getPaymentMethod));
   }

   /**
    * A hard one !
    * Get total number of products bought by a customer, across all her orders.
    * Customer --->* Order --->* OrderLines(.count .product)
    * The sum of all counts for the same product.
    * i.e. SELECT PROD_ID, SUM(COUNT) FROM PROD GROUPING BY PROD_ID
    */
   public Map<Product, Long> p06_getProductCount(Customer customer) {
      List<OrderLine> allLines = customer.getOrders().stream()
          .flatMap(order -> order.getOrderLines().stream())
          .collect(toList());

      return allLines.stream()
          .collect(groupingBy(OrderLine::getProduct, summingLong(OrderLine::getCount)));

   }

   // -------------- MOVIE BREAK :p --------------------

   /**
    * All the unique products bought by the customer,
    * sorted by Product.name.
    */
   public List<Product> p07_getAllOrderedProducts(Customer customer) {

      return customer.getOrders().stream()
          .flatMap(order -> order.getOrderLines().stream())
          .map(OrderLine::getProduct)
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
      return p07_getAllOrderedProducts(customer).stream()
          .map(Product::getName)
          .peek(System.out::println)
          .collect(joining(","));
   }

   /**
    * Sum of all Order.getTotalPrice(), truncated to Long.
    */
//	public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
//
//		BiFunction<BigDecimal, BigDecimal, BigDecimal> f= BigDecimal::add;
//		BinaryOperator<BigDecimal> f2= BigDecimal::add;
//		 return customer.getOrders().stream()
//			.map(Order::getTotalPrice)
//			.reduce(BigDecimal.ZERO, BigDecimal::add)
//			 .longValue();
//	}
   public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
      return customer.getOrders().stream()
          .mapToLong(order -> order.getTotalPrice().longValue())
          .sum();
   }
//   public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
//      long sum = 0;
//      customer.getOrders().stream()
//          .map(order -> order.getTotalPrice().longValue())
//          .forEach(val -> {
//             sum += val;
//          });
//      return sum;
//   }

//   static Supplier<Integer> magic () {
//      final int n = 0;
//      return () -> n++;
//   }
//
//   static {
//      Supplier<Integer> supplier = magic();
//      //the stack of magic() is gone here. so is the memory occupied by n local var
//      System.out.println(supplier.get());
//      System.out.println(supplier.get());
//   }

   @FunctionalInterface
   interface MyOwn {
      Integer stuff(Integer i);
   }
}
