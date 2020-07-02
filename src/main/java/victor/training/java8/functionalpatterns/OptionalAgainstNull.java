package victor.training.java8.functionalpatterns;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class OptionalAgainstNull {
    public static void main(String[] args) {
        System.out.println(convertToName(new A(new B(new C("Gigel")))));
        System.out.println(convertToName(new A(new B())));
        System.out.println(convertToName(new A()));
    }

    public static String convertToName(A a) {
        return a.getBOpt().flatMap(B::getCOpt).map(C::getName).orElse("X");
    }
}

class A {
    private final B b;
    public A(B b) {
        this.b = b;
    }
    public A() {
        this(null);
    }

    // Law of Demeter ar zice sa creezi metode ca asta in loc sa navighezi din exterior modelul - OOP
    // Insa, practica din 90% din codul enterprise zice ca da-i cu "."
    public Optional<String> getCName() {
        return b.getCName();
    }
    public Optional<B> getBOpt() {
        return ofNullable(b);
    }
}
class B {
    private final C c;

    public B(C c) {
        this.c = c;
    }
    public B() {
        this(null);
    }

    // ies prea multe delegate functions ca asta care 'polueaza entitatile'
    public Optional<String> getCName() {
        return getCOpt().map(C::getName);
    }

    public Optional<C> getCOpt() {
        return ofNullable(c);
    }
}
class C {
    private final String name;

    public C(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
