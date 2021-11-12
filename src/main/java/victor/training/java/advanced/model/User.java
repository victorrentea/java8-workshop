package victor.training.java.advanced.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@Entity
public class User {
   @Id
   private Long id;
   private String username;
   private String fullName;
   private Boolean active = true;
   private Integer ticketsRaised;
   @Enumerated(STRING)
   private Language language;
}
