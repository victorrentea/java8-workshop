package victor.training.java8.stream.java17;

import com.google.common.collect.ImmutableList;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Java17_Records {
   public static void main(String[] args) {

      List<AltRecord> list = Stream.of(1,2).map(AltRecord::new).collect(toList());

      Point point = new Point(1, 2, ImmutableList.copyOf(list));


      point.mutableList().clear();

      Point point2 = point.mutaLaDreaptaCu(2);
      System.out.println(point2);

      int x = point2.x(); // getter si setterele au contaminat codul java 25 de ani

//      PointX pointX = new PointX(1, 2);
//      pointX.set

   }
}

// Ce e aia programare functionala ?

/**
 * opt1: sa dai functii parametru ~= synctactic sugar in Java --- new Consumer<>
 * opt2: sa faci functii frumoase --- clean code
 * opt3: sa mearga codu! *functional*
 * opt5: sa folostesti cat mai mult .stream si ->
 * opt4(corecta): cat mai MULTE:  pure functions (fara side effects), immutable objects. NU SCHIMBI ci CREEZI.
 *
 * DE CE ?
 *    > paralelism multithreading (nu mai ai race bugs)
 *    > super scalabilitate:  Spark/infinispan/ ... iti iau lambda da si o trimit pe retea sa ruleze
 *    pe cele 20 se servere din cluster TB-PB date. De la rulari primesti rezultatele inapoi.
 *    > E (cica) mai usor de citit (mai SCURT)
 *    > mai putine buguri pentru ca nu ai setteri. PUNCT. Mai putin "TEMPORAL COUPLING" ascuns intre linii de cod.
 *    > (des cel mai important) e mai geek :) - tine developerii😊😊😊
 *    >>> e foarte trendy: toate limbajele din lume fac: ts,scala,kotlin,c#
 *    - NU o stii. trebuie invatat
 */

record Point(int x, int y, ImmutableList<AltRecord> mutableList) {
   public Point mutaLaDreaptaCu(int delta) {
      return new Point(x + delta, y, mutableList);
   }
}
record AltRecord(Integer index) {}

//record Point3D extends Point {}

//~ I LOVE @Value lombok, i HATE @Data

@Value
class PointX{
   int x;
   int y;
}