package victor.training.java8.advanced;

import lombok.NonNull;

import javax.persistence.Embeddable;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class Optional_Chain {
	static MyMapper mapper = new MyMapper();
   public static void main(String[] args) {
		Parcel parcel = new Parcel()
          .setDelivery(new Delivery(new Address().setContactPerson(new ContactPerson("John"))));

		DeliveryDto dto = mapper.convert(parcel);
      System.out.println(dto);
   }
}

class MyMapper {
   public DeliveryDto convert(Parcel parcel) {
      DeliveryDto dto = new DeliveryDto();
      dto.recipientPerson = parcel.getDelivery()
          .flatMap(delivery -> delivery.getAddress().getContactPerson())
          .map(contactPerson -> contactPerson.getName().toUpperCase())
          .orElse("NOT SET");
      return dto;
   }

//   public DeliveryDto convertSafeJava7(Parcel parcel) {
//      DeliveryDto dto = new DeliveryDto();
//      if (
//          parcel != null &&
//          parcel.getDelivery() != null &&
//          parcel.getDelivery().getAddress() != null &&
//          parcel.getDelivery().getAddress().getContactPerson() != null &&
//          parcel.getDelivery().getAddress().getContactPerson().getName() != null
//      )
//         dto.recipientPerson = parcel.getDelivery().getAddress().getContactPerson().getName().toUpperCase();
//       else
//         dto.recipientPerson = "NOT SET";
//      return dto;
//   }


}

class DeliveryDto {
	public String recipientPerson;
}

class Parcel {
   private Delivery delivery; // NULL until a delivery is scheduled

   public Optional<Delivery> getDelivery() {
      return ofNullable(delivery);
   }
	public Parcel setDelivery(Delivery delivery) {
      this.delivery = delivery;
      return this;
   }
}


class Delivery {
   private Address address; // 'NOT NULL' IN DB

   public Delivery(@NonNull Address address) {
      setAddress(address);
   }

	public void setAddress(@NonNull Address address) {
      this.address = address;
	}

	public Address getAddress() {
      return address;
   }
}

class Address {
   private ContactPerson contactPerson; // can be null

   public Address() {
   }

   public Address setContactPerson(ContactPerson contactPerson) {
      this.contactPerson = contactPerson;
      return this;
   }

   public Optional<ContactPerson> getContactPerson() {
      return ofNullable(contactPerson);
   }
}

@Embeddable
class ContactPerson {
   private String name; // 'NOT NULL' in DB

   private ContactPerson() {} // just for hibernate
   public ContactPerson(String name) {
      this.name = requireNonNull(name);
   }

   public String getName() {
      return name;
   }
}
