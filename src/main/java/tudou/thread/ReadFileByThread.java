package tudou.thread;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import cn.nubia.framework.util.StringUtil;
/**
 * 
 * 如果不加sleep()，可能在io没有结束就换了线程，这个需要处理
 * @author tudou
 *
 */
public class ReadFileByThread {
	public static void main(String args[]) throws InterruptedException {
		read read = new read();
		new Thread(read, "线程1").start();
		new Thread(read, "线程2").start();
		new Thread(read, "线程3").start();
		new Thread(read, "线程4").start();
		new Thread(read, "线程5").start();
		TimeUnit.SECONDS.sleep(15);
		System.out.println(read.getWordMap().size());
		read.printMap(read.getWordMap());
	}
}

class read implements Runnable {
	ConcurrentMap<String, Integer> wordMap = new ConcurrentHashMap<>();
	public ConcurrentMap<String, Integer> getWordMap() {
		return wordMap;
	}
	List<File> filePathsList = new ArrayList<File>();
	int index = 0;
	volatile int currentSize = 0;//TODO
	public read() {
		// File f = new File("d:" + File.separator + "tmp");
		File f = new File("D:/tmp");
		getFileList(f);
		currentSize= filePathsList.size();
	}
	void printMap(ConcurrentMap<String, Integer> wordMap){
		if(wordMap.size() > 0){
			Set<String> set = wordMap.keySet();
			for(String str:set){
				System.out.println("----->" +str+wordMap.get(str));
			}
		}else{
			System.out.println("null");
		}
	}
	//得到文件列表
	private void getFileList(File f) {
		File[] filePaths = f.listFiles();
		for (File s : filePaths) {
			if (s.isDirectory()) {// 是一个目录
				getFileList(s);
			} else {
				//int value = s.getName().lastIndexOf(".txt");
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
				//防止
				if (index >= filePathsList.size()) {
					continue;
				}
				file = filePathsList.get(index);
				index++;
				currentSize--;
			
			try {
				Thread.sleep(300);
				//InputStream is = new FileInputStream(file.getPath());
				readFileByLine(file.getPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("当前使用的线程是：" + Thread.currentThread().getName()+  '\t' + file.getPath()
					+ '\t'+ '\t'+"正在读文件:" + filePathsList.indexOf(file) + ",列表当前长度:"
					+ currentSize);
			}
		}
		//printMap();
		//System.out.println(wordMap.size());
	}
	
	
	//填入word到Map中并计数,word去掉多于的空格（空格应该是没有的，再考虑TODO）
	 void fillMap(String key){
		String keyTrim = key.trim();
		if(StringUtil.isNotEmpty(keyTrim)){
			Set<String> words= wordMap.keySet();
			if(words.isEmpty()){
				wordMap.put(keyTrim, 1);
			}else{
				if(wordMap.containsKey(keyTrim)){
					int currentCount = wordMap.get(keyTrim).intValue();
					wordMap.put(keyTrim, ++currentCount);
				}else{
					wordMap.put(keyTrim, 1);
				}
			}
		}
	}
	//对于英文，不考虑隔行断掉的情况，对于中文，不适用
	 void readFileByLine(String fileName) {
		File f = new File(fileName);
		InputStream is = null;
		try {
			RandomAccessFile raf = new RandomAccessFile(f, "r");
			String temp = null;
			while ((temp = raf.readLine()) != null) {
				String[] words = temp.split(" ");
				if(words.length > 0){
					for(String word:words){
						fillMap(word);
					}
				}else{
					continue;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 确保最终会执行该条语句，关闭流
				if (is != null) {
					is.close();// 关闭流
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
