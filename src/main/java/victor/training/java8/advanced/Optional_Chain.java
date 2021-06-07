package victor.training.java8.advanced;

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
      dto.recipientPerson = parcel.getDelivery().getAddress().getContactPerson().getName().toUpperCase();
      return dto;
   }

   public DeliveryDto convertSafeJava7(Parcel parcel) {
      DeliveryDto dto = new DeliveryDto();
      if (
          parcel!=null &&
          parcel.getDelivery()!=null &&
          parcel.getDelivery().getAddress()!=null &&
          parcel.getDelivery().getAddress().getContactPerson()!=null &&
          parcel.getDelivery().getAddress().getContactPerson().getName()!=null) {
         dto.recipientPerson = parcel.getDelivery().getAddress().getContactPerson().getName().toUpperCase();
      } else {
         dto.recipientPerson = "<NOT SET>";
      }
      return dto;
   }


}

class DeliveryDto {
	public String recipientPerson;
}

class Parcel {
   private Delivery delivery; // NULL until a delivery is scheduled

   public Delivery getDelivery() {
      return delivery;
   }
	public Parcel setDelivery(Delivery delivery) {
      this.delivery = delivery;
      return this;
   }
}


class Delivery {
   private Address address; // 'NOT NULL' IN DB

   public Delivery(Address address) {
      this.address = address;
   }

	public void setAddress(Address address) {
		this.address = address; // TODO null safe
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

   public ContactPerson getContactPerson() {
      return contactPerson;
   }
}

class ContactPerson {
   private final String name; // 'NOT NULL' in DB

   public ContactPerson(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}
