package victor.training.java.advanced;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import victor.training.java.advanced.model.Language;
import victor.training.java.advanced.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(OutputCaptureExtension.class)
class SplitLoopToStreamTest {

   SplitLoopToStream target = new SplitLoopToStream();

   @Test
   void bossLevel(CapturedOutput capturedOutput) {
      List<User> users = List.of(
          new User()
              .setUsername("::user1::")
              .setActive(true)
              .setTicketsRaised(2)
              .setLanguage(null),
          new User()
              .setUsername("::user2::")
              .setActive(true)
              .setTicketsRaised(1)
              .setLanguage(Language.ENGLISH),
          new User()
              .setId(3L)
              .setUsername("::user3::")
              .setActive(false)
      );

      target.bossLevel(users);

      System.out.println(capturedOutput);
      Assertions.assertThat(capturedOutput)
          .contains("Alert inactive user: 3")
          .contains("No language set  = 66.67%")
          .contains("Number of tickets = 3")
          .contains("Write to file: [::user1::, ::user2::, ::user3::]");
   }
}