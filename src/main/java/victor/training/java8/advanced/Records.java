package victor.training.java8.advanced;

import javax.persistence.Entity;

public class Records {
   public static void main(String[] args) {
      System.out.println(new Interval(1, 2));
      System.out.println(new Interval(1, 2).equals(new Interval(1, 2)));

      System.out.println(new Interval(2, 1));
//      new Interval(1, 2, new B()).b().setPanica(-1);
      // immutable
      // extends
      // extra content
      // override generated methods
      // implements
      // constructor overload
   }

}

interface A {
   int start();
}

record Interval(int start, int end) implements A{
   public Interval {
      if (start > end) {
         throw new IllegalArgumentException("Start should be < End");
      }
   }

   public boolean intersects(Interval other) {
      return start <= other.end && other.start <= end;
   }
}
class B {
   private int panica;

   public B setPanica(int panica) {
      this.panica = panica;
      return this;
   }

   public int getPanica() {
      return panica;
   }
}