package victor.training.java8.advanced;

import victor.training.java8.advanced.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitLoopToStream {
   public static void main(String[] args) {
      new SplitLoopToStream().bossLevel(Arrays.asList(
          new User().setId(1L).setTicketsRaised(5).setUsername("jdoe"),
          new User().setId(2L).setTicketsRaised(65).setUsername("hero").setActive(false)
      ));
   }

   public void bossLevel(List<User> users) {
      List<String> allUsernames = new ArrayList<>();
      int noLanguageCount = 0;
      int totalTickets = 0;
      for (User user : users) {
         allUsernames.add(user.getUsername());
         if (!user.getActive()) {
            insertAlertInactive(user.getId());
            continue;
         }
         totalTickets += user.getTicketsRaised();
         if (user.getLanguage() == null) {
            noLanguageCount++;
         }
      }
      double noLanguagePercent = 100d * noLanguageCount / users.size();
      System.out.printf("No language set  = %.2f%%\n", noLanguagePercent);
      System.out.printf("Number of tickets = %d\n", totalTickets);
      writeToFile(allUsernames);
   }

   private void writeToFile(List<String> usernames) {
      System.out.println("Write to file: " + usernames);
      // omitted
   }

   private void insertAlertInactive(Long userId) {
      System.out.println("Alert inactive user: " + userId);
      // omitted
   }
}

