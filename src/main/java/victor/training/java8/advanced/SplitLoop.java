package victor.training.java8.advanced;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

interface NotifyStuff {
    public void notifyForEmployee(long employeeId);
}

@RequiredArgsConstructor
public class SplitLoop {
    private final NotifyStuff notifyStuff;

    private String computeStats(List<Employee> employees) {
        long averageAge = 0;
        double averageSalary = 0;
        for (Employee employee : employees) {
            if (!employee.isConsultant())
            averageAge += employee.getAge();
            averageSalary += employee.getSalary();
            notifyStuff.notifyForEmployee(employee.getId());
        }
        averageAge = averageAge / employees.stream().filter(e -> !e.isConsultant()).count();
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