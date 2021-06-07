package victor.training.java8.stream.order;

import org.springframework.stereotype.Component;
import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Order;
@Component
public class OrderMapper {
   public OrderDto convert(Order order) {
      OrderDto dto = new OrderDto(order);
      return dto;
   }
}
