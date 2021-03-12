package victor.training.java8.advanced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ImmutableObjectPlay {

   public static void main(String[] args) {
      List<String> list = new ArrayList<>();

      list.add("1");
      ImmutableObject obj = new ImmutableObject("a", new BX("aa"), list);
      obj.getList().add("Surpriza");

      System.out.println(obj);

      ImmutableObject alt = obj.withS("newS");
   }
}

class ImmutableObject {
   private final String s;
   private final BX b;
   private final List<String> list;

   public ImmutableObject(String s, BX b, List<String> list) {
      this.s = Objects.requireNonNull(s);
      this.b = b;
      this.list = list;
   }

   public List<String> getList() {
      return Collections.unmodifiableList(list);
   }

   public String getS() {
      return s;
   }

   public BX getB() {
      return b;
   }

   @Override
   public String toString() {
      return "ImmutableObject{" +
             "s='" + s + '\'' +
             ", b=" + b +
             ", list=" + list +
             '}';
   }

   public ImmutableObject withS(String newS) {
      return new ImmutableObject(newS, b, list);
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