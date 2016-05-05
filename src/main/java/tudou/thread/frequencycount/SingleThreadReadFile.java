package tudou.thread.frequencycount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 用处：
 * 	1.验证多线程读取的准确性
 * 	2.对比时间效率问题
 * @author tudou
 *
 */
public class SingleThreadReadFile {
	DataHandle dataHandle = new DataHandle();
	/**
	 * 按行读取文件单线程
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
				String[] words = dataHandle.wordSegment(line, " ");
				for (String word : words) {
					dataHandle.wordCount(word);
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
}
