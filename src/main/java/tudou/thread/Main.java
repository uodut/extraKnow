package tudou.thread;

import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		FrequencyCalculation fc = new FrequencyCalculation();
		new Thread(fc, "线程1").start();
		new Thread(fc, "线程2").start();
		new Thread(fc, "线程3").start();
		new Thread(fc, "线程4").start();
		new Thread(fc, "线程5").start();
		TimeUnit.SECONDS.sleep(15);
		System.out.println(ReadAndWrite.getWordMap().size());
		fc.printMap(ReadAndWrite.getWordMap());
		
	}
}
