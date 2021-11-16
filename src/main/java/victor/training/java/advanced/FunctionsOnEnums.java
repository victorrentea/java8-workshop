package victor.training.java.advanced;


enum MovieType {
   REGULAR, NEW_RELEASE, CHILDREN
}

public class FunctionsOnEnums {

   public static void main(String[] args) {
      System.out.println(computePrice(MovieType.REGULAR, 2));
      System.out.println(computePrice(MovieType.NEW_RELEASE, 2));
      System.out.println(computePrice(MovieType.CHILDREN, 2));
   }

   public static int computePrice(MovieType typeEnum, int days) {
      int v = switch (typeEnum) {
         case REGULAR -> days + 1;
         case NEW_RELEASE -> days * 2;
         case CHILDREN -> 5;
      };
      return v;
   }

   public static int computePrice11(MovieType type, int days) {
      switch (type) {
         case REGULAR:
            return days + 1;
         case NEW_RELEASE:
            return days * 2;
         case CHILDREN:
            return 5;
         default:
            throw new IllegalStateException("Unexpected value: " + type);
      }
   }

}
