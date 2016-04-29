package tudou.thread.practice.thread;
/**
 * 守护线程
 * 
 * 在Java中有两类线程：User Thread(用户线程)、Daemon Thread(守护线程)
 * 用个比较通俗的比如，任何一个守护线程都是整个JVM中所有非守护线程的保姆：
 * 只要当前JVM实例中尚存在任何一个非守护线程没有结束，守护线程就全部工作；只有当最后一个非守护线程结束时，守护线程随着JVM一同结束工作。
 * Daemon的作用是为其他线程的运行提供便利服务，守护线程最典型的应用就是 GC (垃圾回收器)，它就是一个很称职的守护者。
 * User和Daemon两者几乎没有区别，唯一的不同之处就在于虚拟机的离开：如果 User Thread已经全部退出运行了，只剩下Daemon
 * Thread存在了，虚拟机也就退出了。 因为没有了被守护者，Daemon也就没有工作可做了，也就没有继续运行程序的必要了。
 * 
 * 个人理解：效果上就相当于是大臣（守护线程）和主公（非守护线程）的关系（但是这里的主公不止一位），如果大臣守护的主公全部死了，那么大臣的存在也就没有意义了
 * 
 * 非守护线程消亡以后，守护线程也就不再工作了
 * 
 * 注意： 
 * 	(1) thread.setDaemon(true)必须在thread.start()之前设置
 * 	(2)	在Daemon线程中产生的新线程也是Daemon的。 
 * 	(3) 不要认为所有的应用都可以分配给Daemon来进行服务，比如读写操作或者计算逻辑。
 * 
 * 链接：http://blog.csdn.net/shimiso/article/details/8964414
 */
//没有做完的例子，不成功
public class ThreadDaemon {
	public static void main(String[] args) {
		ThreadFunction tFunction = new ThreadFunction();
		Thread t1 = new Thread(tFunction,"主公");
		Thread t2 = new Thread(tFunction,"臣子1");
		Thread t3 = new Thread(tFunction,"臣子2");
		Thread t4 = new Thread(tFunction,"臣子3");
		t2.setDaemon(true);
		t3.setDaemon(true);
		t4.setDaemon(true);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}
