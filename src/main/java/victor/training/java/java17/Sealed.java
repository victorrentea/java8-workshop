package victor.training.java.java17;

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
         totalPerimeter += shape.getPerimeter();
      }
      System.out.println("Total perimeter: " + totalPerimeter);
   }

   public static void main(String[] args) {
      for (Shape shape : shapes) {
         switch (shape) {
            case Circle circle -> DisplayShape.display(graphics -> graphics.drawOval(circle.radius(), circle.radius(), circle.radius(), circle.radius()));
            case Square square ->  DisplayShape.display(graphics -> graphics.drawRect(square.edge(), square.edge(), square.edge(), square.edge()));
         }
//         Ellipsis(radiusX,radiusY) = methodCalLreturningCircle();

//         if (shape instanceof Circle circle) {
//            DisplayShape.display(graphics -> {
//               graphics.drawOval(circle.radius(), circle.radius(), circle.radius(), circle.radius());
//            });
//         } else if (shape instanceof Square square) {
//            DisplayShape.display(graphics -> {
//               graphics.drawRect(square.edge(), square.edge(), square.edge(), square.edge());
//            });
//         }
      }
      // examples
//      DisplayShape.display(graphics -> {
//         graphics.drawRect(10, 10, 10, 30);
//      });
   }

}


sealed interface Shape permits Circle,Square{
   double getPerimeter();
}

record Circle(int radius) implements Shape {

   @Override
   public double getPerimeter() {
      return 2 * Math.PI * radius;
   }
}

record Square(int edge) implements Shape {

   @Override
   public double getPerimeter() {
      return 4 * edge;
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
