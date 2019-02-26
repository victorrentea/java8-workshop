package victor.training.java8.promisiuni;

import static victor.training.java8.parallelstream.ConcurrencyUtil.log;
import static victor.training.java8.parallelstream.ConcurrencyUtil.sleep2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import victor.training.java8.parallelstream.ConcurrencyUtil;

public class BonDeOrdine {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		new BonDeOrdine().entryPoint();
		sleep2(5000);
	}
	
	
	void entryPoint() throws InterruptedException, ExecutionException {
		ForkJoinPool piscina = new ForkJoinPool(2); // consideram ca e deja pornit
		log("Start!");		
		CompletableFuture<Void> viitorA = CompletableFuture.runAsync(this::stepA, piscina);
		
		
		CompletableFuture<Void> viitorB = viitorA.thenRunAsync(this::stepB, piscina);
		CompletableFuture<Void> viitorC = viitorA.thenRunAsync(this::stepC, piscina);
		
		log("le-am pornit");
		
		 // JOIN din UML
		CompletableFuture<Void> futureD = CompletableFuture.allOf(viitorB, viitorC) //asta e facut POC
				.exceptionally(exceptie -> {
//					exceptie.printStackTrace(System.out);
					if (exceptie.getCause() instanceof IllegalStateException) { 
						return iaDinCache();
					} else { 
						throw new RuntimeException(exceptie); // arunc-o mai departe. Miaaau!! 
					}
				})
				.thenRunAsync(this::stepD, piscina);
		futureD.get();
		log("Gata!");
	}
	
	private Void iaDinCache() {
		return null; // TODO
	}


	void stepA() {
		log("Execut A");
		sleep2(1000);
		log("Gata A");
	}
	void stepB() {
		log("Execut B");
		sleep2(1000);
		log("Gata B");
	}
	void stepC() {
		log("Execut C");
		sleep2(1000);
		if (true) {
			throw new IllegalStateException("Intentionat o comit");
		}
		log("Gata C");
	}
	void stepD() {
		log("Execut D");
		sleep2(1000);
		log("Gata D");
	}
}
