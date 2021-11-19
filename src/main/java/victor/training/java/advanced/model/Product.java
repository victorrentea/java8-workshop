package victor.training.java.advanced.model;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Entity
//@Immutable
@NoArgsConstructor(access = PRIVATE)
public class Product {
   @Id
   @GeneratedValue
   @EqualsAndHashCode.Exclude
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
