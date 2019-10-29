package victor.training.java8;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

public class Galuste {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long t0 = System.currentTimeMillis();


        CompletableFuture<String> futurea = supplyAsync(Galusca1::m);

        CompletableFuture<String> futureA =  futurea.thenApplyAsync(a -> Galusca2.m(a));
        CompletableFuture<String> futurea1 =  futurea.thenApplyAsync(a -> Galusca3.m(a));
//        String a = Galusca1.m();
//        String a = futurea.get();

//        CompletableFuture.allOf(futureA,futurea1)

        futureA.thenCombineAsync(futurea1, (A,a1) -> A+a1)
            .thenApplyAsync(Aa1 -> Galusca4.m(Aa1))
            .thenAcceptAsync(Aa1Aa1 -> Galusca5.m(Aa1Aa1));

//        CompletableFuture<String> futureA = supplyAsync(() -> Galusca2.m(a));
//        CompletableFuture<String> futurea1 = supplyAsync(() -> Galusca3.m(a));
//
////        String A = futureA.get();
//        String a1 = futurea1.get();


//        CompletableFuture<String> futureAa1Aa1 = supplyAsync(() -> Galusca4.m(A + a1));
//        String Aa1Aa1 = futureAa1Aa1.get();

//        runAsync(() -> Galusca5.m(Aa1Aa1));

        long t1 = System.currentTimeMillis();
        System.out.println(t1 - t0);

        Thread.sleep(6000);
    }

}

@Slf4j
class Galusca1 {
    @SneakyThrows
    public static String m() {
        log.debug("Procesez");
        Thread.sleep(1000);
        return "a";
    }
}
@Slf4j
class Galusca2 {
    @SneakyThrows
    public static String m(String a) {
        log.debug("Procesez");
        Thread.sleep(1000);
        return a.toUpperCase();
    }
}
@Slf4j
class Galusca3 {
    @SneakyThrows
    public static String m(String a) {
        log.debug("Procesez");
        Thread.sleep(1000);
        return a + "1";
    }
}
@Slf4j
class Galusca4 {
    @SneakyThrows
    public static String m(String Aa1) {
        log.debug("Procesez");
        Thread.sleep(1000);
        return Aa1 + Aa1;
    }
}
@Slf4j
class Galusca5 {
    @SneakyThrows
    public static void m(String Aa1Aa1) {
        log.debug("Procesez");
        Thread.sleep(1000);
        log.debug(Aa1Aa1);
    }
}


