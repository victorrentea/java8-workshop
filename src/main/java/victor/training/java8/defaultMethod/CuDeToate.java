package victor.training.java8.defaultMethod;

import static java.util.Arrays.asList;

import java.util.List;

public class CuDeToate implements A,B {
	public void m() {
		System.out.println("NU INCERCATI ASTA ACASA!");
	}
	public void intrare() {
		altaMetodaCuImplementare();
	}
	public void altaMetodaCuImplementare() {
		A.super.altaMetodaCuImplementare(); // porceala!
		// alegi catre care delegi, A sau B.
	}
	public static void main(String[] args) {
		List<Integer> list = asList(1,2,3);
		list.stream();
		new CuDeToate().intrare();
	}
}
class D extends CuDeToate implements A {
	// prefera metoda din superclasa in fata interfetei
}
interface A {
	void m();
	default void altaMetodaCuImplementare() {
		System.out.println("DA! Cod in intefete!");
		m();
	}
}
interface B {
	default void altaMetodaCuImplementare() {
		System.out.println("DA! Cod in intefata B!");
	}
}