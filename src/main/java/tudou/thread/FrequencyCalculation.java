package tudou.thread;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * 词频统计
 * 
 * @author tudou
 *
 */
public class FrequencyCalculation implements Runnable {
	private List<File> files;// 文件列表
	private final String filePath = "D:/tmp/file-five";
	private volatile int index = 0;
	@Override
	public void run() {
		while (index < files.size()) {
			synchronized (FrequencyCalculation.class) {
				try {
					Thread.sleep(300);
					readAndRecord();
				} catch (Exception e) {
					e.printStackTrace();
				}
				index++;
				System.out.println(Thread.currentThread().getName());
			}
		}
	}
	public FrequencyCalculation() {
		File file = new File(filePath);
		this.files = getFiles(file);
	}
	/**
	 * 多线程读取指定目录下的文件 分为两种方式： 线程按照文件夹进行读取：所有线程进入一个文件夹后读取完所有的文件后，接着进行下一个文件夹
	 * 线程按照文件读取：不分文件夹读取
	 * 
	 * @throws IOException
	 */
	int i = 0;
	private void readAndRecord() throws IOException {
		if (files.size() <= 0)
			return;
		for (File file : files) {
			i++;
			ReadAndWrite.read(file);
			//System.out.println("文件读取了"+i+"ci");
		}
	}
	private List<File> getFiles(File file) {
		return FileHandle.getFiles(file);
	}

	void printMap(ConcurrentMap<String, Long> wordMap) {
		if (wordMap.size() > 0) {
			Set<String> set = wordMap.keySet();
			for (String str : set) {
				System.out.println("----->" + str + wordMap.get(str));
			}
		} else {
			System.out.println("null");
		}
	}
}
