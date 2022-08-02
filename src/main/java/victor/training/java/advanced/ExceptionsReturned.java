package victor.training.java.advanced;

import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class ExceptionsReturned {

    // ** Requirements **
    // Some dates might have invalid format
    // - if >= 50% valid => return the parseable dates,
    // - else => throw IllegalArgumentException
    // I WANT TO SEE ALL THE FAILED DATES>
    /* @see tests */
    public List<LocalDate> parseDates(List<String> dateStrList) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<String> failedDates = new ArrayList<>();
        List<Try<LocalDate>> tries = dateStrList.stream()
                .map(text -> Try.of(() -> LocalDate.parse(text, pattern)))
                .toList();

        long successNumber = tries.stream().filter(Try::isSuccess).count();

        CompletableFuture<String> futureValue = CompletableFuture.supplyAsync(() -> networkCall());
        futureValue = CompletableFuture.completedFuture("aaa");
        futureValue = CompletableFuture.failedFuture(new IllegalArgumentException());

//        Mono<String> m = Mono.erorr(); Mono.just

        if (successNumber > dateStrList.size() / 2) {
            return tries.stream().filter(Try::isSuccess).map(Try::get).collect(toList());
        } else {
            String allErrors = tries.stream()
                            .filter(Try::isFailure)
                            .map(Try::getCause)
                            .map(Throwable::getMessage)
                            .collect(Collectors.joining("\n"));
            throw new IllegalArgumentException(allErrors);
        }
    }

    private static String networkCall() {
        return "data";
    }
}
