package victor.training.java8.advanced;

import java.util.Optional;

public class NuASA {


   public static void main(String[] args) {


   }

   public String method(String uneoriNull) {

//      String asaNU = Optional.ofNullable(uneoriNull).map(s -> "Prefix: " + s).orElse("");

      if (uneoriNull != null) {
         return "Prefix: " + uneoriNull;
      }
      return "";

   }
}
