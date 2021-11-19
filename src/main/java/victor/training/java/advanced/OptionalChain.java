package victor.training.java.advanced;

import com.google.common.base.Preconditions;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.Optional;

public class OptionalChain {

	static MyMapper mapper = new MyMapper();
   public static void main(String[] args) {
		Parcel parcel = new Parcel()
          .setDelivery(new Delivery(null));

      Address address = method();
      if (address != null) {
         address.getContactPerson();
      }

      DeliveryDto dto = mapper.convert(parcel);
      System.out.println(dto);
   }

//   @NonNull
   @Nullable
   private static Address method() {
      return null;
   }
}

class MyMapper {
   public DeliveryDto convert(Parcel parcel) {
      DeliveryDto dto = new DeliveryDto();

//      Preconditions.nul

      dto.recipientPerson = parcel.getDelivery()
          .flatMap(d -> d.getAddress().getContactPerson())
          .map(person ->person.getName().toUpperCase())
          .orElse(null);
      return dto;
   }




}

class DeliveryDto {
	public String recipientPerson;
}

class Parcel {
   private Delivery delivery; // NULL until a delivery is scheduled

   public Optional<Delivery> getDelivery() {
      return Optional.ofNullable(delivery);
   }

	public Parcel setDelivery(Delivery delivery) {
      this.delivery = delivery;
      return this;
   }
}


class Delivery {
   private Address address; // 'NOT NULL' IN DB
//   public Delivery(@lombok.NotNull Address address) {


   // jetbrain/spring/javax.annotation.Nonnull (findbugs)
   public Delivery(@NonNull Address address) {
//      this.address = address;
      this.address = Objects.requireNonNull(address); //< 99% of people
   }


	public Address getAddress() {
      return address;
   }
}

class Address {
   private ContactPerson contactPerson; // can be null if compnay

   public Address() {
   }

   public Address setContactPerson(ContactPerson contactPerson) {
      this.contactPerson = contactPerson;
      return this;
   }

   public Optional<ContactPerson> getContactPerson() {
      return Optional.ofNullable(contactPerson);
   }
}

class ContactPerson {
   private final String name; // 'NOT NULL' in DB

   public ContactPerson(String name) {
      this.name = Objects.requireNonNull(name);
   }

   public String getName() {
      return name;
   }
}
