package victor.training.java8.stream.order;

import io.vavr.control.Try;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ParseFile {
   public static void main(String[] args) throws IOException {


      // daca < 15% din linii sunt in eroare, ignora si accepta fisierul
      // >15% ==> eroare reject file

//      Try<OrderLine> trySucces = Try.of(new OrderLine());
//      Try<OrderLine> tryFailed = Try.failure(new IllegalArgumentException());

      try (Stream<String> lineStream = Files.lines(new File("test.ok.txt").toPath())) {
         List<Try<OrderLine>> tries = lineStream
             .map(line -> line.split(";")) // Stream<String[]>
             .filter(cell -> "LINE".equals(cell[0]))
             .map(ParseFile::parseOrderLine) // Stream<OrderLine>
             .map(orderLine -> Try.ofSupplier(() -> validateOrderLine(orderLine)))
             .collect(toList());

         double errorRatio = tries.stream().mapToInt(t -> t.isFailure() ? 1 : 0).average().getAsDouble();
         if (errorRatio > .15) throw new IllegalArgumentException("Prea multe erori: ");


         List<OrderLine> lines = tries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
         System.out.println(lines);
      }
   }


   private static OrderLine parseOrderLine(String[] cells) {
      return new OrderLine(new Product(cells[1]), Integer.parseInt(cells[2]));
   }

   private static OrderLine validateOrderLine(OrderLine orderLine) {
      if (orderLine.getCount() < 0) {
         throw new IllegalArgumentException("Negative items");
      }
      return orderLine;
   }
}
