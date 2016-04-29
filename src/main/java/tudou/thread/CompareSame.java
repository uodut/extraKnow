package tudou.thread;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

//比较读取的文件是否有相同的行
public class CompareSame {
	static Set<String> lines = new HashSet<String>();
	static int count= 0;
	public static void main(String[] args) {
		readFileByLine("../foundation/src/main/java/tudou/thread/data");
		System.out.println(count);
		System.out.println(Integer.MAX_VALUE);//2147483647
		//String info = lines.size()==count?"没有重复数据":"有重复数据";
		//System.out.println(info);
		System.out.println(lines.size()==count?"没有重复数据":"有重复数据");
	}
	static void readFileByLine(String fileName) {
		File f = new File(fileName);
		InputStream is = null;
		try {
			RandomAccessFile raf = new RandomAccessFile(f, "r");
			String temp = null;
			while ((temp = raf.readLine()) != null) {
				lines.add(temp);
				count++;
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
