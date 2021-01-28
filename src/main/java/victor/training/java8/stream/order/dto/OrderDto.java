package victor.training.java8.stream.order.dto;

import victor.training.java8.stream.order.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDto {

	public BigDecimal totalPrice;
	public LocalDate creationDate;

	public OrderDto() {}

   public OrderDto(Order order) {
      totalPrice = order.getTotalPrice();
      creationDate = order.getCreationDate();
   }
}
