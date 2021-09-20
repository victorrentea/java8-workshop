package victor.training.java8.advanced;

import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import victor.training.java8.advanced.repo.UserRepo;

import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class UserContentWriter {
   @Autowired
   private UserRepo userRepo;

   @SneakyThrows
   public void writeContent(Writer writer) {
      writer.write("Username;fullname\n");
      List<String> lines = userRepo.findAll().stream()
          .map(user -> user.getUsername() + ";" + user.getFullName() + "\n")
          .collect(Collectors.toList());
      lines.forEach(Unchecked.consumer(writer::write));
   }
}
