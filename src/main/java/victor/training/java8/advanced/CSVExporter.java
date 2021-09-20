package victor.training.java8.advanced;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import victor.training.java8.advanced.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

// Infrastuctura
@Service
public class CSVExporter {

   @Value("${export.folder.out}")
   private File folder;

   @FunctionalInterface
   interface ExportContentWriter {
      void writeContent(Writer writer) throws IOException;
   }

   public void exportFile(final String fileName, ExportContentWriter contentWriter) {
      File file = new File(folder, fileName + ".csv");
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());
         contentWriter.writeContent(writer);
         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         // TODO send email notification

         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }
   }

}
//      new JdbcTemplate().query("", (rs, rowNum) -> {
//         User user = new User();
//         user.setFullName(rs.getString(1));
//         return user;
//      });
