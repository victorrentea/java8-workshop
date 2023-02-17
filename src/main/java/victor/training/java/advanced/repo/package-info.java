//@NonNullApi
package victor.training.java.advanced.repo;

// @NotNullApi on the Spring Data Repos package will cause
// any generated repo method to return a null.

// Instead of returning raw type (eg Order getLastOrderByCustomerId(customerId))
// it will force you to return an Optional<Order> if the calling code
// is ever able to accept not finding a last order for the customer.

// With this annotation, if there is no last order:
//  Order getLastOrderByCustomerId(customerId) => throws
//  Optional<Order> getLastOrderByCustomerId(customerId) => returns Optional.empty()