package victor.training.java8.advanced.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class User {
   @Id
   private Long id;
   private String username;
   private String fullName;
}
