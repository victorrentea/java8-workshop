package victor.training.java.advanced;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java.advanced.model.User;
import victor.training.java.advanced.repo.UserRepo;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserExportContentServiceTest {
   @Mock
   UserRepo userRepo;

   @InjectMocks
   UserExportContentService target;

   @Test
   void writeUserContent() throws IOException {
      // given
      StringWriter writer = new StringWriter();
      User user = new User().setUsername("uname").setFullName("fullname");
      when(userRepo.findAll()).thenReturn(List.of(user));

      //when
      target.writeUserContent(writer);

      // then
      String content = writer.toString();
      assertThat(content).isEqualTo("username;fullname\n" +
                                    "uname;fullname\n");
   }
}