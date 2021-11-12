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

//      PerimeterCalculatorVisitor perimeterCalculator = new PerimeterCalculatorVisitor();
//      for (Shape shape : shapes) {
//         shape.accept(perimeterCalculator);
//      }
      System.out.println("Total perimeter: " + 0); // TODO

   }

}

interface Shape {
}

class Circle implements Shape {
   private final int radius;

   public Circle(int radius) {
      this.radius = radius;
   }

   public int getRadius() {
      return radius;
   }
}

class Square implements Shape {
   private final int edge;

   public Square(int edge) {
      this.edge = edge;
   }

   public int getEdge() {
      return edge;
   }
}


// ======== HAZARD AREA: VISITOR PATTERN ==========
interface ShapeVisitor {
   void visit(Square square);
   void visit(Circle circle);
}
class PerimeterCalculatorVisitor implements ShapeVisitor {
   private double total;

   @Override
   public void visit(Square square) {
      total += 4 * square.getEdge();
   }

   @Override
   public void visit(Circle circle) {
      total += circle.getRadius() * 2 * Math.PI;
   }

   public double getTotal() {
      return total;
   }

}
