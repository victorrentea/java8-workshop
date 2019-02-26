package victor.training.java8.stream;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReferences {
	public static void main(String[] args) {

		// f():Date
		Supplier<Date> dinNimicOData = Date::new;
		System.out.println("Acum = " + dinNimicOData.get());
		
		// f(Long):Date
		Function<Long, Date> timpulCandVreauEu = Date::new;
		System.out.println("Inceputul timpului = " + timpulCandVreauEu.apply(0L));
		
		
		
		BiFunction<String, String, Integer> caseNesimtit = (s1,s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
		Comparator<String> caseNesimtit3 = (s1,s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
		Comparator<String> caseNesimtit2 = Comparator.comparing(String::toUpperCase);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		BigDecimal bd1 = new BigDecimal("1");
		BigDecimal bd2 = new BigDecimal("2");
		
		BigDecimal bd3 = bd1.add(bd2);
		
		// f(BD):BD
		Function<BigDecimal, BigDecimal> adunare = bd1::add;
		
		// f(BD,BD): BD
		BiFunction<BigDecimal, BigDecimal, BigDecimal> adunareArbitrara = BigDecimal::add;
		System.out.println(adunareArbitrara.apply(bd1, bd2));
		
		
		// f(BD):BD
		Function<BigDecimal, BigDecimal> f1 = BigDecimal::abs;
		// f():BD
		Supplier<BigDecimal> f2 = bd1::abs;
		// f():Integer
		Supplier<Integer> f3 = bd1::intValue;
		
		//f(BD):String
		Function<BigDecimal, String> f4 = BigDecimal::toString;
		
		// f(String):BD
		Function<String, BigDecimal> f5 = BigDecimal::new; // new BigDecimal("2");
		
		// f(Double)::BD
		Function<Double, BigDecimal> f6 = BigDecimal::valueOf;
		
		// f():Calendar
		Supplier<Calendar> mamaCalendarului = Calendar::getInstance;
		
		// f():void
		Calendar c = mamaCalendarului.get();
		Runnable f7 = c::clear;
		
		
		// f(BD,BD):BD
		BiFunction<BigDecimal, BigDecimal, BigDecimal> f8 = BigDecimal::add;
		BinaryOperator<BigDecimal> f9 = BigDecimal::add;
		
		
		
	}
}
