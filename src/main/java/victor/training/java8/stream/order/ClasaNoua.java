package victor.training.java8.stream.order;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ClasaNoua {
   public static void main(String[] args) {
      List<String> elems = IntStream.range(1, 10).mapToObj(n -> "Elem " + n).collect(toList());


      elems.forEach(System.out::println);
   }
}
