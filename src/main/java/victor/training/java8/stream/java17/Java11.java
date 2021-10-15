package victor.training.java8.stream.java17;

import io.vavr.Tuple6;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

//class AiaEAdresa {
//   String, LocalDate, Integer, String, String ,Long
//}

public class Java11 {



   public static final Map<Integer, String> MAPA2 = Map.of(
       1,"unu",
       2,"doi"
   );
   public static final Map<Integer, String> MAPA = Map.ofEntries(
       entry(1, "unu"),
       entry(2, "doi"),
       entry(2, "doi"),
       entry(2, "doi"),
       entry(2, "doi"),
       entry(2, "doi"),
       entry(2, "doi"),
       entry(2, "doi"),
       entry(2, "doi"),
       entry(2, "doi"),
       entry(2, "doi"),
       entry(2, "doi"),
       entry(2, "doi"),
       entry(2, "doi"),
       entry(2, "doi")
   );

   public static void main(String[] args) {
//      Tuple6<String, LocalDate, Integer, String, String ,Long> domaneFereste;
//      domaneFereste.

//      Map<String, List<Tuple3<String,Long, Long>>>

      // NU FOLOSI (decat in teste cand sunt multe generice):
      var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
      Set.of(1,2,3,3,3);
   }
}
