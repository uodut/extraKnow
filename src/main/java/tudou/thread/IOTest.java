package tudou.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.conn.tsccm.WaitingThread;
import org.dom4j.Branch;
import org.junit.Test;
import org.junit.experimental.theories.Theories;

import cn.nubia.framework.util.StringUtil;
/**
 * note: //生成a-b之间的随机数 m=(int)Math.rint(Math.random()*（b-a）+a）
 * 
 */
public class IOTest {
	static final String seed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
	static final String seedWithSign = "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ~!@#$%^&*()_+<>?:/.,";
	static final String filePath = "../foundation/src/main/java/tudou/thread/data";
	static final String filePath1 = "../foundation/src/main/java/tudou/thread/data1";
	static String path = "D:/bigText/100M.txt";
	static String writepath = "D:/bigText/1Gresult1.txt";
	public static final long ONE_KB = 1024;
	public static final long ONE_MB = ONE_KB * ONE_KB;
	static final long limitSize = 106 * ONE_MB;// 106M
	public static void main(String[] args)
			throws IOException, InterruptedException {
		execute(4);
	}
	static void execute(int direction) {
		switch (direction) {
			case 1 :// 写文件
				write(path);
				break;
			case 2 :// reader一次读取多个字节方式读文件
				break;
			case 3 :// buffer方式读文件
				
				File file = new File(path);
				try {
					read(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 等线程处理完后操作--TODO
				while (!tempWareHouse.isEmpty()) {

				}
				// size()操作比较耗时
				if (tempWareHouse.isEmpty()) {
					System.out.println(wordList.getWordsMap().size());
				}
				

				
				wordList.getWordsMap().clear();
				break;
			case 4 :// 把处理好的数据写入到文件中
				long startTime = System.currentTimeMillis();
				File file1 = new File(path);
				try {
					read(file1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 等线程处理完后操作--TODO
				while (!tempWareHouse.isEmpty()) {

				}
				if (tempWareHouse.isEmpty()) {
					System.out.println(wordList.getWordsMap().size());
				}
				// write(writepath, wordList.getWordsMap());
				wordList.takeContent(wordList.getWordsMap(), writepath);
				long wasteTime = System.currentTimeMillis() - startTime;
				System.out.println("总共用时：" + wasteTime);
				wordList.getWordsMap().clear();
				break;
			default :
				break;
		}
	}
	static Random randomContent = new Random();
	// 随机文件内容生成
	static String randomContent() {
		// 随机生成给定内容
		int subBeginIndex = randomContent.nextInt(seed.length());
		String newRandomStr = randomStr(seed).substring(subBeginIndex);
		if (!StringUtil.isEmpty(newRandomStr)) {
			return newRandomStr;
		}
		return null;
	}
	// 随机给定的字符串
	static String randomStr(final String str) {
		if (!StringUtil.isEmpty(str)) {
			char[] arr = str.toCharArray();
			char[] newArr = new char[str.length()];
			Random random = new Random();
			for (int i = 0; i < arr.length; i++) {
				// 每次取出一个随机值赋值给新数组
				newArr[i] = arr[random.nextInt(arr.length)];
			}
			return String.valueOf(newArr);
		}
		return null;
	}
	// 写入数据到指定文件中(生成比较大的文本)
	static void write(final String filePath) {
		try {
			StringBuilder content = new StringBuilder();
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			/*
			 * for (int i = 0; i < 400000; i++) {// 100M左右 for (int j = 0; j <
			 * 10; j++) {// 每行十个数据 content.append(randomContent() + " "); }
			 * content.append('\r'); bw.write(content.toString());
			 * content.delete(0, content.length()); }
			 */
			for (int i = 0; i < 100000000; i++) {// 1-2G左右
				content.append("this is a good test");
				content.append('\r');
				bw.write(content.toString());
				content.delete(0, content.length());
			}
			bw.close();
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 写入数据到指定文件中(生成比较大的文本)
	static void write(final String filePath,
			ConcurrentMap<String, Long> source) {
		try {
			// StringBuilder content = new StringBuilder();
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			/*
			 * for (int i = 0; i < 500000; i++) {// 2.5g左右 for (int j = 0; j <
			 * 10; j++) {// 每行十个数据 content.append(randomContent() + " "); }
			 * content.append('\r'); bw.write(content.toString());
			 * content.delete(0, content.length()); }
			 */
			Set<String> keys = source.keySet();
			for (String key : keys) {
				// System.out.println(key+"---"+source.get(key));
				bw.write(key + "---" + source.get(key));
			}

			bw.close();
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 用来存入和弹出从文本中读取到的数据--线程安全，方便擦除,无界队列，大小没有控制
	protected static BlockingQueue<String> tempWareHouse = new LinkedBlockingQueue<String>(
			1000000);
	// private static Queue<String> tempWareHouse = new
	// ConcurrentLinkedQueue<String>();
	public static BlockingQueue<String> getTempWareHouse() {
		return tempWareHouse;
	}
	static WordList wordList = new WordList();
	// 线程正在运行标志位
	static boolean needStart = true;
	static void method(File file) {
		// read(file);
	}
	// IO读取文件性能测试
	public static void read(File file) throws IOException {
		/*
		 * if(file.length()>limitSize){//单个文件读取限定为100M
		 * System.out.println("文件超过106M,太大了读不了"); return; }
		 */
		InputStreamReader input = null;
		BufferedReader br = null;
		try {
			input = new InputStreamReader(new FileInputStream(file));
			br = new BufferedReader(input);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				// 定义一个队列来存存储读取到的数据
				// 开启线程来对读取的数据进行计算和移除
				// tempWareHouse.
				try {
					tempWareHouse.put(line);
					//System.out.println("tempWareHouse当前的数量为："+tempWareHouse.size());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // 以行为单位进行存储

				if (!tempWareHouse.isEmpty() && needStart) {// 如果不为空
					// 开启线程读取内容
					new Thread(wordList, "线程1").start();
					/*new Thread(wordList, "线程2").start();
					new Thread(wordList, "线程3").start();
					new Thread(wordList, "线程4").start();*/
					// new Thread(wordList,"线程2").start();
					needStart = false;
				} else {
					
				}
			}
			/*
			 * while (!tempWareHouse.isEmpty()) { //等待 }
			 */
			// 关闭线程
			// 标志位复原
			// needStart = true;
			System.out.println("程序执行完毕");
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

	static void readFileByChars(String fileName) {
		File f = new File(fileName);
		Reader read = null;
		try {
			read = new InputStreamReader(new FileInputStream(f));
			char[] tempChar = new char[100];
			int tempValue;
			while ((tempValue = read.read(tempChar)) != -1) {// read(char[]
				// System.out.print(tempValue);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {// 关闭流
			if (read != null) {
				try {
					read.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
