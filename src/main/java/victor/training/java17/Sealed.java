package victor.training.java17;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class Sealed {

   @Test
   void test() {
      List<Shape> shapes = Arrays.asList(
          new Square(10),
          new Circle(5),
          new Square(5));

      double totalPerimeter = shapes.stream().mapToDouble(Shape::perimeter).sum();


      System.out.println("Total perimeter: " + totalPerimeter); // TODO

   }

}

interface Shape {
   double perimeter();
}
record Circle(int radius) implements  Shape {
   @Override
   public double perimeter() {
      return radius * Math.PI * 2;
   }
}
record Square(int edge) implements  Shape {
   @Override
   public double perimeter() {
      return edge * 4;
   }
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
