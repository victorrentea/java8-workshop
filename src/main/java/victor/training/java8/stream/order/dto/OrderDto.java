package victor.training.java8.stream.order.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import victor.training.java8.stream.order.entity.Order;

public class OrderDto {

	public OrderDto(Order order) {
		totalPrice = order.getTotalPrice(); 
		creationDate = order.getCreationDate();
	}
	public OrderDto() {
	}
	public BigDecimal totalPrice;
	public LocalDate creationDate;


}
