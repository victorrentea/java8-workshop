package victor.training.java8.stream.transaction;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class TransactionPlay {
	
	



	private Trader raoul = new Trader("Raoul", "Cambridge");
	private Trader mario = new Trader("Mario","Milan");
	private Trader alan = new Trader("Alan","Cambridge");
	private Trader brian = new Trader("Brian","Cambridge");

	private List<Transaction> transactions = Arrays.asList(
	    new Transaction(brian, 2011, 300),
	    new Transaction(raoul, 2012, 1000),
	    new Transaction(raoul, 2011, 400),
	    new Transaction(mario, 2012, 710),
	    new Transaction(mario, 2012, 700),
	    new Transaction(alan, 2012, 950)
	);
	
	private Transaction[] tx = transactions.toArray(new Transaction[0]);
		
	@Test //1
	public void all_2011_transactions_sorted_by_value() {
		List<Transaction> expected = Arrays.asList(tx[0], tx[2]);
		List<Transaction> list = transactions.stream()
				.filter(t -> t.getYear() == 2011)
				.sorted(Comparator.comparing(Transaction::getValue))
				.collect(toList());
		assertEquals(expected, list);
	}
	@Test //2
	public void unique_cities_of_the_traders() {
		List<String> expected = Arrays.asList("Cambridge", "Milan");

		List<String> list = transactions.stream()
				.map(Transaction::getTrader) // Stream<Trader>
				.map(Trader::getCity)
				.distinct()
				.collect(Collectors.toList());

//				.collect(Collectors.toCollection(TreeSet::new)); // ordoneaza crescator
//				.collect(Collectors.toSet()); // ordoneaza varza

//		assertEquals(expected, list);
//		list.containsAll(expected);
		Assertions.assertThat(list).containsAnyElementsOf(expected);
	}
	@Test //3
	public void traders_from_Cabridge_sorted_by_name() {
		List<Trader> expected = Arrays.asList(alan, brian, raoul);

		List<Trader> list = transactions.stream()
				.map(Transaction::getTrader)
				.filter(t -> t.getCity().equals("Cambridge"))
				.sorted(Comparator.comparing(Trader::getName))
				.distinct()
				.collect(toList());
		assertEquals(expected, list);
	}
	@Test //4
	public void names_of_all_traders_sorted_joined() {
		String expected = "Alan,Brian,Mario,Raoul";

		String joined = transactions.stream()
				.map(Transaction::getTrader)
				.sorted(Comparator.comparing(Trader::getName))
				.map(Trader::getName)
				.distinct()
				.collect(joining(","));
		assertEquals(expected, joined);
	}
			
	@Test //5
	public void are_traders_in_Milan() {
		boolean areTradersInMilan = transactions.stream()
//				.map(Transaction::getTrader) -- de evitat daca e posibil
				.anyMatch(tx -> tx.getTrader().getCity().equals("Milan"));
		assertTrue(areTradersInMilan);
	}
	@Test //6
	public void sum_of_values_of_transactions_from_Cambridge_traders() {
		int sum = transactions.stream()
				.filter(tx -> tx.getTrader().getCity().equals("Cambridge"))
				.mapToInt(Transaction::getValue)
				.sum();
		assertEquals(2650, sum);
	}
	
	@Test //7
	public void max_transaction_value() {


		int max = transactions.stream()
				.mapToInt(Transaction::getValue)
				.max()
				.orElse(-1);
		assertEquals(1000, max);
	}

	
	@Test
	public void transaction_with_smallest_value() {
		Transaction expected = transactions.get(0);

		Transaction min = transactions.stream()
				.min(Comparator.comparing(Transaction::getValue))
				.get(); // asa nu

		Iterator<Transaction> iterator = transactions.stream().iterator();
		Iterable<Transaction> iterable = () -> iterator;
		Stream<Transaction> stream = StreamSupport.stream(iterable.spliterator(), false);


		assertEquals(expected, min);
	}
	@Test
	public void fibonacci_first_10() {
//		(a,b) -> (b,a+b)
		Stream.iterate(new int[]{1, 1},
				a -> new int[]{a[1], a[0]+a[1]})
			.map(a -> a[0])
			.limit(30)
			.forEach(System.out::println);


	}
	// 1 1 2 3 5 8 13 21 34

	@Test
	public void a_transaction_from_2012() {
		Transaction expected = tx[1];

//		assertEquals(expected, tx2012);
	}
	
	@Test
	public void uniqueCharactersOfManyWords() {
		List<String> expected = Arrays.asList("a", "b", "c", "d", "f");
		List<String> wordsStream = Arrays.asList("abcd", "acdf");

		char[] arr = {'a'};
//.flatMap

//		List<char[]> chars = Arrays.asList(arr);
//		Arrays.stream(arr);
//		Stream.of(arr);
//		List<String> ss  =

//		List<String> actual = wordsStream.stream()
//				.map(word -> word.toCharArray())
//				;
//		assertEquals(expected, actual);
	}
	
	
	
	@Test
	public void advanced_sum_using_consumer() {
		List<Integer> numere = Arrays.asList(1, 3, 6, 8,9);

		int sum = 0;
//		for (int n : numere) {
//			sum += n;
//		}
		numere.stream()
				.forEach(e-> {
//					sum += e;
					System.out.println("Vad: " + e);
				});

		Supplier<Integer> f = coaceUnSupplier();
		System.out.println(f.get());
		System.out.println(f.get());
	}

	Supplier<Integer> coaceUnSupplier() {
		int counter = 0;
		return () ->{
				return counter++;
		};
	}



	// SOLUTION(
	private static class MySmartConsumerIBM implements Consumer<Integer> {
		int sum = 0;

		@Override
		public void accept(Integer n) {
			sum += n;
		}
		
		public int getSum() {
			return sum;
		}
	}
	// SOLUTION)
}
