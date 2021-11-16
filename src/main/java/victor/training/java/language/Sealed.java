package victor.training.java.language;

import org.junit.jupiter.api.Test;
import victor.training.java.language.Filter.BhFilter;
import victor.training.java.language.Filter.DnFilter;
import victor.training.java.language.Filter.FrFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class Sealed {

   @Test
   void test() {
      List<Filter> filters = Arrays.asList(
          new DnFilter(10),
          new FrFilter(10),
          new BhFilter(5),
          new FrFilter(5));


      List<String> jaxbFilters = new ArrayList<>();
      for (Filter filter : filters) {
         jaxbFilters.add(toJaxkFilter(filter));
      }

      System.out.println("Total perimeter: " + 0); // TODO
   }

   private String toJaxkFilter(Filter filter) { // NOT OOP. but you might have good reasons.
      return  switch (filter) {
         case DnFilter dn -> "DN " + dn.radius();
         case FrFilter fr -> "FR " + fr.edge();
         case BhFilter bh -> "BH " + bh.radius();
      };
   }

   @Test
   void handle11Test() throws InstantiationException, IllegalAccessException {
      List<Class<?>> filterSubtypes = List.of(DnFilter.class, FrFilter.class, BhFilter.class); // TODO  // hard to find all subtypes of the Filter interface: https://stackoverflow.com/questions/492184/how-do-you-find-all-subclasses-of-a-given-class-in-java
      for (Class<?> filterClass : filterSubtypes) {
         try {
            handle11((Filter) filterClass.newInstance());
         } catch (MySpecialCaseNotFoundException e) {
            fail("subtype not supported : " +filterClass);
         } catch(Exception whatever ) {
            // ignore
         }
      }
   }

   public String handle11(Filter filter) {
      if (filter instanceof DnFilter) {
         DnFilter dn = (DnFilter) filter;
         return  "DN " + dn.radius();
      }
      if (filter instanceof FrFilter) {
         FrFilter fr = (FrFilter) filter;
         return  "FR " + fr.edge();
      }
      if (filter instanceof BhFilter) {
         BhFilter bh = (BhFilter) filter;
         return  "BH " + bh.radius();
      }
      throw new MySpecialCaseNotFoundException();
   }

}

class MySpecialCaseNotFoundException extends RuntimeException {
   
}

//java21 sep 2022
sealed interface Filter {
   record BhFilter(int radius) implements Filter {
   }

   record DnFilter(int radius) implements Filter {
   }

   record FrFilter(int edge) implements Filter {
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
      total += 4 * square.edge();
   }

   @Override
   public void visit(BhFilter circle) {
      total += circle.radius() * 2 * Math.PI;
   }

   public double getTotal() {
      return total;
   }

}
