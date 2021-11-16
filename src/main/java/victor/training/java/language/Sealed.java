package victor.training.java.language;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class Sealed {

   @Test
   void test() {
      List<Filter> shapes = Arrays.asList(
          new FrFilter(10),
          new BhFilter(5),
          new FrFilter(5));

//      PerimeterCalculatorVisitor perimeterCalculator = new PerimeterCalculatorVisitor();
//      for (Shape shape : shapes) {
//         shape.accept(perimeterCalculator);
//      }
      System.out.println("Total perimeter: " + 0); // TODO

   }

}

interface Filter {
}

class BhFilter implements Filter {
   private final int radius;

   public BhFilter(int radius) {
      this.radius = radius;
   }

   public int getRadius() {
      return radius;
   }
}
class DnFilter implements Filter {
   private final int radius;

   public DnFilter(int radius) {
      this.radius = radius;
   }

   public int getRadius() {
      return radius;
   }
}

class FrFilter implements Filter {
   private final int edge;

   public FrFilter(int edge) {
      this.edge = edge;
   }

   public int getEdge() {
      return edge;
   }
}


// ======== HAZARD AREA: VISITOR PATTERN ==========
interface ShapeVisitor {
   void visit(FrFilter square);
   void visit(BhFilter circle);
}
class PerimeterCalculatorVisitor implements ShapeVisitor {
   private double total;

   @Override
   public void visit(FrFilter square) {
      total += 4 * square.getEdge();
   }

   @Override
   public void visit(BhFilter circle) {
      total += circle.getRadius() * 2 * Math.PI;
   }

   public double getTotal() {
      return total;
   }

}
