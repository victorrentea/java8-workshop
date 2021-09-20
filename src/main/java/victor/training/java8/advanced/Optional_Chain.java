package victor.training.java8.advanced;

import io.vavr.control.Try;
import victor.training.java8.advanced.MyMapper.*;

import java.util.Objects;
import java.util.Optional;

public class Optional_Chain {
	static MyMapper mapper = new MyMapper();
   public static void main(String[] args) {
		Parcel parcel = new Parcel()
          .setDelivery(new Delivery(new Address().setContactPerson(new ContactPerson(null))));

		DeliveryDto dto = mapper.convert(parcel);
      System.out.println(dto);
   }
}

class MyMapper {
   public DeliveryDto convert(Parcel parcel) {
      DeliveryDto dto = new DeliveryDto();
      // the null pyramid
//      if (
//          parcel != null &&
//          parcel.getDelivery() != null &&
//          parcel.getDelivery().getAddress() != null &&
//          parcel.getDelivery().getAddress().getContactPerson() != null &&
//          parcel.getDelivery().getAddress().getContactPerson().getName() != null
//      ) {


//      if (parcel.getDelivery().getId() < 0) {
//      if (parcel.getDelivery() == Delivery.NOT_SCHEDULED) {
//      }
//      if (parcel.getDelivery().isEmpty()) {
//         System.out.println("Send email");
//      }

      // faptul ca are .flatMap face ca Optional, Stream, Try sa fie MONADE !
      dto.recipientPerson = parcel.getDelivery()
          .flatMap(d -> d.getAddress().getContactPerson())
          .map(person -> person.getName().toUpperCase())

          // Opt2:
//          .map(ContactPerson::getName)
//          .map(String::toUpperCase)

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

   public Delivery(Address address) {
     setAddress(address);
   }

	public void setAddress(Address address) {
//      if (address == null) {
//         throw new IllegalArgumentException();
//      }
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
//   public static final ContactPerson NO_PERSON = new ContactPerson("undefined");
   private final String name; // 'NOT NULL' in DB

   public ContactPerson(String name) {
      this.name = Objects.requireNonNull(name);
   }

   public String getName() {
      return name;
   }
}


class ContactPersonValidator {
   public void validate(ContactPerson person) {

   }
}