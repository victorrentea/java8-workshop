package victor.training.java.advanced;

import victor.training.java.advanced.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SplitLoopToStream {

    // see tests
    public void bossLevel(List<User> users) {
        int missingLanguageCount;
        int totalTickets;
        List<String> allUsernames = users.stream().map(User::getUsername).collect(Collectors.toList());
        missingLanguageCount = (int) users.stream().filter(user -> user.getLanguage() == null).count();

        for (User user : users) {
           if (!user.getActive()) {
              insertInactiveAlert(user.getId());
           }
        }
 // dupa gust daca faci
       // .stream.forEach ( side effects ) (!! ai grija sa nu ai lambde de mai multe linii -> {   ) sau
       // for (:) { side effects } e dupa gust
        users.stream()
                .filter(Predicate.not(User::getActive))
                .forEach(u -> {
                   // cod
                   // cod
                   insertInactiveAlert(u.getId());
                });


       totalTickets = users.stream().filter(User::getActive).mapToInt(User::getTicketsRaised).sum();
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

