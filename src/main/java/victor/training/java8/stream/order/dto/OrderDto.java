package victor.training.java8.stream.order.dto;

import victor.training.java8.stream.order.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDto {

	public BigDecimal totalPrice;
	public LocalDate creationDate;

	public OrderDto() {}

	// foarte ok sa lasi obiectul JSON sa se creeze din Entitate. E ok sa depinzi cu DTO de Entitate, dar nu care cumva in sens invers
	public OrderDto(Order entity) {
		totalPrice = entity.getTotalPrice();
		creationDate = entity.getCreationDate();
	}
}
