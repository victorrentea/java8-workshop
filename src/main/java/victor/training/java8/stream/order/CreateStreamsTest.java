package victor.training.java8.stream.order;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import victor.training.java8.stream.order.entity.OrderLine;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateStreamsTest {
   private CreateStreams service = new CreateStreams();


   @Test
   public void p1_readOrderFromFile() throws IOException {
      List<OrderLine> orderLines = service.p1_readOrderFromFile(new File("test.ok.txt"));
      assertEquals("Chair", orderLines.get(0).getProduct().getName());
      assertEquals(2, orderLines.get(0).getCount());
      assertEquals("Table", orderLines.get(1).getProduct().getName());
      assertEquals(1, orderLines.get(1).getCount());
   }

   @Test(expected = IllegalArgumentException.class)
   public void p1_readOrderFromFile_throws() throws IOException {
      service.p1_readOrderFromFile(new File("test.invalid.txt")); // look at stacktrace
      // TODO uncomment to see the exception trace :S
   }

   @Test
   public void p2_fibonacci() {
      List<Integer> actual10 = service.p2_createFibonacciStream().limit(10).collect(toList());
      List<Integer> expected10 = Arrays.asList(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
      assertEquals(expected10, actual10);
   }

   @Test
   public void p3_pseudoRandomStream() {
      Stream<Integer> randomStream = service.p3_createPseudoRandomStream(10);
      DescriptiveStatistics stats = new DescriptiveStatistics();
      randomStream.forEach(stats::addValue);
      System.out.println(stats.getVariance());
   }

   @Test
   public void p4_getAllPaths() {
      Stream<String> pathsStream = service.p4_getAllPaths(new File("src/main/java"));
      Set<String> set = pathsStream.collect(toSet());
      assertTrue(set.stream().anyMatch(p -> p.contains("/CreateStreamsTest.java")));
   }
}
