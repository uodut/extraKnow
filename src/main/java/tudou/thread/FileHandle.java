package tudou.thread;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 文件处理类
 * @author tudou
 *
 */
public class FileHandle {
	private static final String FILE = "file";
	private static final String FOLDER = "folder";
	private static final String ALL = "all";
	public static final long ONE_KB = 1024;
	public static final long ONE_MB = ONE_KB * ONE_KB;
	public static final long ONE_GB = ONE_KB * ONE_MB;
	
	/**
	 * 文件显示大小
	 * @param size
	 * @return
	 */
    public static String byteCountToDisplaySize(long size) {
        String displaySize;
        if (size / ONE_GB > 0) {
            displaySize = String.valueOf(size / ONE_GB) + " GB";
        } else if (size / ONE_MB > 0) {
            displaySize = String.valueOf(size / ONE_MB) + " MB";
        } else if (size / ONE_KB > 0) {
            displaySize = String.valueOf(size / ONE_KB) + " KB";
        } else {
            displaySize = String.valueOf(size) + " bytes";
        }
        return displaySize;
    }
	
	/**
	 * 得到文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	protected static String getFileExtensionName(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.') + 1);
	}
	
	/**
	 * 得到某个文件夹下的所有文件及文件夹列表
	 */
	protected static List<File> getAll(File folder) {
		return getAll(folder, ALL);
	}
	/**
	 * 得到文件夹下的所有文件列表,不包括子文件夹
	 * 
	 * @param folder
	 *            文件夹名
	 * @return List<File> 文件列表
	 */
	protected static List<File> getFiles(File folder) {
		return getAll(folder, FILE);
	}
	/**
	 * 得到某个文件夹下的所有文件夹列表
	 * 
	 * @param folder
	 *            文件夹名
	 * @return List<File> 文件夹列表
	 */
	protected static List<File> getFolders(File folder) {
		return getAll(folder, FOLDER);
	}
	private static List<File> getAll(File folder, String type) {
		List<File> list = new LinkedList<File>();
		File flist[] = folder.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (type.equals(FILE)) {
				if (!flist[i].isDirectory()) {
					list.add(flist[i]);
				}
			} else if (type.equals(FOLDER)) {
				if (!flist[i].isDirectory()) {
					list.add(flist[i]);
				}
			} else {
				list.add(flist[i]);
			}

		}
		return list;
	}
}
