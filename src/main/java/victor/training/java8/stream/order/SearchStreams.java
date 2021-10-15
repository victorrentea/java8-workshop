package victor.training.java8.stream.order;

import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.Order.Status;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class SearchStreams {

   /**
    * FIRST OF ALL: Add the following "Favourite" static imports:
    * Eclipse: Window > Preferences > type "Favo" > Favorites >
    * > New Type > Browse > and type the class name for:
    * java.util.stream.Collectors
    */

   interface AMea {
      void f(int x, int y, int z);
   }

   /**
    * - shorten/clean it up
    */
   public List<Order> p1_getActiveOrders(Customer customer) {

      AMea mea = (x, y, z) -> System.out.println("#sieu");

      // target typing : fara castul explicit de jos NU compileaza. ca nu stie ce tip are lambda.
      Object p = (Predicate<Order>) (order) -> {
         return order.getStatus() == Status.ACTIVE;
      };

      Predicate<Order> predicate = new Predicate<>() {
         @Override
         public boolean test(Order order) {
            return false;
         }
      };

      /// ------------ o linie -------------------
      Predicate<Order> isActive = Order::isActive;
      isActive.test(new Order());

      /// ------------ o linie -------------------
      Order order1 = new Order(); // am deja instanta
      Supplier<Boolean> f1 = order1::isActive; // refer metoda de instanta din intanta ece deja o am
      boolean b = f1.get();
      /// ------------ o linie -------------------

      return customer.getOrders().stream()
          .filter(order -> order.isActive()) // toleram doar one-liner lambdas
          .filter(isActive) // toleram doar one-liner lambdas
          .collect(toList());
   }

   /**
    * @return the Order in the list with the given id
    * - ...Any or ...First ?
    * - what do you do when you don't find it ? null/throw/Optional ?
    */
   public Order p2_getOrderById(List<Order> orders, long orderId) {
      Optional<Order> orderOpt = orders.stream()
          .filter(o -> o.getId() == orderId)
          .findFirst();
// GET order/1001 <<<<< nu aveai cum sa vii din FE cu id de order inexsitent. decat daca face un HACKER magarii cu curl
      Order order = orderOpt
          .get(); // nu pot continua
//			.orElseThrow(() -> new RuntimeException("Sa-mi fie cu ieratare, dar order cu id = " + orderId + " nu exista"))
//			.orElse(null);

      cheamaLogicaInfricosatoare(order);

      return order;
   }

   private void cheamaLogicaInfricosatoare(Order order) {
      met1(order);
   }

   private void met1(Order order) {
      met2(order);
   }

   private void met2(Order order) {
      System.out.println(order.getCreationDate());
   }

   /**
    * @return true if customer has at least one order with status ACTIVE
    */
   public boolean p3_hasActiveOrders(Customer customer) {
      Predicate<Order> isActive = order -> order.isActive() && order.getOrderLines().size() > 2
                                           || order.getCustomer()!= null;

      return customer.getOrders().stream()
//          .filter(o -> o.isActive()).findFirst().isPresent();

//          .filter(o -> o.isActive()).collect(toList()).size() >= 1;
//          .filter(o -> o.isActive()).count() >=1;

         // asta e cel corect ce l mai scur
          .anyMatch(Order::isActive);
   }

//   public boolean isActive(Order order) {
//      return order.getStatus() == Status.ACTIVE;
//   }

   /**
    * An Order can be returned if it doesn't contain
    * any OrderLine with isSpecialOffer()==true
    */
   public boolean p4_canBeReturned(Order order) {
      return order.getOrderLines().stream()
          .noneMatch(ol -> ol.isSpecialOffer());

   }

   // ---------- select the best ------------

   /**
    * The Order with maximum getTotalPrice.
    * i.e. the most expensive Order, or null if no Orders
    * - Challenge: return an Optional<creationDate>
    */
   public Order p5_getMaxPriceOrder(Customer customer) {
//      Order maxDePanaAcum = customer.getOrders().get(0);
//      for (Order order : customer.getOrders()) {
//         if (order.getTotalPrice().compareTo(maxDePanaAcum.getTotalPrice()) >= 0) {
//            maxDePanaAcum = order;
//         }
//      }

//      Order max = customer.getOrders().stream().reduce(customer.getOrders().get(0),
//          (order, order2) -> order.getTotalPrice().compareTo(order2.getTotalPrice()) > 0 ? order : order2);

//      Order max = customer.getOrders().stream().max((o1, o2) -> o1.getTotalPrice().compareTo(o2.getTotalPrice()));

      Comparator<Order> comparatorDeMana = (o1,o2) -> o1.getTotalPrice().compareTo(o2.getTotalPrice());
      Comparator<Order> comparator = comparing(Order::getTotalPrice);

      return customer.getOrders().stream().max(comparing(Order::getTotalPrice)).orElse(null);
   }

   /**
    * last 3 Orders sorted descending by creationDate
    */
   public List<Order> p6_getLast3Orders(Customer customer) {
      return customer.getOrders().stream()
          .sorted(comparing(Order::getCreationDate).reversed())
          .limit(3)
          .collect(toList());
   }


}
