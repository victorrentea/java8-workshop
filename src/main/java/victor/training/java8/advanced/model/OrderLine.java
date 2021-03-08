package victor.training.java8.advanced.model;

import lombok.Data;

@Data
public class OrderLine {
   private Product product;
   private int itemCount;
}
