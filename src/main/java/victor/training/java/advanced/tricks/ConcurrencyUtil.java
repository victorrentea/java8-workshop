package victor.training.java.advanced.tricks;

public class ConcurrencyUtil {
	/**
	 * Sleeps quietly (without throwing anything out)
	 */
	public static void sleepq(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
