package victor.training.java8.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java8.advanced.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
