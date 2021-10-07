package victor.training.java8.advanced.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Product {
   @Id
   @GeneratedValue
   private Long id;
   private String name;
   private boolean deleted;
   private boolean hidden;

   public Product(String name) {
      this.name = name;
   }

   public boolean isActive() {
      return !deleted;
   }
}
