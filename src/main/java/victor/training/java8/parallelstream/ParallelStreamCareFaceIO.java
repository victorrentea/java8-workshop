package victor.training.java8.parallelstream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static victor.training.java8.parallelstream.ConcurrencyUtil.log;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

public class ParallelStreamCareFaceIO {

	private static final int POTENTIOMETRU = 2;

	public static void main(String[] args) throws InterruptedException {
		List<Integer> list = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Stream<User> userStream = list.parallelStream()
				.map(id -> {
					log("REST call getUserById(" + id+ ")");
					return new User(id);
				});
		
		ForkJoinPool pool = new ForkJoinPool(POTENTIOMETRU);
		pool.submit(() ->userStream.collect(toList()));
		//o crapat main-ul. 
//		pool.shutdown();
//		pool.A
		Thread.sleep(1000);
	}
}

class User{
	private final int id;

	public User(int id) {
		this.id = id;
	}
	
}