package victor.training.java8.stream.order;

import io.vavr.control.Try;
import org.jooq.lambda.Unchecked;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Supplier;
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
      Supplier<Stream<String>> streamFactory = Unchecked.supplier(() -> Files.lines(file.toPath()));

      return processFile(streamFactory);

   }

   private List<OrderLine> processFile(Supplier<Stream<String>> streamFactory) {
      // o linie ==============================
      // sub aceasta linie nu strebuie sa stiu CUM se obt streamul de stringuri
      try (Stream<String> lines = streamFactory.get()) {
         if (lines.count() < 2) {
            throw new IllegalArgumentException();
         }
      }

      try (Stream<String> lines = streamFactory.get()) {
         List<Try<OrderLine>> allItems = lines
             .map(line -> line.split(";")) // Stream<String[]>
             .filter(cell -> "LINE".equals(cell[0]))
             .map(this::parseOrderLine) // Stream<OrderLine>
             .map(this::validateOrderLine) // Stream<Try<OrderLine>>
             .collect(toList());

         List<Throwable> throwables = allItems.stream()
             .filter(Try::isFailure)
             .map(Try::getCause)
             .collect(toList());

         if (!throwables.isEmpty()) {
            System.err.println(throwables);
         }

         return allItems.stream()
             .filter(Try::isSuccess)
             .map(Try::get)
             .collect(toList());
      }
   }

   private OrderLine parseOrderLine(String[] cells) {
      return new OrderLine(new Product(cells[1]), Integer.parseInt(cells[2]));
   }

   private Try<OrderLine> validateOrderLine(OrderLine orderLine) {

      if (orderLine.getCount() >= 0) {
         return Try.success(orderLine);
      } else {
         return Try.failure(new IllegalArgumentException("Negative items"));
      }
   }

   public Stream<Integer> p2_createFibonacciStream() {
      return Stream.iterate(new int[]{1, 1}, arr -> new int[]{arr[1], arr[0] + arr[1]})
          .map(arr -> arr[0]);
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
