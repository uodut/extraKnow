package tudou.thread.frequencycount;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 多线程读取文件测试，确保每个文件仅被读取一次
 * 
 * @author tudou
 *
 */
public class MultriThreadRead {
	public static void main(String args[]) throws InterruptedException {
		read read = new read();
		new Thread(read, "线程1").start();
		new Thread(read, "线程2").start();
		/*
		 * TimeUnit.SECONDS.sleep(15);
		 * System.out.println(read.getWordMap().size());
		 * read.printMap(read.getWordMap());
		 */
	}
}
class read implements Runnable {
	List<File> filePathsList = new ArrayList<File>();
	int index = 0;
	int currentSize = 0;// TODO
	public read() {
		// File f = new File("d:" + File.separator + "tmp");
		File f = new File("D:/io_read_file/lkb");
		getFileList(f);
		currentSize = filePathsList.size();
	}
	// 得到文件列表
	private void getFileList(File f) {
		File[] filePaths = f.listFiles();
		for (File s : filePaths) {
			if (s.isDirectory()) {// 是一个目录
				getFileList(s);
			} else {
				// int value = s.getName().lastIndexOf(".txt");
				if (-1 != s.getName().lastIndexOf(".txt")) {
					filePathsList.add(s);
				}
			}
		}
	}
	@Override
	public void run() {
		File file = null;
		while (index < filePathsList.size()) {
			synchronized (this) {
				// 防止
				if (index >= filePathsList.size()) {
					continue;
				}
				file = filePathsList.get(index);
				index++;
				currentSize--;
				try {
					Thread.sleep(300);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("当前使用的线程是：" + Thread.currentThread().getName()
								+ '\t' + file.getPath() + '\t' + '\t' + "正在读文件:"
								+ filePathsList.indexOf(file) + ",列表当前长度:"
								+ currentSize);
			}
		}
	}
}