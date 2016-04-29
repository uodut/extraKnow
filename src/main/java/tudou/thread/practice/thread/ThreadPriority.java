package tudou.thread.practice.thread;
/**
 * 线程优先级
 * 	Java线程可以有优先级的设定，高优先级的线程比低优先级的线程有更高的几率得到执行
 * 	Java线程的优先级是一个整数，其取值范围是1 （Thread.MIN_PRIORITY ） - 10 （Thread.MAX_PRIORITY ）。默认优先级为5
 * 	int getPriority():
 *  void setPriority(int newPriority):设置线程优先级
 *
 */
public class ThreadPriority {
	public static void main(String[] args) throws InterruptedException {
		ThreadFunction tFunction = new ThreadFunction();
		Thread t1 = new Thread(tFunction,"线程1");
		Thread t2 = new Thread(tFunction,"线程2");
		Thread t3 = new Thread(tFunction,"线程3");
		Thread t4 = new Thread(tFunction,"线程4");
		t1.setPriority(10);
		t2.setPriority(1);
		Thread.sleep(2000);
		t2.start();
		t3.start();
		t4.start();
		t1.start();
		
	}
}
