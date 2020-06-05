package victor.training.java8.stream.order;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.jooq.lambda.Unchecked;
import org.jooq.lambda.fi.util.function.CheckedSupplier;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

@Service
class OrderMapper {
   // @Autowired alte dep;
   public OrderDto toDto(Order order) {
      return new OrderDto(order);
   }
}

public class TransformStreams {
   @Autowired
   private OrderMapper orderMapper = new OrderMapper();

   /**
    * Transform all entities to DTOs.
    * Discussion:.. Make it cleanest!
    */
   public List<OrderDto> p01_toDtos(List<Order> orders) {

      Function<String, Integer> f1 = String::length; // f(string):int
      Supplier<Integer> f2 = "abc"::length; // f():int
      Function<String, Integer> f3 = Integer::parseInt; // f(String):Integer
      Function<Order, Long> f4 = Order::getId; // f(Order):Long
      Order order1 = new Order();
      Supplier<Long> f5 = order1::getId;// f():Long    () -> order1.getId();
      Callable<Long> f6 = order1::getId;// f():Long

//		/*Function<Order, OrderDto> f7 =*/ OrderMapper::toDto; // (Order) -> OrderDto <--- asta daca toDto este statica


      Function<List<Order>, List<OrderDto>> f9 = this::p01_toDtos; // (List<Order>) -> List<OrderDto>
      BiFunction<TransformStreams, List<Order>, List<OrderDto>> f10 = TransformStreams::p01_toDtos; //

      BiFunction<Integer, Integer, Integer> f11 = Math::max; //
      Function<Order, BigDecimal> f12 = Order::getTotalPrice; // (Order) -> BigDecimal
      Supplier<BigDecimal> f13 = order1::getTotalPrice; // () -> BigDecimal
      Consumer<BigDecimal> f14 = order1::setTotalPrice; // (BigDecimal) -> nimic
      BiConsumer<Order, BigDecimal> f15 = Order::setTotalPrice; // (Order,BigDecimal) -> nimic

      BigDecimal zero = BigDecimal.ZERO;
      BigDecimal ten = BigDecimal.TEN;
      BigDecimal unshpe = zero.add(ten);
      BiFunction<BigDecimal, BigDecimal, BigDecimal> f16 = BigDecimal::add; // (BD,BD) -> BD
      Function<BigDecimal, BigDecimal> f17 = ten::add; //

      BiFunction<OrderMapper, Order, OrderDto> f7 = OrderMapper::toDto; // (OrderMapper, Order) -> OrderDto -->
      Function<Order, OrderDto> f8 = orderMapper::toDto; // (Order) -> OrderDto

      Function<Order, OrderDto> f8bis = OrderDto::new; // wtf?! : f(Order)-> OrderDto

      try (Stream<String> lines = Files.lines(new File("test.ok.txt").toPath())) {
         lines.filter(StringUtils::isNotBlank).map(String::toUpperCase).collect(toList());
//			lines.clo
      } catch (IOException e) {
         e.printStackTrace();
      }

//		new Date()
      Supplier<Date> f18 = Date::new;
//		new Date(1L);
      Function<Long, Date> f19 = Date::new;


//      return orders.stream().map(orderMapper::toDto).collect(toList());
      return orders.stream().map(OrderDto::new).collect(toList());
   }


   /**
    * Note: Order.getPaymentMethod()
    */
   public Set<PaymentMethod> p02_getUsedPaymentMethods(Customer customer) {
      Function<Order, PaymentMethod> f = Order::getPaymentMethod;
      return customer.getOrders().stream().map(f).collect(toSet());
   }

   /**
    * When did the customer created orders ?
    * Note: Order.getCreationDate()
    */
   public SortedSet<LocalDate> p03_getOrderDatesAscending(Customer customer) {
      return customer.getOrders().stream().map(Order::getCreationDate).collect(toCollection(TreeSet::new));
   }


