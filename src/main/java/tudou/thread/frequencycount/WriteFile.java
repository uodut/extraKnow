package tudou.thread.frequencycount;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.Set;

/**
 * 写入文件
 * @author tudou
 *
 */
public class WriteFile {
	/**
	 * 读取wordMap填入到指定文件中
	 */
	void writeFile(String pathname, Map<String, Long> wordMap) {
		try {
			File file = new File(pathname);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			Set<String> keys = wordMap.keySet();
			bw.write("单词名称"+'\t'+"出现次数");
			for (String key : keys) {
				bw.write(key + '\t' + wordMap.get(key) + '\r');
			}
			bw.close();
			System.out.println("write Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
