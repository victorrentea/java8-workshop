package victor.training.java8.parallelstream;

import static java.util.Arrays.asList;
import static victor.training.java8.parallelstream.ConcurrencyUtil.log;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TerenDeJoaca {

	
	public static void main(String[] args) {
		List<Integer> list = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		int suma = list.parallelStream()
//			.parallel()
			.filter(n -> { 
				log("Filter " + n);
				return n % 2 == 0;
			})
//			.sequential()
//			.findAny().get();
//			.findFirst().get();
			.mapToInt(n -> {
				log("Map+IO " + n);
				return n * n;
			})
			.sum();
		
		// findAny intoarce prima valoare gasita de oricare din threaduri.
		// un Stream este integral fie SERIAL fie PARALEL: castiga ultima metoda apelata pe el: .parallel() fie .sequential()
		
		
		
		
//		Iterator<Integer> iterator = list.iterator();
//		Iterable<Integer> iterable = () -> iterator; 
//		Stream<Integer> stream = StreamSupport.stream(iterable.spliterator(), false); 
//
//		Iterator<Integer> iterator2 = stream.iterator();

		
		
		System.out.println(suma);
	}
}
