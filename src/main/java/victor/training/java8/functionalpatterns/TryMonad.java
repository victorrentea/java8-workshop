package victor.training.java8.functionalpatterns;

import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

public class TryMonad {

   public static void main(String[] args) {


      List<String> dateStr = Arrays.asList("2020-10-11", "2020-nov-12", "2020-12-01");
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      List<Date> dates = dateStr.stream().map(s-> parseShit(sdf, s)).collect(Collectors.toList());

   }

   @SneakyThrows
   private static Date parseShit(SimpleDateFormat sdf, String s) {
      return sdf.parse(s);
   }
}
