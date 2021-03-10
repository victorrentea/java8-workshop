package victor.training.java8.advanced;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
class PriceCalculators {
   @Value("${extra.factor}")
   int factor = 2;


   public int computePrice(MovieType type, int days) {
      return type.getPriceAlgo().apply(this, days);
   }
   public int computeRegularMoviePrice(int days) {
      return days + 1;
   }
   public int computeNewReleaseMoviePrice(int days) {
      return days * 2 ;
   }
   public int computeChildrenMoviePrice(int days) {
      return 5;
   }
   public int computEldersMoviePrice(int days) {
      return 5;
   }
}

enum MovieType {
   REGULAR(PriceCalculators::computeRegularMoviePrice),
   NEW_RELEASE(PriceCalculators::computeNewReleaseMoviePrice),
   CHILDREN(PriceCalculators::computeChildrenMoviePrice),
   ELDERS(PriceCalculators::computEldersMoviePrice)
   ;

   private final BiFunction<PriceCalculators, Integer, Integer> priceAlgo;

   MovieType(BiFunction<PriceCalculators, Integer, Integer> priceAlgo) {
      this.priceAlgo = priceAlgo;
   }

   public BiFunction<PriceCalculators, Integer, Integer> getPriceAlgo() {
      return priceAlgo;
   }
}

public class FunctionsOnEnums {

   public static void main(String[] args) {
      PriceCalculators priceCalculators = new PriceCalculators(); // inject from Spring
      System.out.println(priceCalculators.computePrice(MovieType.REGULAR, 2));
      System.out.println(priceCalculators.computePrice(MovieType.NEW_RELEASE, 2));
      System.out.println(priceCalculators.computePrice(MovieType.CHILDREN, 2));
   }


}
