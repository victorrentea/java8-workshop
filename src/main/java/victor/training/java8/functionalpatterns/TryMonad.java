package victor.training.java8.functionalpatterns;

import org.jooq.lambda.Unchecked;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TryMonad {

   public static void main(String[] args) {


      List<String> dateStr = Arrays.asList("2020-10-11", "2020-nov-12", "2020-12-01");
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      List<Date> dates = dateStr.stream().map(Unchecked.function(sdf::parse)).collect(Collectors.toList());

   }

//   interface FunctieRea<T, R> {
//      R apply(T t) throws Throwable;
//   }


//   public static <T,R> Function<T,R> asRuntimeEx(FunctieRea<T,R> oFunctieRea) {
//      return t -> {
//         try {
//            return oFunctieRea.apply(t);
//         } catch (Throwable e) {
//            throw new RuntimeException(e);
//         }
//      };
//   }

//   @SneakyThrows
//   private static Date parseShit(SimpleDateFormat sdf, String s) {
//      return sdf.parse(s);
//   }
}
