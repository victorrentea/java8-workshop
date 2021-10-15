package victor.training.java8.stream.java17;

import victor.training.java8.stream.java17.Node.Literal;
import victor.training.java8.stream.java17.Node.Plus;

public class Java23_Sealed {

   public static void main(String[] args) {
      System.out.println(print(new Plus(new Literal("a"), new Literal("b"))));

   }

   public static String print(Node node) {
      return switch (node) {
         case Plus plus -> print(plus.left()) + "+" + print(plus.right());
         case Literal literal -> literal.text();
      };
   }
}


sealed interface Node {
   record Plus(Node left, Node right) implements Node {}
   record Literal(String text) implements Node {}
}