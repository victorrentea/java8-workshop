package victor.training.java.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import victor.training.java.advanced.MyException.ErrorCode;
import victor.training.java.advanced.repo.OrderRepo;

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
   @Autowired
   private OrderRepo orderRepo;
   @Value("${export.folder.out}")
   private File folder;

//   @Timed // micrometer >> measures and reports the ex time of this function over actuator to prometheus > grafana
   public void exportFile() {
      File file = new File(folder, "orders.csv");
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());


         writer.write("OrderID;Date\n");
			orderRepo.findByActiveTrue()
				.map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
				.forEach(Unchecked.consumer(writer::write));

         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         System.out.println("Imagine: Send Error Notification Email");
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis()-t0));
      }
   }

//   @SneakyThrows
//   private void writeSafely(Writer writer, String str) {
//      try {
//         writer.write(str);
//      } catch (IOException e) {
//         throw new RuntimeException(e);
//      }
//   }
}

@RequiredArgsConstructor
@SpringBootApplication
public class LoanPattern implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }

   private final FileExporter fileExporter;
   public void run(String... args) throws Exception {
      fileExporter.exportFile();

      // TODO implement export of users too
   }
}

class MyException extends RuntimeException{
   MyException(ErrorCode errorCode) {
      this.errorCode = errorCode;
   }

   enum ErrorCode {
      GENERAL,
      SOMET_BAD_IN_PLACE_X
   }
   private final ErrorCode errorCode;
}