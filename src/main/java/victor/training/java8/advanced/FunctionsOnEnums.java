package victor.training.java8.advanced;


enum MovieType {
   REGULAR, NEW_RELEASE, CHILDREN,
   DE_BABACI
}






























public class FunctionsOnEnums {

   public static void main(String[] args) {
      System.out.println(computePrice(MovieType.REGULAR, 2));
      System.out.println(computePrice(MovieType.NEW_RELEASE, 2));
      System.out.println(computePrice(MovieType.CHILDREN, 2));
   }

   public static int computePrice(MovieType type, int days) {
      switch (type) {
         case REGULAR:
            return days + 1;
         case NEW_RELEASE:
            return days * 2;
         case CHILDREN:
            return 5;
      }
      return 0; // Oups! Free movies!!
   }

}
