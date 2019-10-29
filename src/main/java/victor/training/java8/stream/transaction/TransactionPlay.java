package victor.training.java8.stream.transaction;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
		
//		assertEquals(expected, list);
	}
		
	@Test //2
	public void unique_cities_of_the_traders() {
		List<String> expected = Arrays.asList("Cambridge", "Milan");
		

//		assertEquals(expected, list);
	}
	
	@Test //3
	public void traders_from_Cabridge_sorted_by_name() {
		List<Trader> expected = Arrays.asList(alan, brian, raoul);

//		assertEquals(expected, list);
	}
	
	@Test //4
	public void names_of_all_traders_sorted_joined() {
		String expected = "Alan,Brian,Mario,Raoul";
		
//		assertEquals(expected, joined);
	}
			
	@Test //5
	public void are_traders_in_Milano() {
//		assertTrue(areTradersInMilan);
	}
	
	@Test //6 
	public void sum_of_values_of_transactions_from_Cambridge_traders() { 
//		assertEquals(2650, sum);
	}
	
	@Test //7
	public void max_transaction_value() {

//		assertEquals(1000, max);
	}

	
	@Test
	public void transaction_with_smallest_value() {
//		assertEquals(expected, min);
	}
	@Test
	public void fibonacci_first_10() {
	}
	
	@Test
	public void a_transaction_from_2012() {
		Transaction expected = tx[1];

//		assertEquals(expected, tx2012);
	}
	
	@Test
	public void uniqueCharactersOfManyWords() {
		List<String> expected = Arrays.asList("a", "b", "c", "d", "f");
		List<String> wordsStream = Arrays.asList("abcd", "acdf");

//		assertEquals(expected, actual);
	}
	
	
	
	@Test
	public void advanced_sum_using_consumer() {
		List<Integer> numere = Arrays.asList(1, 3, 6, 8,9);
		// SOLUTION(
		MySmartConsumerIBM summer = new MySmartConsumerIBM();

		numere.stream().forEach(summer);
		System.out.println("Suma = " +summer.getSum());

		int sum = 0;
		for (int n : numere) {
			sum += n;
		}
		// SOLUTION)
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
