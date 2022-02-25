package victor.training.java;

import victor.training.java.advanced.model.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class Intro {
   public static void main(String[] args) {

      Comparator<User> c1 = (u1, u2) -> u1.getUsername().compareTo(u2.getUsername());

      List<User> list = List.of(new User());

//      Collections.sort(list, Comparator.comparing(User::getUsername));

      List<User> sortedNewList = list.stream().sorted(comparing(User::getUsername)).collect(toList());

   }
}
