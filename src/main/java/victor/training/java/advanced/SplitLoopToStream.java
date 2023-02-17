package victor.training.java.advanced;

import victor.training.java.advanced.model.User;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class SplitLoopToStream {

  // see tests
  public void bossLevel(List<User> users) {

    // RAU:
    //      for (User user : users) {
    //         allUsernames.add(user.getUsername());
    //      }

    // BINE
    List<String> allUsernames = users.stream()
            .map(User::getUsername)
            .collect(toList());

    int missingLanguageCount = (int) users.stream().filter(user -> user.getLanguage() == null).count();

    //      missingLanguageCount = (int) users.stream()
    //              .filter(user->user.getLanguage()==null)
    //              .count();


    int totalTickets = users.stream()
            .takeWhile(User::getActive)
            .mapToInt(User::getTicketsRaised)
            .sum();


    for (User user : users) {
      if (!user.getActive()) {
        insertInactiveAlert(user.getId());
        break;
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

