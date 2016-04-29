package tudou.thread.practice.sync;

/**
 * 建立三个线程，A线程打印10次A，B线程打印10次B,C线程打印10次C，要求线程同时运行，交替打印10次ABC。
 * @author tudou
 * 知识点：
 * 	Synchoronized:
 * 	wait():属于obj的方法，线程阻塞，主动释放对象锁
 *  notify()/notifyAll()：唤醒阻塞线程，但是没有得到对象锁，要等到Synchronized方法结束后根据系统调度来确定调用哪个线程
 *  sleep():
 */
public class SyncExample1 implements Runnable {

	private String name;
	private Object prev;
	private Object self;

	private SyncExample1(String name, Object prev, Object self) {
		this.name = name;
		this.prev = prev;
		this.self = self;
	}

	@Override
	public void run() {
		int count = 10;
//		程序运行的主要过程就是A线程最先运行，持有C,A对象锁，后释放A,C锁，唤醒B??什么意思。
//		线程B等待A锁，再申请B锁，后打印B，再释放B，A锁，唤醒C，线程C等待B锁，再申请C锁，后打印C，再释放C,B锁，唤醒A。
		while (count > 0) {//总共运行次数为10次，说明每次while循环都执行了三次打印name的操作
			synchronized (prev) {//c//a
				synchronized (self) {//a//b
					System.out.print(name+"---");//A//B
					count--;//9//8
					self.notify();//pa.notify()
					System.out.println("notify线程"+Thread.currentThread().getName());
				}
				try {
					//System.out.println("up to this");
					prev.wait();
					System.out.println("wait线程"+Thread.currentThread().getName());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("******************"+count+"***********************");
		}
	}

	public static void main(String[] args) throws Exception {
		Object a = new Object();
		Object b = new Object();
		Object c = new Object();
		SyncExample1 pa = new SyncExample1("A", c, a);
		SyncExample1 pb = new SyncExample1("B", a, b);
		SyncExample1 pc = new SyncExample1("C", b, c);

		//SynchronizedDemo继承Runnable()接口，实现了run()方法，所以start()方法调用target.run()方法即pa.run()方法
		/*
		 * question:
		 * 	1、pa pb pc作为类SynchronizedDemo的三个实例，他们所开启的线程之间有关系吗？
		 */
		new Thread(pa,"线程1").start();//相当于pa.start()
		Thread.sleep(2000);
		new Thread(pb,"线程2").start();
		Thread.sleep(2000);
		new Thread(pc,"线程3").start();
		Thread.sleep(2000);
	}
}
