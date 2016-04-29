package tudou.thread.practice.thread;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * ThreadPoolExecutor类功能测试
 * 在java doc中，不提倡直接使用ThreadPoolExecutor而是建议使用Executors类提供的静态方法来代替ThreadPoolExecutor，
 * 其实Executors类的静态方法也是调用的ThreadPoolExecutor的构造方法，只不过是填好了参数。
 * 如下：
  public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
  }
 public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
 }
 public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
 }
 ThreadPoolExecutor类构造函数的参数含义：
 	具体参见：http://www.cnblogs.com/dolphin0520/p/3932921.html
 */
public class ThreadPoolExecutorTest {
	public static void main(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
		for (int i = 0; i < 15; i++) {
			MyTask myTask = new MyTask(i);
			executor.execute(myTask);
			System.out.println("线程池中线程数目：" + executor.getPoolSize()
					+ "，队列中等待执行的任务数目：" + executor.getQueue().size()
					+ "，已执行完别的任务数目：" + executor.getCompletedTaskCount());
		}
		executor.shutdown();
	}
}
class MyTask implements Runnable {
	private int taskIndex;//任务编号
	public MyTask(int num) {
		this.taskIndex = num;
	}
	@Override
	public void run() {
		System.out.println("正在执行task " + taskIndex);
		try {
			Thread.currentThread().sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("task " + taskIndex + "执行完毕");
	}
}
