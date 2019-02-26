package victor.training.java8.promisiuni;

import static victor.training.java8.parallelstream.ConcurrencyUtil.log;
import static victor.training.java8.parallelstream.ConcurrencyUtil.sleep2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import victor.training.java8.parallelstream.ConcurrencyUtil;

public class BonDeOrdine {
public static void main(String[] args) throws InterruptedException, ExecutionException {
	new BonDeOrdine().entryPoint();
}
	
	
	void entryPoint() throws InterruptedException, ExecutionException {
		ExecutorService piscina = Executors.newFixedThreadPool(2); // consideram ca e deja pornit
		log("Start!");		
		stepA();
		
		
		Future<?> viitorB = piscina.submit(this::stepB);
		Future<?> viitorC = piscina.submit(this::stepC);

		log("le-am pornit");
		
		viitorB.get();
		viitorC.get();
		
		stepD();
		log("Gata!");
	}
	
	void stepA() {
		log("Execut A");
		sleep2(1000);
	}
	void stepB() {
		log("Execut B");
		sleep2(1000);
	}
	void stepC() {
		log("Execut C");
		sleep2(1000);
	}
	void stepD() {
		log("Execut D");
		sleep2(1000);
	}
}
