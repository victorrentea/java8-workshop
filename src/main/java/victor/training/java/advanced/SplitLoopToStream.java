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


      //  code smell "Accumulator Loop"
      List<String> allUsernames = users.stream().map(User::getUsername).collect(toList());


      ///how would you answer during code review, to a comment that now we
      // iterate 4 times through collection and it is less efficient?

      // did you measured the performance loss ? NO,

      // it might hurt performamnce if this usecase is an extremly
      // hot use0-case with a resposne time < 1ms. no DB 7ms, REST API. fully in-memory
      int missingLanguageCount = (int) users.stream()
              .filter(u -> u.getLanguage() == null)
              .count();

      for (User user : users) {
         if (!user.getActive()) {
            insertInactiveAlert(user.getId());
         }
      }
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

