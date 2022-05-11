package victor.training.java.stream.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

import victor.training.java.stream.order.dto.OrderDto;
import victor.training.java.stream.order.entity.Customer;
import victor.training.java.stream.order.entity.Order;
import victor.training.java.stream.order.entity.OrderLine;
import victor.training.java.stream.order.entity.Product;
import victor.training.java.stream.order.entity.Order.PaymentMethod;

import static java.util.stream.Collectors.*;

public class TransformStreams {

    /**
     * Transform all entities to DTOs.
     * use .map
     */
    public List<OrderDto> p01_toDtos(List<Order> orders) {

        BiFunction<TransformStreams, Order,OrderDto> surpriza = TransformStreams::toDto;  // daca referi o metoda de instanta din numele clasei, primul param al fct trebuise sa fie instanta pe care chemi acea metoda
        Function<Order,OrderDto> nu = this::toDto;

        String s = "wow";
        Supplier<Integer> x = s::length; // f()

//        new Date(123L)
        Function<Long, Date> f1 = Date::new;
        Supplier<Date> f2 = Date::new;

        Function<Order, OrderDto> f = OrderDto::new;
        return orders.stream().map(f).collect(toList());
    }

    public OrderDto toDto(Order order) {
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
        return null;
    }


    /**
     * @return a map order.id -> order
     */
    public Map<Long, Order> p04_mapOrdersById(Customer customer) {
        return customer.getOrders().stream().collect(toMap(Order::getId, Function.identity()));
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
//		 return allLines.stream().collect(groupingBy(OrderLine::getProduct, reducing(0L, line -> (Long) (long) line.getCount(),
//				 (acc, valNoua) -> acc + valNoua)));
    }
    // filtrezi cat mai agresiv datele in baza (nu aduci 100.000 de obiecte Java sa le filtrezi, ci pui un WHERE  care sa reduca la 100)
    // e ok sa aduci cateva sute in java sa le filtrezi/procesezi daca ai LOGICA GREA de biz de implementat

    /**
     * All the unique products bought by the customer,
     * sorted by Product.name.
     */
    public List<Product> p07_getAllOrderedProducts(Customer customer) {
        return customer.getOrders().stream()
                .flatMap(o -> o.getOrderLines().stream())
                .map(OrderLine::getProduct)
                .distinct()
                .sorted(Comparator.comparing(Product::getName))
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
                .collect(joining(","));
    }

    /**
     * Sum of all Order.getTotalPrice(), truncated to Long.
     */
    public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
        // TODO +, longValue(), reduce()
        return customer.getOrders().stream()
                .map(Order::getTotalPrice)
                //                .mapToLong(BigDecimal::longValue)
                //                .sum();
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .longValue();
    }
}
