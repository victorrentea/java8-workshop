package victor.training.java.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import victor.training.java.advanced.repo.OrderRepo;

@Service
class FileExporter {
    @Autowired
    private OrderRepo orderRepo;
    @Value("${export.folder.out}")
    private File folder;

    public void exportFile() {
        File file = new File(folder, "orders.csv");
        long t0 = System.currentTimeMillis();
        try (Writer writer = new FileWriter(file)) {
            System.out.println("Starting export to: " + file.getAbsolutePath());

            writer.write("OrderID;Date\n"); // header
            orderRepo.findByActiveTrue()
                    .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
                    .forEach(orderString -> tryWrite(writer, orderString));

            System.out.println("File export completed: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Imagine: Send Error Notification Email");
            throw new RuntimeException("Error exporting data", e);
        } finally {
            System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
        }
    }

    private void tryWrite(Writer writer, String orderString) { // ai creat-o pentru ca
        // CHECKED exceptions ('95) intr-o lambda (2012) -
        // o fct pusa in codul  tau pt ca java e batran.
        try {
            writer.write(orderString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

@RequiredArgsConstructor
//@SpringBootApplication // enable on demand.
public class LoanPattern implements CommandLineRunner {
    private final FileExporter fileExporter;

    public static void main(String[] args) {
        SpringApplication.run(LoanPattern.class, args);
    }

    public void run(String... args) throws Exception {
        fileExporter.exportFile();

        // TODO implement export of users too
    }
}

