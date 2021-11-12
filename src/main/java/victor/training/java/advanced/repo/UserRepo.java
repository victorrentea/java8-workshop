package victor.training.java.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java.advanced.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
