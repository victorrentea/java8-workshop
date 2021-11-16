package victor.training.java.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.lang.Integer.parseInt;

public class BasicsRecap {
   public static void main(String[] args) {
      List<String> strings = new ArrayList<>();

      Consumer<String> f1 = strings::add;
      BiFunction<BasicsRecap, String, Integer> f2 = BasicsRecap::convert;

      MyEntity myEntity = new MyEntity();
      MyDto dto = new MyDto(myEntity); // compiles
      Function<MyEntity, MyDto> f3 = MyDto::new;


   }

   public Integer convert(String s) {
      return parseInt(s);
   }
   static class MyEntity {}
   static class MyDto {
      public MyDto(MyEntity entity) {}
   }
}
