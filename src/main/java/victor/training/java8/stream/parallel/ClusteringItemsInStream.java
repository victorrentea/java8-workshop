package victor.training.java8.stream.parallel;

import io.vavr.collection.Seq;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ClusteringItemsInStream {
   public static void main(String[] args) {

//      Seq.of(1,2,3,4).
      List < Integer > numbers = IntStream.range(1, 25)
          .boxed()
          .collect(Collectors.toList());

//      Stream<List<Integer>> streamOfPages =
      List<List<Integer>> list = numbers.stream()
          .map(new Function<Integer, List<Integer>>() {
             List<Integer> underConstruction = new ArrayList<>();

             @Override
             public List<Integer> apply(Integer e) {
                underConstruction.add(e);
                if (underConstruction.size() == 10) {
                   List<Integer> ret = this.underConstruction;
                   underConstruction = new ArrayList<>();
                   return ret;
                }
                return null;
             }

             ;
          })
          .filter(l -> l != null)
          .collect(Collectors.toList());

      System.out.println(list);
   }
}
