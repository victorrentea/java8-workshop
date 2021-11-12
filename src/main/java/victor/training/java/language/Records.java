package victor.training.java.language;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Records {

   public static void main(String[] args) {
      List<Integer> numbers = IntStream.range(1, 10).boxed().toList();
      Immutable obj = new Immutable("John", new Other("halo"), numbers);

      System.out.println(obj);

      unkownFierceCode(obj);

      System.out.println(obj);
   }

   private static void unkownFierceCode(Immutable obj) {
      // TODO what can go wrong here ?
   }
}


class Immutable {
   private final String name;
   private final Other other;
   private final List<Integer> list;

   public Immutable(String name, Other other, List<Integer> list) {
      this.name = name;
      this.other = other;
      this.list = list;
   }

   public String getName() {
      return name;
   }

   public Other getOther() {
      return other;
   }

   public List<Integer> getList() {
      return list;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Immutable immutable = (Immutable) o;
      return Objects.equals(name, immutable.name) && Objects.equals(other, immutable.other) && Objects.equals(list, immutable.list);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name, other, list);
   }

   @Override
   public String toString() {
      return "Immutable{" +
             "name='" + name + '\'' +
             ", other=" + other +
             ", list=" + list +
             '}';
   }
}

class Other {
   private String data;

   public Other(String data) {
      this.data = data;
   }

   public String getData() {
      return data;
   }

   public Other setData(String data) {
      this.data = data;
      return this;
   }
}