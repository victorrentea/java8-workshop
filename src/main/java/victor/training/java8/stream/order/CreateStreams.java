package victor.training.java8.stream.order;

import io.vavr.collection.Seq;
import io.vavr.control.Try;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

public class CreateStreams {


   private Consumer<List<Throwable>> errorReporter =
       list -> System.err.println("ERori: " +list);

   /**
    * - Read lines from file as Strings.
    * - Where do you close the opened file?
    * - Parse those to OrderLine using the function bellow
    * - Validate the created OrderLine. Throw ? :S
    */
  // Change request: return correct liens and report the list of lines in error to
   public List<OrderLine> p1_readOrderFromFile(File file) throws IOException {

      Reader dePeRequestHttp = new StringReader("cica de pe \nservlet\nequest");
      try (Stream<String> s = new BufferedReader(dePeRequestHttp).lines()) {
         s.forEach(System.out::println);
      }

      try (Stream<String> lines = Files.lines(file.toPath())) {
//         List<Throwable> errors = new ArrayList<>();;

         List<Try<OrderLine>> tries = lines
             .map(line -> line.split(";")) // Stream<String[]>
             .filter(cell -> "LINE".equals(cell[0]))
//             .peek(e->errors.add(err))
             .map(cells -> Try.of(() -> parseOrderLine(cells))) // Stream<OrderLine>
             .collect(toList());


         Map<Boolean, List<Try<OrderLine>>> collect = tries.stream().collect(partitioningBy(Try::isSuccess));
//         collect.get(Boolean.TRUE).

         List<Throwable> errors = tries.stream().filter(Try::isFailure).map(Try::getCause).collect(toList());
         errorReporter.accept(errors);

         return tries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
      }
      // TODO check the number of lines is >= 2

   }

   private OrderLine parseOrderLine(String[] cells) {
      OrderLine orderLine = new OrderLine(new Product(cells[1]), Integer.parseInt(cells[2]));
      if (orderLine.getCount() < 0) {
         throw new IllegalArgumentException("Negative items");
      }
      return orderLine;
   }


   public Stream<Integer> p2_createFibonacciStream() {
      return Stream.of(1, 1, 2, 3, 5);
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
