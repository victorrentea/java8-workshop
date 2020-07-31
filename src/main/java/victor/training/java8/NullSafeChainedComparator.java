package victor.training.java8;

import io.vavr.Tuple3;
import org.jooq.lambda.Seq;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public class NullSafeChainedComparator {
   public static void main(String[] args) {

      List<Tuple3<String, String ,String>> entries = Arrays.asList(
        new Tuple3<>(null, "a", "b"),
        new Tuple3<>("x", "a", "b"),
        new Tuple3<>(null, null, "z")
      );


      List<String> asList = Arrays.asList("a","b","c");
      List<String> asList2 = Arrays.asList("a","b","d");

      Seq<Optional<String>> se1 = Seq.of(of("a"), of("b"), of("c"));
      Seq<Optional<String>> se2 = Seq.of(of("a"), Optional.empty(), of("z"));
      System.out.println(se1.zip(se2).map(t -> t.v1.orElse("").compareTo(t.v2.orElse(""))).skipWhile(c -> c==0).get(0).orElse(0));

   }
}
class Trio {
   private final String a,b,c;


   Trio(String a, String b, String c) {
      this.a = a;
      this.b = b;
      this.c = c;
   }

   public Optional<String> getA() {
      return ofNullable(a);
   }

   public Optional<String> getB() {
      return ofNullable(b);
   }

   public Optional<String> getC() {
      return ofNullable(c);
   }
}
