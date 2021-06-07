package victor.training.java8.stream.order.entity;

import java.util.Objects;

public class Product {

	private final String name;

	public Product(String name) {
		this.name = name;
	}
	
	public final String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return Objects.equals(name, product.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return "Product{name="+name+"}";
	}
	
}
