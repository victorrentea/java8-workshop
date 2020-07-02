package victor.training.java8.functionalpatterns;

import lombok.Data;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

// get the products frequently ordered during the past year


class ProductService {
	private ProductRepo productRepo;

	// at most 1 year old oders, the count of their lines, the product ordered at least 10 times.
	// Not hidden (repo)
	public List<Product> getFrequentOrderedProducts(List<Order> orders) {
		List<Long> hiddenProductIds = productRepo.getHiddenProductIds();
		return getProductCounts(orders).stream()
				.filter(e -> e.getTotalOrderCount() >= 10) // ar fi si mai sugestiv aici sa folosesti ProductOrderHistory
				.map(ProductOrderHistory::getProduct)
				.filter(p -> !hiddenProductIds.contains(p.getId()))
				.collect(toList());
	}

	private List<ProductOrderHistory> getProductCounts(List<Order> orders) {
		return orders.stream()
			.filter(this::isRecent)
			.flatMap(order -> order.getOrderLines().stream())
			.collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)))
			.entrySet()
			.stream()
			.map(e -> new ProductOrderHistory(e.getKey(), e.getValue()))
			.collect(toList());
		// TODO exercitiu cititorului: faceti ProductOrderHistory.totalOrderCount sa fie nefinal. si incercati sa colectati direct in instante de-astea: nu intr-un Map.
	}

	private boolean isRecent(Order order) {
		return order.getCreationDate().isAfter(LocalDate.now().minusYears(1));
	}
}

@Data
class ProductOrderHistory {
	private final Product product;
	private final int totalOrderCount;
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
