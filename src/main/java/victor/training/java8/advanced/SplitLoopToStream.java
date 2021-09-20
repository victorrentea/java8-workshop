package victor.training.java8.advanced;

import victor.training.java8.advanced.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

public class SplitLoopToStream {
   public static void main(String[] args) {
      new SplitLoopToStream().bossLevel(Arrays.asList(
          new User().setId(1L).setTicketsRaised(5).setUsername("jdoe"),
          new User().setId(2L).setTicketsRaised(65).setUsername("hero").setActive(false)
      ));
   }

   public void bossLevel(List<User> users) {
      List<String> allUsernames = users.stream().map(User::getUsername).collect(toList());

      int totalTickets = users.stream().mapToInt(User::getTicketsRaised).sum();
      // 10K elemente   Loop Unrolling

      //      users.stream().filter(User::getActive)
//          .reduce(0, (oldValue, user) -> oldValue + user.getTicketsRaised(), Integer::sum);

//      users.stream()
//          .filter(User::getActive)
//          .map(User::getTicketsRaised)
//          .reduce(0, Integer::sum);

//      int totalTickets = users.stream()
//          .filter(User::getActive)
//          .mapToInt(User::getTicketsRaised)
//          .sum();

      // 100 elemente
      int noLanguageCount = (int) users.stream().filter(User::getActive).filter(user1 -> user1.getLanguage() == null).count();

      ///---
      users.stream()
          .filter(user -> !user.getActive())
//          .filter(not(User::getActive))
//          .filter(user -> user.isInactive()) // o noua functie
           .map(User::getId)
           .forEach(this::insertAlertInactive);

      ///---
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

