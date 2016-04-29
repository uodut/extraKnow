package tudou.thread.practice.thread;

/**
 *线程知识知识测试
 */
public class ThreadTest {
	public static void main(String[] args) {
		ThreadFunction tFunction = new ThreadFunction();
		Thread t1 = new Thread(tFunction,"线程1");
		Thread t2 = new Thread(tFunction,"线程2");
		Thread t3 = new Thread(tFunction,"线程3");
		Thread t4 = new Thread(tFunction,"线程4");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}
