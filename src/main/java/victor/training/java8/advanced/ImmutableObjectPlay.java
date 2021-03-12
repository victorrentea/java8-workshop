package victor.training.java8.advanced;

import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.unmodifiableList;

public class ImmutableObjectPlay {

   public static void main(String[] args) {
      List<String> list = new ArrayList<>();

      list.add("1");
      ImmutableObject obj = new ImmutableObject("a", new BX("aa"), list);
//      obj.getList().add("Surpriza");

      System.out.println(obj);

      ImmutableObject alt = obj.withS("newS");
   }
}

@Value
class ImmutableObject {
   @NonNull
   @With
   String s;
   BX b;
   List<String> list;

   public List<String> getList() {
      return unmodifiableList(list);
   }
}

class BX {
   private final String name;

   BX(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}