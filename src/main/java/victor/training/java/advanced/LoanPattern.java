package victor.training.java.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import victor.training.java.advanced.repo.OrderRepo;
import victor.training.java.advanced.repo.UserRepo;

@Service
class FileExporter {
    @Autowired
    private OrderRepo orderRepo;
    @Value("${export.folder.out}")
    private File folder;
    @FunctionalInterface
    public interface ConsumeruMeuCareArunca<T> {
        void accept(T t) throws Exception;
    }

 // infrastructura pura

    @FunctionalInterface
    interface ExportFileWriter {
        void writeContent(Writer writer) throws IOException;
    }
    public void exportFile(String fileName, ExportFileWriter contentWriter) {


        File file = new File(folder, fileName);
        long t0 = System.currentTimeMillis();
        try (Writer writer = new FileWriter(file)) {
            System.out.println("Starting export to: " + file.getAbsolutePath());

            contentWriter.writeContent(writer);

            System.out.println("File export completed: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Imagine: Send Error Notification Email");
            throw new RuntimeException("Error exporting data", e);
        } finally {
            System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
        }
    }
}
//------si .. la un nivel de abstractie superior. adica logica mai putin "tehnica"

@Service
@RequiredArgsConstructor
class OrderContentWriter {
    private final OrderRepo orderRepo;

    public void writeOrders(Writer writer) throws IOException {
        writer.write("OrderID;Date\n"); // header

        orderRepo.findByActiveTrue()
                .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
                .forEach(Unchecked.consumer(writer::write));
    }
}
@Service
class UserContentWriter {
    @Autowired
    private UserRepo userRepo;

    public void writeUsers(Writer writer) throws IOException {
        writer.write("username;fullname\n"); // header

        userRepo.findAll().stream()
                .map(u -> u.getUsername() + ";" + u.getFullName() + "\n")
                .forEach(Unchecked.consumer(writer::write));
    }
}

@RequiredArgsConstructor
//@SpringBootApplication // enable on demand.
public class LoanPattern implements CommandLineRunner {
    private final FileExporter fileExporter;
private final OrderContentWriter orderContentWriter;
private final UserContentWriter userContentWriter;

    public static void main(String[] args) {
        SpringApplication.run(LoanPattern.class, args);
    }

    public void run(String... args) throws Exception {
        fileExporter.exportFile("orders.csv", orderContentWriter::writeOrders);

        fileExporter.exportFile("users.csv", userContentWriter::writeUsers);


//        fileExporter.exportFile("aa", );
        // TODO implement export of users too
    }
}

//    public static <T> Consumer<T> imbraca(ConsumeruMeuCareArunca<T> originala) {
//        return s -> {
//            try { // mai tarziu
//                originala.accept(s);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        };
//    }
