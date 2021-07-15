package victor.training.java8.advanced;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

interface NotifyStuff {
    public void notifyForEmployee(long employeeId);
}

@RequiredArgsConstructor
public class SplitLoop {
    private final NotifyStuff notifyStuff;

    private String computeStats(List<Employee> employees) {

        long averageAge = employees.stream()
            .filter(employee -> !employee.isConsultant())
            .mapToLong(Employee::getAge).sum();

//        (long) employees.stream().filter(Predicate.not(Employee::isConsultant))
//            .mapToLong(Employee::getAge).average().getAsDouble();

        double averageSalary = employees.stream().mapToDouble(Employee::getSalary).sum();

        for (Employee employee : employees) {
            notifyStuff.notifyForEmployee(employee.getId());
        }
//        averageAge = averageAge / employees.stream().filter(e -> !e.isConsultant()).count();
        averageSalary = averageSalary / employees.size();
        return "avg age = " + averageAge + "; avg sal = " + averageSalary;
    }

    @Test
    public void test() {
        String actual = computeStats(asList(
                new Employee(1L, 24, 2000, false),
                new Employee(1L, 28, 1500, true),
                new Employee(1L, 30, 2500, true)));
        assertEquals("avg age = 24; avg sal = 2000.0", actual);
    }
}


@Data
class Employee {
    private final Long id;
    private final int age;
    private final double salary;
    private final boolean consultant;
}