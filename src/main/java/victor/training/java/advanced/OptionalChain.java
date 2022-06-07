package victor.training.java.advanced;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.Optional;

public class OptionalChain {
    static MyMapper mapper = new MyMapper();

    public static void main(String[] args) {
        Parcel parcel = new Parcel()
                .setDelivery(new Delivery(null));

        DeliveryDto dto = mapper.convert(parcel);
        System.out.println(dto);
    }
}

class MyMapper {
    public DeliveryDto convert(Parcel parcel) {
        DeliveryDto dto = new DeliveryDto();
        // Game: how many NPE can occur below ?
//        dto.recipientPerson = parcel.getDelivery().getAddress().getContactPerson().getName().toUpperCase();
       dto.recipientPerson = parcel.getDelivery()
               .flatMap(d->d.getAddress().getContactPerson())
               .map(p ->p.getName().toUpperCase())
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

//@Entity
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
       this.address = Objects.requireNonNull(address);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
       this.address = Objects.requireNonNull(address); // TODO null safe
    }
}

class Address {
    private ContactPerson contactPerson; // can be null

    public Address() {
    }

    public Optional<ContactPerson> getContactPerson() {
        return Optional.ofNullable(contactPerson);
    }

    public Address setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
        return this;
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
