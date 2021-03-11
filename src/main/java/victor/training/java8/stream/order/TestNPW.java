package victor.training.java8.stream.order;

import victor.training.java8.stream.order.entity.Order;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class TestNPW {
   public static void main(String[] args) {

      System.out.println(Stream.of(new Order(1L))
          .collect(toMap(Order::getId, Order::getStatus)));
   }
}
