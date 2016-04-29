package tudou.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import cn.nubia.framework.entity.Encoding;
import cn.nubia.framework.util.StringUtil;
/**
 * IO读写文件
 * 
 * @author tudou
 *
 */
public class ReadAndWrite {
	// 存储读取到的数据并且计数
	private static ConcurrentMap<String, Long> wordMap = new ConcurrentHashMap<String, Long>();
	public static ConcurrentMap<String, Long> getWordMap() {
		return wordMap;
	}
	/**
	 * 以字符为单位读取文件，一次读取多个字符
	 * 
	 * @param fileName
	 */
	static void readFileByChars(String fileName) {
		File f = new File(fileName);
		Reader read = null;
		try {
			read = new InputStreamReader(new FileInputStream(f));
			char[] tempChar = new char[30];
			int tempValue;
			while ((tempValue = read.read(tempChar)) != -1) {// read(char[]
				// 如果tempChar的长度大于要读取的文件的长度，那么一次性全部读取
				// 如果tempChar的长度小于要读取的文件的长度，那么一次读取tempchars.length个长度
				if (tempChar.length >= tempValue) {
					System.out.print(tempChar);// 字符数组可以直接print
				} else {
					for (int i = 0; i < tempValue; i++) {
						if (tempChar[i] == '\r') {
							continue;
						} else {
							System.out.print(tempChar[i]);
						}
					}
				}
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
	public static void setWordMap(ConcurrentMap<String, Long> wordMap) {
		ReadAndWrite.wordMap = wordMap;
	}
	/**
	 * 以字符为单位读取文件，一次读取单个字符
	 * 
	 * @param fileName
	 */
	static void readFileByChar(String fileName) {
		File f = new File(fileName);
		Reader read = null;
		int temp;
		try {
			read = new InputStreamReader(new FileInputStream(f));
			while ((temp = read.read()) != -1) {// read()方法返回读取到的字符的int值
				// 对于windows下，\r\n这两个字符在一起时，表示一个换行。
				// 但如果这两个字符分开显示时，会换两次行。
				// 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
				if (((char) temp) != '\r') {
					System.out.print((char) temp);
				}
			}
			read.close();// 关闭流
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
	/**
	 * 按行读取文件
	 * 
	 * @param fileName
	 */
	static void readFileByLine(String fileName) {
		File f = new File(fileName);
		InputStream is = null;
		try {
			RandomAccessFile raf = new RandomAccessFile(f, "r");
			String temp = null;
			while ((temp = raf.readLine()) != null) {
				String[] words = temp.split(" ");
				if (words.length > 0) {
					for (String word : words) {
						// fillMap(word);
					}
				} else {
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

	/**
	 * 读取一个文件的内容,默认编码为UTF-8
	 * 
	 * @param fileName
	 *            文件名
	 * @return 返回文件的文本内容
	 * @throws IOException
	 */
	public static void read(File file) throws IOException {
		 read(file, Encoding.UTF_8.name());
	}
	/**
	 * 读取一个文件的内容
	 * 
	 * @param fileName
	 *            文件名
	 * @param encoding
	 *            编码方式
	 * @return 返回文件的文本内容
	 * @throws IOException
	 */
	public static void read(File file, String encoding) throws IOException {
		InputStreamReader input = null;
		BufferedReader br = null;
		try {
			input = new InputStreamReader(new FileInputStream(file), encoding);
			br = new BufferedReader(input);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				if(words.length > 0){
					for(String word:words){
						fillMap(word);
					}
				}else{
					continue;
				}
			}
			//return sb.toString();
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
	/**
	 * 填入word到Map中并计数
	 * 
	 * @param key
	 */
	static void fillMap(String key) {
		String keyTrim = key.trim();
		if (StringUtil.isNotEmpty(keyTrim)) {
			Set<String> words = wordMap.keySet();
			if (words.isEmpty()) {
				wordMap.put(keyTrim, (long) 1);
			} else {
				if (wordMap.containsKey(keyTrim)) {
					int currentCount = wordMap.get(keyTrim).intValue();
					wordMap.put(keyTrim, (long) ++currentCount);
				} else {
					wordMap.put(keyTrim, (long) 1);
				}
			}
		}
	}

	/**
	 * 读取wordMap填入到指定文件中
	 */
	static void takeContent(ConcurrentMap<String, Long> wordMap) {

	}
	
}
