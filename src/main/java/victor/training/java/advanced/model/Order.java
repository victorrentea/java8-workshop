package victor.training.java.advanced.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data

@Entity
@Table(name = "ORDERS")
public class Order {
   @Id
   @GeneratedValue
   private Long id;
   @Transient // cheat
   private List<OrderLine> orderLines;
   private LocalDate creationDate;
   @Transient // cheat
   private Customer customer;
   private Boolean active;

}
