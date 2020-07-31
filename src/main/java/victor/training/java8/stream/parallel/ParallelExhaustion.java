package victor.training.java8.stream.parallel;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ParallelExhaustion {
   public static void main(String[] args) {
      List<Integer> list = IntStream.range(1, 40).boxed().collect(toList());

//      Customer customer = customerRepo.findById();

      List<Object> out = list.parallelStream()
          .map(ParallelExhaustion::transformItem)
          .collect(toList());
   }

   @SneakyThrows
   private static Integer transformItem(@NonNull Integer integer) {
      Thread.sleep(100);
      return m(integer);
   }

   private static Integer m(@NonNull Integer integer) {
      return integer * 2;
   }
}

