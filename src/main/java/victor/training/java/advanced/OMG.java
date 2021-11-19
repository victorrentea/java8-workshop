package victor.training.java.advanced;

import io.vavr.Function0;
import io.vavr.Function3;
import org.jooq.lambda.function.Function13;

import java.util.Date;
import java.util.function.Function;
import java.util.function.Supplier;

public class OMG {
   public static void main(String[] args) {
      Function3<String,String,String,Integer> f;
//      BiFunction

      Supplier<Date> s = Date::new; // target typing
      Function0<Date> s2 = Date::new; // target typing

      // javac needs to know what type you expect
//      Object never = Date::new;
//      Object whatDoYouWant = () -> new Date();

      Function<Long, Date> s3 = Date::new;



   }
}
