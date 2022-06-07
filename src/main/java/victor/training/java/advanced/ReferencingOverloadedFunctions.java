package victor.training.java.advanced;

import java.util.Date;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReferencingOverloadedFunctions {
    public static void main(String[] args) {
        Supplier<Date> s = Date::new;
        Function<Long, Date> s2 = Date::new;
    }
}
