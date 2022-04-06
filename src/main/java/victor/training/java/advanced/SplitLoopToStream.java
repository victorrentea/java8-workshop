package victor.training.java.advanced;

import victor.training.java.advanced.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SplitLoopToStream {

   // see tests
   public void bossLevel(List<User> users) {
      int missingLanguageCount = 0;
      int totalTickets = 0;


      List<String> allUsernames = users.stream().map(User::getUsername).collect(Collectors.toList());
      //      List<String> allUsernames = new ArrayList<>();
//      for (User user : users) {
//         allUsernames.add(user.getUsername());
//      }
//      usual for vs forEach?

      for (User user : users) {
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

