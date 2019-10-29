package victor.training.java8;

import lombok.SneakyThrows;

public class Galuste {

    public static void main(String[] args) {
        long t0 = System.currentTimeMillis();
        String a = Galusca1.m();
        String A = Galusca2.m(a);
        String a1 = Galusca3.m(a);
        String Aa1Aa1 = Galusca4.m(A + a1);
        Galusca5.m(Aa1Aa1);
        long t1 = System.currentTimeMillis();
        System.out.println(t1 - t0);
    }

}
class Galusca1 {
    @SneakyThrows
    public static String m() {
        Thread.sleep(1000);
        return "a";
    }
}
class Galusca2 {
    @SneakyThrows
    public static String m(String a) {
        Thread.sleep(1000);
        return a.toUpperCase();
    }
}
class Galusca3 {
    @SneakyThrows
    public static String m(String a) {
        Thread.sleep(1000);
        return a + "1";
    }
}
class Galusca4 {
    @SneakyThrows
    public static String m(String Aa1) {
        Thread.sleep(1000);
        return Aa1 + Aa1;
    }
}
class Galusca5 {
    @SneakyThrows
    public static void m(String Aa1Aa1) {
        Thread.sleep(1000);
        System.out.println(Aa1Aa1);
    }
}


