package victor.training.java8.stream.menu;

import java.util.Arrays;
import java.util.List;

public class DishPlay {
   public static final List<Dish> menu = Arrays.asList(
       new Dish("pork", false, 800, Dish.Type.MEAT),
       new Dish("beef", false, 700, Dish.Type.MEAT),
       new Dish("chicken", false, 400, Dish.Type.MEAT),
       new Dish("french fries", true, 530, Dish.Type.OTHER),
       new Dish("rice", true, 350, Dish.Type.OTHER),
       new Dish("season fruit", true, 120, Dish.Type.OTHER),
       new Dish("pizza", true, 550, Dish.Type.OTHER),
       new Dish("prawns", false, 300, Dish.Type.FISH),
       new Dish("salmon", false, 450, Dish.Type.FISH));

   public static void main(String[] args) {
      // The above code sample + problems are from Java 8 in Action book (Manning)

      // TODO select the low-calories (<400) items

      // TODO find out three high-calorie dish names

      // TODO find out the 2nd and 3rd most caloric items

      // TODO find vegetarian dishes

      // TODO find 2 dishes with meat

      // TODO Map<Type, Map<Object, List<Dish>>> dishesByTypeAndCaloricLevel

      // TODO Map<Dish.Type, Long> typesCount

      // TODO Map<Dish.Type, Set<Dish>> groupedAsSets

      // TODO Map<Dish.Type, Integer> totalCaloriesByType

      // TODO Map<Dish.Type, Optional<Dish>> mostCaloricByType
   }
}
