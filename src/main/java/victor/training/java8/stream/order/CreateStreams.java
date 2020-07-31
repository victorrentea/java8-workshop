package victor.training.java8.stream.order;

import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
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
         return lines
             .map(line -> line.split(";")) // Stream<String[]>
             .filter(cell -> "LINE".equals(cell[0]))
             .map(this::parseOrderLine) // Stream<OrderLine>
             .peek(this::validateOrderLine)
//             .forEach(repo::save);
//             .forEach(messageSender::);
             .collect(toList());
      }
   }

   private OrderLine parseOrderLine(String[] cells) {
      return new OrderLine(new Product(cells[1]), Integer.parseInt(cells[2]));
   }

   private void validateOrderLine(OrderLine orderLine) {
      if (orderLine.getCount() < 0) {
         throw new IllegalArgumentException("Negative items");
      }
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
