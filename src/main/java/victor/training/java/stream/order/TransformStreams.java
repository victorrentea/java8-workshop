package victor.training.java.stream.order;

import victor.training.java.stream.order.dto.OrderDto;
import victor.training.java.stream.order.entity.Customer;
import victor.training.java.stream.order.entity.Order;
import victor.training.java.stream.order.entity.Order.PaymentMethod;
import victor.training.java.stream.order.entity.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class TransformStreams {

   interface Mea {
      Date f();
   }

   /**
    * Transform all entities to DTOs.
    * Discussion:.. Make it cleanest!
    */
   public List<OrderDto> p01_toDtos(List<Order> orders) {

      // prin target typing, javac iti 'instantiaza' interfata ceruta in cod pe baza lambde/met ref tau
      Mea s4 = Date::new;
      Supplier<Date> s = Date::new;
      Function<Long, Date> s2 = Date::new;

      // asta nu compileaza pentru ca Javac nu stie la ce INTERFATA FUNCTIONALA sa atribuie expresia ta.
//		var f2 = TransformStreams::toDto;
      BiFunction<TransformStreams, Order, OrderDto> f = TransformStreams::toDto;
      return orders.stream().map(this::toDto).collect(toList());
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
      return customer.getOrders().stream().map(Order::getPaymentMethod).collect(toSet());
   }

   /**
    * When did the customer created orders ?
    * Note: Order.getCreationDate()
    */
   public SortedSet<LocalDate> p03_getOrderDatesAscending(Customer customer) {
      return customer.getOrders().stream()
          .map(order -> order.getCreationDate())
          .collect(Collectors.toCollection(TreeSet::new));
   }


   /**
    * @return a map order.id -> order
    */
   public Map<Long, Order> p04_mapOrdersById(Customer customer) {
      return customer.getOrders().stream()
          .collect(toMap(Order::getId, Function.identity()));
   }

   /**
    * Orders grouped by Order.paymentMethod
    */
   public Map<PaymentMethod, List<Order>> p05_getProductsByPaymentMethod(Customer customer) {
      return customer.getOrders().stream()
          .collect(groupingBy(order -> order.getPaymentMethod()));
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
          .collect(groupingBy(orderLine -> orderLine.getProduct(),
              Collectors.summingLong(orderLine -> orderLine.getCount())));

   }

   /**
    * All the unique products bought by the customer,
    * sorted by Product.name.
    */
   public List<Product> p07_getAllOrderedProducts(Customer customer) {
      return customer.getOrders().stream()
          .flatMap(order -> order.getOrderLines().stream())
          .map(line -> line.getProduct())
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
      return p07_getAllOrderedProducts(customer)
          .stream()
          .map(p -> p.getName())
          .collect(joining(","));
   }

   /**
    * Sum of all Order.getTotalPrice(), truncated to Long.
    */
   public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
      // TODO +, longValue(), reduce()
      return null;
   }
}
