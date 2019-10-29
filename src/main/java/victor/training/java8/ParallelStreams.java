package victor.training.java8;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ParallelStreams {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

//        List<Integer> list2 =
        list.parallelStream()
                .filter(n -> {
                    log.debug("Filtrez " + n); // n-ai voie sa faci nimic IO daca rulezi pe common pool. nici macar log. FRATE!
                    // DACA.
                    return n % 2 == 1;
                })
                .distinct()
                .map(n -> {
                    log.debug("La Patrat " + n);
                    return n * n;
                })
                .forEachOrdered(n -> log.debug("Vad: " + n));
//                .forEach(n -> log.debug("Vad: " + n));
//                .collect(Collectors.toList());

//        System.out.println(list2);
    }
}
