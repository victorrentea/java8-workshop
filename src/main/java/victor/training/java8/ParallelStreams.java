package victor.training.java8;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ParallelStreams {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Stream<Integer> integerStream = list.parallelStream()
                .filter(n -> {
                    log.debug("Filtrez " + n); // n-ai voie sa faci nimic IO daca rulezi pe common pool. nici macar log. FRATE!
                    // DACA.
                    return n % 2 == 1;
                })
                .distinct()
                .map(n -> {
                    log.debug("La Patrat " + n);
                    return n * n;
                });

        ForkJoinPool pool = new ForkJoinPool(40);
        Future<List<Integer>> rez =  pool.submit(() -> integerStream.collect(Collectors.toList()));
//                .forEachOrdered(n -> log.debug("Vad: " + n))
//                .forEach(n -> log.debug("Vad: " + n));

        System.out.println(rez.get());

        pool.shutdown();
//        System.out.println(list2);
    }
}
