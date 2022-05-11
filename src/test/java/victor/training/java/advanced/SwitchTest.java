package victor.training.java.advanced;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static victor.training.java.advanced.MovieType.*;

class SwitchTest {
   Switch target = new Switch();

   @Test
   void test() {
      for (MovieType value : values()) {
         try {
            Switch.computePrice(value, 1);
         } catch (Exception e) {
            if (e instanceof ExMea) {
               fail(" Nu ai acoperit " + value);
            } else {
               // nimic e ok
            }
         }
      }
   }
   
   
   @Nested
   class ComputePrice {
      @Test
      void regular() {
         assertThat(target.computePrice(REGULAR, 2)).isEqualTo(3);
      }

      @Test
      void newRelease() {
         assertThat(target.computePrice(NEW_RELEASE, 2)).isEqualTo(4);
      }

      @Test
      void children() {
         assertThat(target.computePrice(CHILDREN, 3)).isEqualTo(5);
      }
   }

   @Nested
   @ExtendWith(OutputCaptureExtension.class)
   class AuditDelayReturn {
      @Test
      void regular(CapturedOutput capturedOutput) {
         target.auditDelayReturn(REGULAR, 3);
         assertThat(capturedOutput.getOut()).contains("Regular delayed by 3");
      }

      @Test
      void newRelease(CapturedOutput capturedOutput) {
         target.auditDelayReturn(NEW_RELEASE, 3);
         assertThat(capturedOutput.getOut()).contains("CRITICAL: new release return delayed by 3");
      }

      @Test
      void children(CapturedOutput capturedOutput) {
         target.auditDelayReturn(CHILDREN, 3);
         assertThat(capturedOutput.getOut()).isEmpty();
      }
   }
}
