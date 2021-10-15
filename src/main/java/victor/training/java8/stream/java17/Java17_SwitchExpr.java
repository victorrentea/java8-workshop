package victor.training.java8.stream.java17;

public class Java17_SwitchExpr {
   private boolean soare = true;

   enum MessageType {
      PLACE_ORDER,
      CANCEL_ORDER,
      SHIP_ORDER
//       , CEVA_NOUA
   }

   public int processMessage(String messageTypeStr) {

      MessageType messageType = MessageType.valueOf(messageTypeStr);

      return switch (messageType) {
         case PLACE_ORDER -> handleOrderPlaced();
         case CANCEL_ORDER -> handleOrderCancelled();
         case SHIP_ORDER -> handleOrderShipped();

      };
   }

   public int handleOrderPlaced() {
      System.out.println("chestii");
      System.out.println("chestii");
      System.out.println("chestii");
      System.out.println("chestii");
      System.out.println("chestii");
      return 1;
   }
   public int handleOrderCancelled() {
      // cod 100-10000 Linii
      return 0;
   }
   public int handleOrderShipped() {
      // cod 100-10000 Linii
      return 0;
   }
}
