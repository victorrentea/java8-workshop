package victor.training.java8.advanced;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Switch {
   private static final Logger log = LoggerFactory.getLogger(Switch.class);

   public static void main(String[] args) {
      System.out.println("VAT BG = " + getVAT("BG", 10, true));
      System.out.println("VAT BD = " + getVAT("MD", 20, false));

      auditMessage("CreateOrder");
   }

   private static double getVAT(String isoCode, double value, boolean tobacco) {
      /* TODO for MD return 5 + ... if tobacco==true */
//      switch (isoCode) {
//         case "BG":
//            return 0;
//         case "US":
//         case "MX":
//            return 15 + .05 * value;
//         case "MD":
//            return 7 + .02 * value;
//         default:
//            throw new IllegalArgumentException();
//      }
      return switch (isoCode) {
         case "BG" -> 0;
         case "US", "MX" -> 15 + .05 * value;
         case "MD" -> {
            double defaultValue = 7 + .02 * value;
            if (tobacco) {
               defaultValue += 5;
            }
            yield defaultValue;
         }
         default -> throw new IllegalArgumentException();
      };
   }

   public static void auditMessage(String messageCode) {
      switch (messageCode) {
         case "CreateOrder" -> log.info("Order Created");
         case "ViewOrder", "PrintOrder" -> log.info("Order Accessed");
      }
//      switch (messageCode) {
//         case "CreateOrder":
//            log.info("Order Created");
//            break;
//
//         case "ViewOrder":
//         case "PrintOrder":
//            log.info("Order Accessed");
//            break;
//      }
   }

   public static int computeMoviePrice(MovieType type, int days) {
      return switch (type) {
         case REGULAR -> days + 1;
         case NEW_RELEASE -> days * 2;
         case CHILDREN -> 5;
         case DE_BABACI -> 1;
      };
   }
}

