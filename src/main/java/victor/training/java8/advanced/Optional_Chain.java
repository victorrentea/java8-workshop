package victor.training.java8.advanced;

import java.util.Objects;
import java.util.Optional;

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
          .map(cp -> cp.getName().toUpperCase())
          .orElse("NOT SET");

//      dto.recipientPerson = parcel.getDelivery()
//          .map(Delivery::getAddress)
//          .flatMap(Address::getContactPerson)
//          .map(ContactPerson::getName)
//          .map(String::toUpperCase)
//          .orElse("NOT SET");
      return dto;
   }


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

   public Delivery(Address address) {
      setAddress(address);
   }

	public void setAddress(Address address) {
      this.address = Objects.requireNonNull(address); // TODO null safe
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

class ContactPerson {
   private final String name; // 'NOT NULL' in DB

   public ContactPerson(String name) {
      this.name = Objects.requireNonNull(name);
   }

   public String getName() {
      return name;
   }
}
