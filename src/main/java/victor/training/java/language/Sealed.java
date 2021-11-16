package victor.training.java.language;

import org.junit.jupiter.api.Test;

import java.util.List;

public class Sealed {
   private static final List<Shape> shapes = List.of(
       new Circle(10),
       new Square(10),
       new Square(5));

   @Test
   void perimeter() {
      double totalPerimeter = 0;
      for (Shape shape : shapes) {
         // TODO
      }
      System.out.println("Total perimeter: " + totalPerimeter);
   }

   public static void main(String[] args) {
      for (Shape shape : shapes) {
         // TODO display all shapes
      }
      // examples
      DisplayShape.display(graphics -> {
         graphics.drawOval(10, 10, 10, 20);
      });
      DisplayShape.display(graphics -> {
         graphics.drawRect(10, 10, 10, 30);
      });
   }

}


interface Shape {

}

final class Circle implements Shape {
   private final int radius;

   Circle(int radius) {
      this.radius = radius;
   }

   public int radius() {
      return radius;
   }
}

final class Square implements Shape {
   private final int edge;

   Square(int edge) {
      this.edge = edge;
   }

   public int edge() {
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
      total += 4 * square.edge();
   }

   @Override
   public void visit(Circle circle) {
      total += circle.radius() * 2 * Math.PI;
   }

   public double getTotal() {
      return total;
   }

}
