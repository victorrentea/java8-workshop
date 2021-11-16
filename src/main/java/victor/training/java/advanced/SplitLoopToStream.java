package victor.training.java.advanced;

import victor.training.java.advanced.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitLoopToStream {

   // see tests
   public void bossLevel(List<User> users) {
      List<String> allUsernames = new ArrayList<>();
      int missingLanguageCount = 0;
      int totalTickets = 0;
      for (User user : users) {
         allUsernames.add(user.getUsername());
         if (user.getLanguage() == null) {
            missingLanguageCount++;
         }
         if (!user.getActive()) {
            insertInactiveAlert(user.getId());
            continue;
         }
         totalTickets += user.getTicketsRaised();
      }
      double noLanguagePercent = 100d * missingLanguageCount / users.size();
      System.out.printf("No language set  = %.2f%%\n", noLanguagePercent);
      System.out.printf("Number of tickets = %d\n", totalTickets);
      writeToFile(allUsernames);
   }

   private void writeToFile(List<String> usernames) {
      System.out.println("Write to file: " + usernames);
      // omitted
   }

   private void insertInactiveAlert(Long userId) {
      System.out.println("Alert inactive user: " + userId);
      // omitted
   }
}

