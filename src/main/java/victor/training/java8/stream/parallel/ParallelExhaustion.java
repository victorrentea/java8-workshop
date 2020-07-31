package victor.training.java8.stream.parallel;

import lombok.SneakyThrows;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ParallelExhaustion {
   public static void main(String[] args) {
      List<Integer> list = IntStream.range(1, 40).boxed().collect(toList());

      List<Object> out = list.parallelStream()
          .map(ParallelExhaustion::transformItem)
          .collect(toList());
   }

   @SneakyThrows
   private static Integer transformItem(Integer integer) {
      Thread.sleep(100);
      return integer * 2;
   }
}
