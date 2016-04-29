package tudou.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.codec.binary.StringUtils;

import cn.nubia.framework.util.StringUtil;

/**
 * 对读取的到文件里的内容进行临时存储，读取完成后根据存储的信息写入到文件中展示
 * 
 * @author tudou
 *
 */
public class WordList implements Runnable {
	/*
	 * private IOTest ioTest;
	 * 
	 * public WordList(IOTest ioTest){ this.ioTest = ioTest; }
	 */
	public WordList() {
	}

	private ConcurrentMap<String, Long> wordMap = new ConcurrentHashMap<String, Long>();

	public ConcurrentMap<String, Long> getWordsMap() {
		return wordMap;
	}

	public void setWordsMap(ConcurrentMap<String, Long> wordsMap) {
		this.wordMap = wordsMap;
	}

	/**
	 * 填入word到Map中并计数
	 * 
	 * @param key
	 */
	void fillMap(String key) {
		String keyTrim = key.trim();
		if (StringUtil.isNotEmpty(keyTrim)) {
			Set<String> words = wordMap.keySet();
			if (words.isEmpty()) {
				wordMap.put(keyTrim, (long) 1);
			} else {
				//wordMap.putIfAbsent(keyTrim, value)
				if (wordMap.containsKey(keyTrim)) {
					int currentCount = wordMap.get(keyTrim).intValue();
					wordMap.put(keyTrim, (long) ++currentCount);//线程不安全
					//wordMap.putIfAbsent(keyTrim, (long)++currentCount);//
				} else {
					wordMap.put(keyTrim, (long) 1);
				}
			}
		}
	}
	/**
	 * 读取wordMap填入到指定文件中
	 */
	void takeContent(ConcurrentMap<String, Long> wordMap, String filepath) {
		if (IOTest.getTempWareHouse().isEmpty()) {//确定已经读取完毕
			try {
				File file = new File(filepath);
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);

				Set<String> keys = wordMap.keySet();
				for (String key : keys) {
					// System.out.println(key+"---"+source.get(key));
					bw.write(key + "---" + wordMap.get(key)+'\r');
				}
				bw.close();
				System.out.println("Done");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 如果临时仓库中有数据，则读取，如果没有，等待
	int count = 0;
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		
		while (true) {//这个是保证线程在处于一直就绪状态，以及和其它线程切换的条件的
			
			String line = "";
			try {
				line = IOTest.getTempWareHouse().take();
				count++;
				System.out.println(count);
				//System.out.println(line+count+"---"+IOTest.tempWareHouse.size());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] strings = line.split(" ");
			for (String str : strings) {
				fillMap(str);//耗时
			}
			
		}
		
		//如果为空
		
		/*while(IOTest.getTempWareHouse().isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		/*
		 * try { wait(); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

	}
}
