package victor.training.java.advanced.model;

import lombok.Data;

@Data
public class OrderLine {
   private Product product;
   private int itemCount;
}
