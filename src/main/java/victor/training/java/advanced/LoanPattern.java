package victor.training.java.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.java.advanced.repo.OrderRepo;
import victor.training.java.advanced.repo.UserRepo;

@FunctionalInterface
interface ThrowingConsumer<T> {
    void accept(T t) throws IOException;
}

@Service
class FileExporter {
    @Value("${export.folder.out}")
    private File folder;

    @FunctionalInterface
    interface ContentWriter {
        void writeContent(Writer toWriteTo) throws Exception;
    }

    public void exportFile(String fileName, ContentWriter contentWriter) {
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
@Component
class OrderContentWriter {
    @Autowired
    private OrderRepo orderRepo;

    public void writeOrders(Writer writer) throws IOException {
        writer.write("OrderID;Date\n"); // header
        orderRepo.findByActiveTrue()
                .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
                .forEach(Unchecked.consumer(writer::write));
    }
}

@Component
class UserContentWriter {
    @Autowired
    private  UserRepo userRepo;
    public void writeUsers(Writer writer) throws IOException {
        writer.write("Username;FullName\n"); // header
        userRepo.findAll().stream()
                .map(u -> u.getUsername() + ";" + u.getFullName() + "\n")
                .forEach(Unchecked.consumer(writer::write));
    }

    public static <T> Consumer<T> convert(ThrowingConsumer<T> f) {
        return s -> {
            try {
                f.accept(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

}

@RequiredArgsConstructor
//@SpringBootApplication // enable on demand.
public class LoanPattern implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(LoanPattern.class, args);
    }

    private final FileExporter fileExporter;
    private final UserContentWriter userContentWriter;
    private final OrderContentWriter orderContentWriter;

    public void run(String... args) throws Exception {
        fileExporter.exportFile("orders.csv", orderContentWriter::writeOrders);



        fileExporter.exportFile("users.csv", userContentWriter::writeUsers);


//        fileExporter.exportFile("a.txt", );

        // TODO implement export of users too
    }
}

