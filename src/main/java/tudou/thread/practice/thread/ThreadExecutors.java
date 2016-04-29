package tudou.thread.practice.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * 
 * Java通过Executors提供四种线程池，分别为：
 * 		newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * 		newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。 
 * 		newScheduledThreadPool创建一个定长线程池，支持定时及周期性任务执行。 
 * 		newSingleThreadExecutor创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 */
public class ThreadExecutors {
	static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
	static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
	static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	// 创建一个可缓存线程池,如果线程长度超过处理需要，可灵活回收空闲线程，如无可回收，则新建线程。
	protected static void cachedThreadPool() throws InterruptedException {
		ThreadFunction tFunction = new ThreadFunction();
		for (int i = 0; i < 10; i++) {
			cachedThreadPool.execute(tFunction);
			Thread.currentThread().sleep(1000);
		}
	}
	
	//创建一个固定长度的线程池,可以控制最大并发数
	protected static void fixedThreadPool(){
		ThreadFunction tFunction = new ThreadFunction();
		for (int i = 0; i < 4; i++) {
			fixedThreadPool.execute(tFunction);
			
		}
	}
	//创建一个定长线程池，支持定时及周期性任务执行。 
	protected static void scheduledThreadPool(){
		ThreadFunction tFunction = new ThreadFunction();
		/**
		 * Runnable command:要执行的对象
		 * long initialDelay ：第一次运行时延迟的时间
		 * long period：周期：每隔多久执行一次
		 * TimeUnit unit：计时单位
		 */
		scheduledThreadPool.scheduleAtFixedRate(tFunction, 10, 2, TimeUnit.SECONDS);
	}
	//创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
	/**
	 * 不明白带线程为什么还要用线程？！！
	 */
	protected static void singleThreadExecutor() {
		ThreadFunction tFunction = new ThreadFunction();
		singleThreadExecutor.execute(tFunction);
	}
	public static void main(String[] args) throws InterruptedException {

//		**第一种**
		/*fixedThreadPool();	
		fixedThreadPool.shutdown();*/
		
//		**第二种**
		
		//打印结果为全部为pool-2-thread-1在执行，说明在进行第二次循环时，线程1已经完成了任务处于空闲状态，所以不会创建新线程，而是复用线程1.
		/*cachedThreadPool();
		cachedThreadPool.shutdown();*/
		
//		**第三种**
		
		/*scheduledThreadPool();*/
		
//		**第四种**
		singleThreadExecutor();
	}
	
}
