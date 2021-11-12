package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java8.advanced.FileExporter.FileContentWriter;
import victor.training.java8.advanced.repo.OrderRepo;
import victor.training.java8.advanced.repo.UserRepo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

interface MyConsumer<T> {
   void accept(T t) throws Exception;
}

@Service
class FileExporter {

   @Value("${export.folder.out}")
   private File folder;

   @FunctionalInterface
   interface FileContentWriter {
      void write(Writer writer) throws Exception;
   }

   //   @Timed(micrometer)
   @Transactional
   public void exportFile(String fileName, FileContentWriter contentWriter) {
      File file = new File(folder, fileName);
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) { // ~ anaology to any resource that needs cleaning / releasing : sockets, jms connections, jdbc conn
         System.out.println("Starting export to: " + file.getAbsolutePath());

         contentWriter.write(writer);

         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         // TODO send email notification
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }
   }

}

@Service
class ExportFormats {

   @Autowired
   private OrderRepo orderRepo;

   public void writeOrdersContent(Writer writer) throws IOException {
      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
          .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
          .forEach(Unchecked.consumer(writer::write));
   }

   @Autowired
   UserRepo userRepo;

   public void writeUserContent(Writer writer) throws IOException {
      writer.write("username;fullname\n");
      userRepo.findAll().stream()
          .map(u -> u.getUsername() + ";" + u.getFullName() + "\n")
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
   private final ExportFormats exportFormats;

   public void run(String... args) throws Exception {
      fileExporter.exportFile("orders.csv", exportFormats::writeOrdersContent);

      fileExporter.exportFile("users.csv", exportFormats::writeUserContent);


//      fileExporter.exportFile("file.name", new FileContentWriter() {
//         @Override
//         public void write(Writer writer) throws Exception {
//
//         }
//      });

   }


   // UTIL wornderful idea
//   public static <T> Consumer<T> conversion(MyConsumer<T> f) {
//      return input -> {
//         try {
//            f.accept(input);
//         } catch (Exception e) {
//            throw new RuntimeException(e);
//         }
//      };
//   }

//
//   //   @SneakyThrows // maybe NOT
//   private void javaSucks(Writer writer, String str) {
//      try {
//         writer.write(str);
//      } catch (IOException e) {
//         throw new RuntimeException(e);
//      }
//   }
}
