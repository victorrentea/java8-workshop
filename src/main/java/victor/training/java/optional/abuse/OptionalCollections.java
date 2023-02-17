package victor.training.java.optional.abuse;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionalCollections {
  private static class Product {
  }

  private static class Coupon {
  }

  private static class Customer {
  }

  private interface CustomerRepo extends JpaRepository<Customer, Long> {
  }

  private CustomerRepo customerRepo;

  public List<Coupon> findApplicableCoupons(Long customerId, List<Product> products) {
    System.out.println("Retrieve customer coupons " + customerId);
    Optional<Customer> customerFound = customerRepo.findById(customerId);
    if (customerFound.isEmpty()) {
      return List.of();
    }
    List<Coupon> coupons = new ArrayList<>();
    System.out.println("Filter out not applicable coupons from " + coupons);
    return coupons;
  }

  public void caller(Long customerId) {
    List<Product> products = List.of(new Product(), new Product());
    List<Coupon> couponsOpt = findApplicableCoupons(customerId, products);

    //    if (couponsOpt.isPresent()) {
    for (Coupon coupon : couponsOpt) {
      System.out.println("Apply coupon: " + coupon);
    }
    //      }
  }
}
