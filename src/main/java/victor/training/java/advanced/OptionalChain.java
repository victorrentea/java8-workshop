package victor.training.java.advanced;

import java.util.Objects;
import java.util.Optional;

public class OptionalChain {
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
      // Game: how many NPE can you spot below ?
      dto.recipientPerson = parcel.getDelivery()
              .flatMap(d -> d.getAddress().getContactPerson())
              .map(cp -> cp.getName().toUpperCase())
              .orElse(null);
      return dto;
   }

//   public DeliveryDto pyramidOfNull(Parcel parcel) {
//      DeliveryDto dto = new DeliveryDto();
//      if (
//          parcel!=null &&
//          parcel.getDelivery()!=null &&
//          parcel.getDelivery().getAddress()!=null &&
//          parcel.getDelivery().getAddress().getContactPerson()!=null &&
//          parcel.getDelivery().getAddress().getContactPerson().getName()!=null) { // null terror
//         dto.recipientPerson = parcel.getDelivery().getAddress().getContactPerson().getName().toUpperCase();
//      } else {
//         dto.recipientPerson = "<NOT SET>";
//      }
//      return dto;
//   }


}

class DeliveryDto {
	public String recipientPerson;
}

class Parcel {
   private Delivery delivery;

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

   public Delivery(Address address) {
      this.address = Objects.requireNonNull(address);
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
