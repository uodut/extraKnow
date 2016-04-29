package tudou.thread.practice.thread;

public class ThreadFunction implements Runnable {
	private  int count = 1;
	@Override
	public void run() {
		synchronized (this) {
			while(count < 1000){
				System.out.println(Thread.currentThread().getName() + "---"
						+ Thread.currentThread().getPriority()+"--"+count+"--");
				if(Thread.currentThread().getName().equals("主公")&& count>50){
					System.out.println("进来了");
				}
				count++;
				
			}
		}
		
		
	}
}
