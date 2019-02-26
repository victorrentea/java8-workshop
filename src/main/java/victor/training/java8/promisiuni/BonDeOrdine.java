package victor.training.java8.promisiuni;

import static victor.training.java8.parallelstream.ConcurrencyUtil.log;

import victor.training.java8.parallelstream.ConcurrencyUtil;

public class BonDeOrdine {
public static void main(String[] args) throws InterruptedException {
	new BonDeOrdine().entryPoint();
}
	
	void entryPoint() throws InterruptedException {
		log("Start!");		
		stepA();
		Thread tB = new Thread(this::stepB);
		Thread tC = new Thread(this::stepC);
		
		tB.start();
		tC.start();
		log("le-am pornit");
		
		tB.join();
		tC.join();
		
		stepD();
		log("Gata!");
	}
	
	void stepA() {
		log("Execut A");
	}
	void stepB() {
		log("Execut B");
	}
	void stepC() {
		log("Execut C");
	}
	void stepD() {
		log("Execut D");
	}
}
