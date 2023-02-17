package victor.training.java.stream.order;

import java.util.stream.Stream;

public class Fib {
  public static void main(String[] args) {
    Stream.iterate( new int[]{0,1}, arr -> new int[]{ arr[1], arr[0]+arr[1]} )
            .map(arr->arr[0])
            .limit(100)
            .forEach(System.out::println);
  }
}
