package victor.training.java8;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class Galuste {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long t0 = System.currentTimeMillis();


        CompletableFuture<String> futureA = supplyAsync(Galusca1::m);

//        String a = Galusca1.m();
        String a = futureA.get();


        String A = Galusca2.m(a);
        String a1 = Galusca3.m(a);
        String Aa1Aa1 = Galusca4.m(A + a1);
        Galusca5.m(Aa1Aa1);
        long t1 = System.currentTimeMillis();
        System.out.println(t1 - t0);
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
        System.out.println(Aa1Aa1);
    }
}


