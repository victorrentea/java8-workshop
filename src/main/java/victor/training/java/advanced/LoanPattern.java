package victor.training.java.advanced;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import victor.training.java.advanced.FileExporter.ContentWriter;
import victor.training.java.advanced.repo.OrderRepo;
import victor.training.java.advanced.repo.UserRepo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

//
//   public static <T> Consumer<T> wrapChecked(MyConsumer<T> function) {
//      return str -> {
//         try {
//            function.accept(str);
//         } catch (RuntimeException e) {
//            throw e;
//         } catch (Exception e) {
//            throw new RuntimeException(e);
//         }
//      };
//   }

//@FunctionalInterfacec
@Service
class FileExporter {
   @Value("${export.folder.out}")
   private File folder;

   @FunctionalInterface
   interface ContentWriter {
      void writeContentTo(Writer writer) throws IOException;
   }
   //   @Timed // micrometer >> measures and reports the ex time of this function over actuator to prometheus > grafana
   public void exportFile(String fileName, ContentWriter contentWriterFunction) {
      File file = new File(folder, fileName);
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());

         contentWriterFunction.writeContentTo(writer);

         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         System.out.println("Imagine: Send Error Notification Email");
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }

   }
}
// garbage
// -------------------- arch = the art of drawing lines
// what i care about; bugs/cr will be here
@Service
class Exports {
   @Autowired
   private OrderRepo orderRepo;

//   @SneakyThrows
   public void writeOrders(Writer writer) throws IOException {
      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
          .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
          .forEach(Unchecked.consumer(writer::write));
   }

   @Autowired
   UserRepo userRepo;

//   @SneakyThrows
   public void writeUsers(Writer writer) throws IOException {
      writer.write("username;fullname\n");
      userRepo.findAll()
          .stream().map(u -> u.getUsername() + ";" + u.getFullName() + "\n")
          .forEach(Unchecked.consumer(writer::write));
   }


}

@RequiredArgsConstructor
@SpringBootApplication
public class LoanPattern implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }

   private final FileExporter fileExporter;
   private final Exports exports;

   public void run(String... args) throws Exception {
      fileExporter.exportFile("orders.csv", exports::writeOrders);


      fileExporter.exportFile("users.csv", exports::writeUsers);


//      fileExporter.exportFile("a", cw);



      // TODO implement export of users too "the same way yo exported the orders"
   }
}

class MyException extends RuntimeException {
   MyException(ErrorCode errorCode) {
      this.errorCode = errorCode;
   }

   enum ErrorCode {
      GENERAL,
      SOMET_BAD_IN_PLACE_X
   }

   private final ErrorCode errorCode;
}