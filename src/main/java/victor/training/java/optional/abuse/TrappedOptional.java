package victor.training.java.optional.abuse;

import lombok.Data;

import java.util.Optional;

public class TrappedOptional {
  public void kafkaSend(String personName) {
  }
  static class MyDto {
    public String recipientPerson;
  }
  @Data
  static class MyEntity {
    private String recipient;
  }

  public void trappedOptional(MyDto dto) {
    MyEntity entity = new MyEntity();

    Optional.ofNullable(dto.recipientPerson) // daca recipientPerson==null=> Optional.empty() : Optional.of(recipientPerson)
            .map(String::toUpperCase)
            .ifPresent(entity::setRecipient);

    if (dto.recipientPerson != null) {
      entity.setRecipient(dto.recipientPerson.toUpperCase());
    }

  }

  public void trappedOptionalWithExternalSideEffect(MyDto dto) {
    Optional.ofNullable(dto.recipientPerson)
            .map(String::toUpperCase)
            .ifPresent(this::kafkaSend);

    if (dto.recipientPerson != null) {
      kafkaSend(dto.recipientPerson.toUpperCase());
    }
  }
}
