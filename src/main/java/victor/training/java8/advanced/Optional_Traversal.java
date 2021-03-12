package victor.training.java8.advanced;

import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class Optional_Traversal {
   public static void main(String[] args) {
      System.out.println(convertToName(new A(new B(new C("Gigel")))));
      System.out.println(convertToName(new A(new B())));
      System.out.println(convertToName(new A(null)));
   }

   public static String convertToName(/*@NonNull*/ A a) {

      // '2000 style
//      if (a != null &&
//          a.getB() != null &&
//          a.getB().getC() != null) {
//
//         return a.getB().getC().getName();
//      } else {
//         return "";
//      }

      return a.getB().getC()
          .flatMap(C::getName)
          .map(String::toUpperCase)
          .orElse("")
          ;
   }

}

class A {
   private B b;

   public A(B b) {
      this.b = Objects.requireNonNull(b);
   }

   public B getB() {
      return b;
   }
}

class B {
   private final C c;

   public B(C c) {
      this.c = c;
   }

   public B() {
      this(null);
   }

   public Optional<C> getC() {
      return ofNullable(c);
   }
}

class C {
   private final String name;

   public C(String name) {
      this.name = name;
   }

   public Optional<String> getName() {
      return Optional.ofNullable(name);
   }
}
