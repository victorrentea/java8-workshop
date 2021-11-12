package victor.training.java17;

import org.junit.jupiter.api.Test;
import victor.training.java17.Shape.Circle;
import victor.training.java17.Shape.Square;

import java.util.Arrays;
import java.util.List;

public class Sealed {

   @Test
   void test() {
      List<Shape> shapes = Arrays.asList(
          new Square(10),
          new Circle(5),
          new Square(5));

//      double totalPerimeter = shapes.stream().mapToDouble(Shape::perimeter).sum();
      double totalPerimeter = shapes.stream().mapToDouble(shape -> {

//         if (shape instanceof Circle circle) {
//            return circle.radius() * Math.PI * 2;
//         } else if (shape instanceof Square square) {
//            return square.edge() * 4;
//         } else {
//            throw new IllegalArgumentException("WTH!?");
//         }
         return switch (shape) {
            case Circle circle -> circle.radius() * Math.PI * 2;
            case Square square -> square.edge() * 4;
         };
      }).sum();

      System.out.println("Total perimeter: " + totalPerimeter); // TODO

//      Circle(radius) = method();
   }

   public Circle method() {
      return null;
   }
}
//interface Optional<> {
//    record Of(value) {}
//    record Empty {}
//}

sealed interface Shape {
   //record Rectangle(int widhth, int height) implements  Shape {
//}
   record Circle(int radius) implements Shape {
   }

   record Square(int edge) implements Shape {
   }
}

//1: shared code between subtypes.
class AsciiHelper {

}


// ======== HAZARD AREA: VISITOR PATTERN ==========
//interface ShapeVisitor {
//   void visit(Square square);
//   void visit(Circle circle);
//}
//class PerimeterCalculatorVisitor implements ShapeVisitor {
//   private double total;
//
//   @Override
//   public void visit(Square square) {
//      total += 4 * square.getEdge();
//   }
//
//   @Override
//   public void visit(Circle circle) {
//      total += circle.getRadius() * 2 * Math.PI;
//   }
//
//   public double getTotal() {
//      return total;
//   }
//
//}
