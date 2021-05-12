package victor.training.java8.advanced;


import java.util.function.BiFunction;
import java.util.function.Function;

enum MovieType {
   REGULAR(FunctionsOnEnums::computeRegularPrice),
   NEW_RELEASE(FunctionsOnEnums::computeNewReleasePrice),
   CHILDREN(FunctionsOnEnums::computeChildrenPrice);

  public final Function<Integer, Integer> priceFunc;

   MovieType(Function<Integer, Integer> priceFunc) {
      this.priceFunc = priceFunc;
   }
}

//abstract class Movie {
//   private final String name;
//   private final MovieType type;
//
//   Movie(String name, MovieType type) {
//      this.name = name;
//      this.type = type;
//   }
//   public abstract int computePrice(int days);
//}
//class RegularMovie extends Movie {
//
//   RegularMovie(String name, MovieType type) {
//      super(name, type);
//   }
//
//   @Override
//   public int computePrice(int days) {
//      return 0;
//   }
//}

public class FunctionsOnEnums {

   private int factor;

   public static void main(String[] args) {
      FunctionsOnEnums obj = new FunctionsOnEnums();
      System.out.println(obj.computePrice(MovieType.REGULAR, 2));
      System.out.println(obj.computePrice(MovieType.NEW_RELEASE, 2));


      System.out.println(obj.computePrice(MovieType.CHILDREN, 2));
   }

   public void otherFlowToTest() {

      int price = computeNewReleasePrice(2);
   }

   public int computePrice(MovieType type, int days) {
      return type.priceFunc.apply(days);
   }

//   @Inject
//   ConfigService configService;

   public static int computeNewReleasePrice(int days) {
      return days * 2 + 1;
   }

   public static int computeChildrenPrice(int days) {
      return 5;
   }

   public static int computeRegularPrice(int days) {
      return days + 1;
   }

}
