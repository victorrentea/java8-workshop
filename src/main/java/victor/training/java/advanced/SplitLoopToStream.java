package victor.training.java.advanced;

import victor.training.java.advanced.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class SplitLoopToStream {

   // see tests
   public void bossLevel(List<User> users) {

//      List<Boolean> booleans = new ArrayList<>();

      List<String> allUsernames = users.stream().map(User::getUsername).collect(toList());

      int missingLanguageCount = (int) users.stream().filter(user -> user.getLanguage() == null).count();

      for (User user : users) {
         if (!user.getActive()) {
            insertInactiveAlert(user.getId());
         }
      }
//      int totalTickets = users.stream().filter(User::getActive).map(User::getTicketsRaised).reduce(0, Integer::sum);
      int totalTickets = users.stream().filter(User::getActive).mapToInt(User::getTicketsRaised).sum();

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

