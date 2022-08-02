package victor.training.java.advanced;

import victor.training.java.advanced.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class SplitLoopToStream {

   // see tests
   public void bossLevel(List<User> users) {

      int missingLanguageCount = (int) users.stream()
              .filter(u -> u.getLanguage() == null)
              .count();
      // the essence of FP: you do not change stuff but compute and return


      List<String> allUsernames = users.stream().map(User::getUsername).collect(toList());

      int totalTickets = users.stream()
              .filter(User::getActive)
              .mapToInt(User::getTicketsRaised)
              .sum();

      for (User user : users) {
         if (!user.getActive()) {
            insertInactiveAlert(user.getId());
         }
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

