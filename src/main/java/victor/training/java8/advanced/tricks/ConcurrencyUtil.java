package victor.training.java8.advanced.tricks;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

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