   /**
    * @return a Map order.id -> order
    */
   public Map<Long, Order> p04_mapOrdersById(Customer customer) {
      // pericole: daca doua intrari produc aceeasi cheie, crapa cu exceptie, daca nu oferi un merge function (vzi overloadurile)
      // pericol2: si mai naspa: daca valoarea pusa in mapa e null -> exceptie!!
      return customer.getOrders().stream().collect(toMap(Order::getId, order -> order));
   }

   /**
    * Orders grouped by Order.paymentMethod
    * GROUP BY
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

      return customer.getOrders()
          .stream()
          .flatMap(order -> order.getOrderLines().stream())
//			.collect(toList()).stream()
          .collect(groupingBy(OrderLine::getProduct, summingLong(OrderLine::getCount)));

   }

   /**
    * All the unique products bought by the customer,
    * sorted by Product.name.
    */
   public List<Product> p07_getAllOrderedProducts(Customer customer) {
      return customer.getOrders()
          .stream()
          .flatMap(o -> o.getOrderLines().stream())
          .map(OrderLine::getProduct)
          .sorted(comparing(Product::getName))
          .distinct() // va tine toate elementele in memorie pentru a putea sti daca lasa sa treaca noul element.
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
          .map(Product::getName)
          .collect(joining(","));
   }

   /**
    * Sum of all Order.getTotalPrice(), truncated to Long.
    */
   public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
      long sum = 0;
//      customer.getOrders().stream()
//          .map(Order::getTotalPrice)
//          .forEach(bd -> {
//             sum += bd.longValue();
//          });


      return customer.getOrders().stream()
          .map(Order::getTotalPrice)
          .mapToLong(BigDecimal::longValue)
          .sum();
   }

      static int x = 0; // merge
   public static Supplier<Integer> createSupplier() {
//      int x = 0; // NU merge pt ca variabila x dispare din stack odata cu terminarea executiei metodei astea
      return () -> ++ x;
   }

   public static Supplier<Integer> createSupplierY() {
      int y = 15; // NU merge pt ca variabila x dispare din stack odata cu terminarea executiei metodei astea
      return () -> y;
   }

   public static void main(String[] args) {
      Supplier<Integer> supplier = createSupplier();
      System.out.println(supplier.get());
      System.out.println(supplier.get());
   }

   // ----------- IO ---------------

   /**
    * - Read lines from file as Strings.
    * - Where do you close the opened file?
    * - Parse those to OrderLine using the function bellow
    * - Validate the created OrderLine. Throw ? :S
    */
   public List<OrderLine> p10_readOrderFromFile(File file) throws IOException {

      try (MyRes r = new MyRes()) {
         System.out.println("Corb try");
      }
//Stream~=Iterator
      Supplier<Stream<String>> streamSupplier = Unchecked.supplier(() -> Files.lines(file.toPath()));

      return logicaDeImport(streamSupplier);
      // TODO check the number of lines is >= 2

   }

   private List<OrderLine> logicaDeImport(Supplier<Stream<String>> streamSupplier) {
      try (Stream<String> lines = streamSupplier.get()) { // ??
         if (lines.count() < 2) {
            throw new IllegalArgumentException("Too few lines");
         }
      }
      try (Stream<String> lines = streamSupplier.get()) { // ??
         return lines
             .map(line -> line.split(";")) // Stream<String[]>
             .filter(cell -> "LINE".equals(cell[0]))
             .map(this::parseOrderLine) // Stream<OrderLine>
             .peek(this::validateOrderLine)
             .collect(toList());

      }
   }


   private OrderLine parseOrderLine(String[] cells) {
      return new OrderLine(new Product(cells[1]), Integer.parseInt(cells[2]));
   }

   private void validateOrderLine(OrderLine orderLine) {
      if (orderLine.getCount() < 0) {
         throw new IllegalArgumentException("Negative items");
      }
   }




   class MyRes implements AutoCloseable {
      public void close() {
         System.out.println("Closing res");
      }
   }

   // TODO print cannonical paths of all files in current directory
   // use Unchecked... stuff
}
