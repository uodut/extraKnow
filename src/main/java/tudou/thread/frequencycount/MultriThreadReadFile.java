package tudou.thread.frequencycount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 读写文件处理
 * 	两个版本:
 * 		1.四核：1读3写
 * 		2.八核：2读6写
 * 			2读：按照文件分（T1读取1-half,T2读取half+1-end）
 *  优化点：1、读完成后如果缓存中剩余数据较多，则读线程撤消后加入到写的线程中去
 * @author tudou
 */
public class MultriThreadReadFile implements Runnable {
	// 用来存入和弹出从文本中读取到的数据--线程安全，方便擦除，大小自己设定
	static BlockingQueue<String> wareHouse = new LinkedBlockingQueue<String>(
			1000000);
	static DataHandle dataHandle = new DataHandle();
	static boolean start = true;
	/**
	 * 按行读取文件 多线程处理操作
	 * 
	 * @param file
	 * @throws IOException
	 */
	void readFileByLine(File file) throws IOException {
		InputStreamReader input = null;
		BufferedReader br = null;
		try {
			input = new InputStreamReader(new FileInputStream(file));
			br = new BufferedReader(input);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (start) {
					start();
					start = false;
				}
				String[] words = dataHandle.wordSegment(line, " ");
				for (String word : words) {
					fillQueue(word);
				}
				
			}
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException e) {
			}
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
			}
		}
	}

	
	// 填充队列
	private static void fillQueue(String content) {
		try {
			wareHouse.put(content);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// 如果这边抛出了异常，要保证读出的数据的合理性
			e.printStackTrace();
		}
	}
	// 开启从队列中取数据的线程
	void start() {
		new Thread(this, "取数据线程1").start();
		new Thread(this, "取数据线程2").start();
		new Thread(this, "取数据线程3").start();
	}
	// 中断线程
	static void shutDown() {

	}
	// 线程读取Queue中数据并进行处理
	@Override
	public void run() {
		while (true) {
			try {
				System.out.println(wareHouse.size());
				// 词频统计存入到一个Map中
				dataHandle.wordCount(wareHouse.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	// 写入文件（在主程序中判断读取和操作的线程完成后，开启四个线程写入文件（））
}
