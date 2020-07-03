package victor.training.java8.lombok;

import lombok.*;
import lombok.EqualsAndHashCode.Include;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

public class Play {
   public static void main(String[] args) {
      Money usd = new Money(BigDecimal.ONE, Currency.getInstance("USD"));
      new Customer().setName("John").setAddress("Aici");
   }
}

@Repository
class CustomerRepo {

}

@Slf4j
@Service
@RequiredArgsConstructor
class CustomerService {
   private final CustomerRepo customerRepo;

}

//@Entity //@Document
//@ToString // dubios de pus, atentie la lazy load ca printeaza tot in string
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // NU pe entitate (citam pe Vlad Mihalcea) ca ia toate campurile
//@Builder
class Customer {
   @Getter
   @Include
   private Long id;
   @Getter
   @Setter
   @ToString.Include
   private String name;
//   @Setter
   private String address;

   public Customer setAddress(String address) {
      this.address = address;
      return this;
   }
}

//@Data // by far, the most used
@Value

class Money {
  /* private final*/ BigDecimal amount; //private final datorita @Value
  /* private final */Currency currency;

//
//   public Money(BigDecimal amount, Currency currency) {
//      this.amount = amount;
//      this.currency = currency;
//   }
//
//   public BigDecimal getAmount() {
//      return amount;
//   }
//
//   public Currency getCurrency() {
//      return currency;
//   }
//
//   @Override
//   public String toString() {
//      return "Money{" +
//          "amount=" + amount +
//          ", currency=" + currency +
//          '}';
//   }
//
//   @Override
//   public boolean equals(Object o) {
//      if (this == o) return true;
//      if (o == null || getClass() != o.getClass()) return false;
//      Money money = (Money) o;
//      return Objects.equals(amount, money.amount) &&
//          Objects.equals(currency, money.currency);
//   }
//
//   @Override
//   public int hashCode() {
//      return Objects.hash(amount, currency);
//   }
}