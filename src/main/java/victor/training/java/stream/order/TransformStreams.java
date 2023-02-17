package victor.training.java.stream.order;

import victor.training.java.stream.order.dto.OrderDto;
import victor.training.java.stream.order.entity.Customer;
import victor.training.java.stream.order.entity.Order;
import victor.training.java.stream.order.entity.Order.PaymentMethod;
import victor.training.java.stream.order.entity.OrderLine;
import victor.training.java.stream.order.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class TransformStreams {

  /**
   * Transform all entities to DTOs.
   * use .map
   */
  public List<OrderDto> p01_toDtos(List<Order> orders) {

    // REFERIREA unei metoda de instana printr-un mod STATIC TransformStreams::
    // nu merge pentru ca fct referita asa primeste ca prim parametru INSTANTA pe care ruleaza
    //		Function<Order, OrderDto> nu= TransformStreams::toDto;

    BiFunction<TransformStreams, Order, OrderDto> da = TransformStreams::toDto;
    OrderDto r = da.apply(this, new Order());

    Function<Order, OrderDto> ff = this::toDto;
    OrderDto r2 = ff.apply(new Order());

    String firstName = "a";
    Supplier<Integer> s = firstName::length; // f():int

    Predicate<Order> o = Order::isActive;

    List<String> l;

    BiPredicate<List, Object> bp = List::add;

    Consumer<Object> c = System.out::println;
    orders.forEach(System.out::println);

    Function<Order, OrderDto> omg = OrderDto::new;
    return orders.stream()
            //						.map(this::toDto)
            .map(this::toDto)
            .collect(Collectors.toList());

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
    return customer.getOrders().stream()
            .map(Order::getPaymentMethod)
            .collect(Collectors.toSet());
  }

  /**
   * When did the customer created orders ?
   * Note: Order.getCreationDate()
   */
  public SortedSet<LocalDate> p03_getOrderDatesAscending(Customer customer) {
//    return customer.getOrders().stream()
//            .map(Order::getCreationDate)
//            .collect(toCollection(TreeSet::new));

    // GRESIT. NON-FP ca face side effects
    TreeSet<LocalDate> set = new TreeSet<>();

    customer.getOrders().stream()
            .map(Order::getCreationDate)
            .forEach(e -> set.add(e)); // side effects
     return set;
  }


  /**
   * @return a map order.id -> order
   */
  public Map<Long, Order> p04_mapOrdersById(Customer customer) {
    return customer.getOrders().stream()
            .collect(toMap(Order::getId, identity())); // crapa daca au 2 order acelasi ID
    //						.collect(toMap(Order::getId, identity(), (e1,e2)->e1)); + merge func
  }

  /**
   * Orders grouped by Order.paymentMethod
   */
  public Map<PaymentMethod, List<Order>> p05_getProductsByPaymentMethod(Customer customer) {
    return customer.getOrders().stream().collect(groupingBy(Order::getPaymentMethod));
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
	  return customer.getOrders().stream()
					  .flatMap(order -> order.getOrderLines().stream())
					  .map(OrderLine::getProduct)
					  .distinct() // elimina elemente care sunt == intre ele cu Object.equals
					  .sorted(Comparator.comparing(Product::getName))
					  .collect(toList());
  }


  // TODO suma orderului platite cu cardul
  public long method(Customer c) {
    return c.getOrders().stream()
            .filter(o -> o.getPaymentMethod() == PaymentMethod.CARD)
            .mapToLong(o -> o.getTotalPrice().longValue())
            .sum();
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
					.collect(joining(",")); // arr.join(',')
  }

  /**
   * Sum of all Order.getTotalPrice(), truncated to Long.
   */
  public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
    // TODO +, longValue(), reduce()
//    for (Order order : customer.getOrders()) {
//
//    }

    // PURE FP cu iz de facultate an 2-3; in java insa avem ceva mai simplu ca reduce
//    BiFunction<BigDecimal,BigDecimal, BigDecimal> omg =  (bd1, bd2) -> bd1.add(bd2);
//    BinaryOperator<BigDecimal> op =  (bd1, bd2) -> bd1.add(bd2);
//    return customer.getOrders().stream()
//            .map(Order::getTotalPrice)
//            .reduce(BigDecimal.ZERO, BigDecimal::add)
//            .longValue();

    LongStream numericStream;
//    numericStream.sum()
    Stream<Long> altceva;

    // Asa da: streamuri numerice


    Stream<BigDecimal> streamInVariabila = customer.getOrders().stream()
            .map(Order::getTotalPrice);

    // PERICULOS SA TII UN STREAM INTR-O VARIABLA pentru ca cineva ar putea fi tentat sa-l foloeasca de 2 ori
//    if (streamInVariabila.count() > 4) {
//      System.out.println("URAA!!");
//    }

    return streamInVariabila
            .mapToLong(BigDecimal::longValue)
            .sum();

  }


  public void discutieInCodIn2023() {
    List<Order> numbers = new ArrayList<>();
//    for (int i = 0; i < 10; i++) {
//      numbers.add(new Order().setId((long) i));
//    }

    numbers = IntStream.range(0, 10)
            .mapToObj(i -> new Order().setId((long) i))
            .collect(toList());

    System.out.println("Ceva "+ numbers);
  }
}
