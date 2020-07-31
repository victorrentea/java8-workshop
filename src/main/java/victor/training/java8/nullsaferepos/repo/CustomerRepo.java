package victor.training.java8.nullsaferepos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java8.nullsaferepos.entity.Customer;

import java.util.Optional;

interface CustomerRepo extends JpaRepository<Customer, Long> {
   Customer findByName(String name);
   Optional<Customer> findByPhone(String name);
}