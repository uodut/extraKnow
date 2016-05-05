package tudou.thread.frequencycount;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 程序入口 功能：数据处理线程完成后把Map中的统计结果写入
 * @author tudou
 *
 */
public class Main {
	static MultriThreadReadFile readFile = new MultriThreadReadFile();
	static WriteFile writeFile = new WriteFile();
	static List<String> pathnames = new ArrayList<String>();
	static File file;
	public static void main(String[] args) throws IOException {
		file=new File(Path.readFolderPathName);
		searchFile(file, FileType.TXT.getType());
		for(String pathname:pathnames){
			file=new File(pathname);
			readFile.readFileByLine(file);
		}
		while(MultriThreadReadFile.wareHouse.size()>0){
			
		}
		writeFile.writeFile(Path.writeFilePathName,MultriThreadReadFile.dataHandle.getWordCountMap());
	}
	// 根据根目录文件名称访问文件夹下的所有文件
	protected static void searchFile(File file, String fileType) {
		// 得到文件列表
		File[] files = file.listFiles();
		for (File f : files) {
			// 是一个目录
			if (f.isDirectory()) {
				searchFile(f, fileType);
			} else {
				if (-1 != f.getName().lastIndexOf(fileType)) {
					pathnames.add(f.getAbsolutePath());
				}
			}
		}
	}
}
