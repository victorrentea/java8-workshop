package victor.training.java.optional.abuse;

import java.util.Optional;

public class OptionalParameters {

  public void callers() {
    // without
    sendMessage("jdoe", "message");

    // with
    sendMessageWithTracking("jdoe", "message", "REGLISS");

  }

  // ⬇⬇⬇⬇⬇⬇ utility / library code ⬇⬇⬇⬇⬇⬇
  public void sendMessage(String recipient, String message) {
    System.out.println("Resolve phone number for " + recipient);
    System.out.println("Send message " + message);
  }
  public void sendMessageWithTracking(String recipient, String message, String reg) {
    sendMessage(recipient, message);

   System.out.println("Also notify the tracking registry : " + reg);
  }
}
