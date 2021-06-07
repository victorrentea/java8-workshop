package victor.training.java8.advanced;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java8.advanced.model.User;
import victor.training.java8.advanced.repo.UserRepo;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserExportContentTest {

   @InjectMocks
   private UserExportContent userExportContent;

   @Mock
   private UserRepo userRepo;

   @Test
   void writeUserContent() throws IOException {
      Mockito.when(userRepo.findAll())
          .thenReturn(Arrays.asList(new User().setId(1L).setFullName("John Doe")));
      StringWriter writer = new StringWriter();

      userExportContent.writeUserContent(writer);

      String exportText = writer.toString();
      assertEquals("username;fullname\n1;John Doe", exportText);
   }
}