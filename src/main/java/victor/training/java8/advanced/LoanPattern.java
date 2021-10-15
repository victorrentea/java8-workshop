package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import victor.training.java8.advanced.CSVExporter.ExportContentWriter;
import victor.training.java8.advanced.repo.UserRepo;

@RequiredArgsConstructor
@SpringBootApplication
public class LoanPattern implements CommandLineRunner {
   private final CSVExporter csvExporter;
   private final OrderContentWriter orderContentWriter;
   private final UserContentWriter userContentWriter;

   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }

   @Autowired
   private UserRepo userRepo;

   public void run(String... args) throws Exception {

      csvExporter.exportFile("orders", orderContentWriter::writeContent);

      System.out.println("-------------------");
      csvExporter.exportFile("users", userContentWriter::writeContent);
   }
}

