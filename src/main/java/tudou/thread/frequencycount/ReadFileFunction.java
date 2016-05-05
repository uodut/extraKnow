package tudou.thread.frequencycount;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
/* 读取性能测试
* 	文件格式：*.txt
* 	方式：
* 		字符读取：
* 			普通:一次读取一个字符/一次读取多个字符
* 			buffered：一次读取一个字符/一次读取多个字符
* 		按行读取
* 			普通
* 			buffered
* @author tudou
*/
public class ReadFileFunction {
	/**
	 * 按行读取
	 * 
	 * @param file
	 * @throws IOException
	 */
	protected static void readFileByLine(File file) throws IOException {
		InputStreamReader input = null;
		BufferedReader br = null;
		try {
			input = new InputStreamReader(new FileInputStream(file));
			br = new BufferedReader(input);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
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
	/**
	 * 按字节读取，一次读取多个字节
	 * @param fileName
	 */
	protected static void readFileByChars(String fileName) {
		File f = new File(fileName);
		Reader read = null;
		try {
			read = new InputStreamReader(new FileInputStream(f));
			char[] tempChar = new char[100];
			int tempValue;
			while ((tempValue = read.read(tempChar)) != -1) {// read(char[]
				System.out.println(tempValue);
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
