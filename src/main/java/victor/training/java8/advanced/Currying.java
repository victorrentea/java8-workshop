package victor.training.java8.advanced;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Currying {

   public static void main(String[] args) {

      Function<Integer, Integer> f10 = partialApply(Integer::sum, 10);

      System.out.println(f10.apply(2));

   }

   private static  <T1, T2, R>  Function<T2, R> partialApply(BiFunction<T1, T2, R> f, T1 arg0) {
      return arg1 -> f.apply(arg0, arg1);
   }

}
