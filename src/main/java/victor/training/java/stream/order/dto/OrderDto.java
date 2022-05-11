package victor.training.java.stream.order.dto;

import victor.training.java.stream.order.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDto {

	public BigDecimal totalPrice;
	public LocalDate creationDate;


    public OrderDto(Order order) {
        totalPrice = order.getTotalPrice();
        creationDate = order.getCreationDate();
    }
}
