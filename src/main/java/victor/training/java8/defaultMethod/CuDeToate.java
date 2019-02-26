package victor.training.java8.defaultMethod;

import static java.util.Arrays.asList;

import java.util.List;

public class CuDeToate implements A {

	public void m() {
		System.out.println("NU INCERCATI ASTA ACASA!");
	}

	public void intrare() {
		altaMetodaCuImplementare();
	}
	public static void main(String[] args) {
		List<Integer> list = asList(1,2,3);
		list.stream();
		new CuDeToate().intrare();
	}
}

interface A {
	void m();
	default void altaMetodaCuImplementare() {
		System.out.println("DA! Cod in intefete!");
		m();
	}
}
