package victor.training.java8.advanced;

import org.h2.engine.User;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import victor.training.java8.advanced.CsvExporter.ContentWriterFunction;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CsvExporterTest {

   @Test
   void exportFile() {
      ContentWriterFunction dummyContent = w -> w.write("dummy");
      CsvExporter exporter = new CsvExporter();

      JdbcTemplate jdbc = new JdbcTemplate();


//      jdbc.queryForObject("SELECT * FROM USER", new RowMapper<User>() {
//         @Override
//         public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//            User user = new User();
//            user.setUsername(rs.getString("username"));
//         }
//      })

      exporter.exportFile("a.txt", dummyContent);

      // TODO verifica ca fisierul s-a creeat in {out.folder}/a.txt continand "dummy"
   }
}