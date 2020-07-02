package victor.training.java8.functionalpatterns;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// get the products frequently ordered during the past year


class ProductService {
	private ProductRepo productRepo;

	// at most 1 year old oders, the count of their lines, the product ordered at least 10 times.
	// Not hidden (repo)
	public List<Product> getFrequentOrderedProducts(List<Order> orders) {
		Map<Product, Integer> productCount = orders.stream()
			.filter(order -> order.getCreationDate().isAfter(LocalDate.now().minusYears(1)))
			.flatMap(order -> order.getOrderLines().stream())
			.collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));

		for (Entry<Product, Integer> productIntegerEntry : productCount.entrySet()) {

		}
		return productCount.entrySet()
				.stream()
				.filter(e -> e.getValue() >= 10)
				.map(Entry::getKey)
				.filter(p -> !productRepo.getHiddenProductIds().contains(p.getId()))
				.collect(toList());
	}
}





//VVVVVVVVV ==== supporting (dummy) code ==== VVVVVVVVV
class Product {
	private Long id;
	private boolean deleted;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}

class Order {
	private Long id;
	private List<OrderLine> orderLines;
	private LocalDate creationDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<OrderLine> getOrderLines() {
		return orderLines;
	}
	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	
}

class OrderLine {
	private Product product;
	private int itemCount;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
}

interface ProductRepo {
	List<Long> getHiddenProductIds();
}
