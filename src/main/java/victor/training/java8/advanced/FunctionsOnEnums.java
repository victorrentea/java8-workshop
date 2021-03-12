package victor.training.java8.advanced;


enum MovieType {
   REGULAR{
      public int computePrice(int days) {
         // Problema cand e multa logica
         return days + 1;
      }
   },
   NEW_RELEASE {
      public int computePrice(int days) {
         // daca factorul "2" trebuie citit dintr-un fisier de proprietati ~ @Value
         return days * 2;
      }
   },
   CHILDREN {
      public int computePrice(int days) {
         return 5;
      }
   }
   ,DE_BABACI {
      public int computePrice(int days) {
         return 1;
      }
   };
   ;
   public abstract int computePrice(int days);

}

public class FunctionsOnEnums {

   public static void main(String[] args) {
      System.out.println(computePrice(MovieType.REGULAR, 2));
      System.out.println(computePrice(MovieType.NEW_RELEASE, 2));
      System.out.println(computePrice(MovieType.CHILDREN, 2));
   }

   public static int computePrice(MovieType type, int days) {
      return type.computePrice(days);
   }


}
