package victor.training.java8.stream.order;

import io.vavr.control.Try;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class CreateStreams {


   /**
    * - Read lines from file as Strings.
    * - Where do you close the opened file?
    * - Parse those to OrderLine using the function bellow
    * - Validate the created OrderLine. Throw ? :S
    */
   public List<OrderLine> p1_readOrderFromFile(File file) throws IOException {

      try (Stream<String> lines = Files.lines(file.toPath())) {
         List<Try<OrderLine>> allResults = lines
             .map(line -> line.split(";")) // Stream<String[]>
             .filter(cell -> "LINE".equals(cell[0]))
             .map(this::parseOrderLine) // Stream<OrderLine>
             .map(this::validateOrderLine)
//             .forEach(repo::save);
//             .forEach(messageSender::);
             .collect(toList());
         System.out.println(allResults.stream()
             .filter(Try::isFailure)
             .map(Try::getCause)
             .map(Throwable::getMessage)
             .collect(Collectors.joining("\n")));
         return allResults.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
      }
   }

   private OrderLine parseOrderLine(String[] cells) {
      return new OrderLine(new Product(cells[1]), Integer.parseInt(cells[2]));
   }

   private Try<OrderLine> validateOrderLine(OrderLine orderLine) {
      return Try.ofSupplier(() -> {
         if (orderLine.getCount() < 0) {
            throw new IllegalArgumentException("Negative items");
         } else return orderLine;
      });
   }

   public Stream<Integer> p2_createFibonacciStream() {
      return Stream.of(1,1,2,3,5);
   }

   public Stream<Integer> p3_createPseudoRandomStream(int seed) {
      return Stream.of(1);
   }

   public Stream<String> p4_getAllPaths(File folder) {
      // TODO print cannonical paths of all files in given directory and subdirectories
      System.out.println("folder = " + folder.getAbsolutePath());
      return null;
   }
}
