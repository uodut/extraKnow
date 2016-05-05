package tudou.thread.frequencycount;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Random;
import cn.nubia.framework.util.StringUtil;

/**
 * 生成需要读取的文件 
 * 	文件格式 
 * 		*.txt 
 *  文件内容 
 *  	多key小容量:multiKeySmall 
 *  	少key大容量:lessKeyBig
 * @author tudou
 *
 */
public class MakeFileUtil {
	static Random randomContent = new Random();
	static final String seed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
	static final String seedWithSign = "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ~!@#$%^&*()_+<>?:/.,";
	//50W单词 10M左右  size=50000	15M
	private static  String mksFolderPath = "D:/io_read_file/mks";// 多key小容量路径
	private static String lkbFolderPath = "D:/io_read_file/lkb";//少key大容量路径
	private static long lkbSize=10000000;//190M
	private static long mksSize=50000;//15M
	private static final String Lkb = "Lkb";
	private static final String mks = "mks";
	private static final int mkscolumns = 10;//每行生成的单词数
	private static StringBuilder sBuilder = new StringBuilder();
	public static void main(String[] args) {
//		makeFiles(lkbFolderPath,10,5);
		makeFiles(mksFolderPath,10,5);
	}
	/**
	 * 生成多个文件夹，每个文件夹下生成多个文件
	 * @param folders
	 * @param files
	 */
	//TODO
	static void makeFiles(String folderPath,int folders,int files){
		//不允许直接在根目录下面生成文件
		if(folders <=0 && files >=0){
			return;
		}
		//文件夹和文件数量的容错判断
		sBuilder.append(folderPath);//文件夹路径
		for (int i = 1; i <= folders; i++) {
			//文件夹路径
			if(folderPath.equals(lkbFolderPath)){
				makeFolders(sBuilder,Lkb + "_"+i);
			}else{
				makeFolders(sBuilder,mks + "_"+i);
			}
			for (int j = 1; j <= files; j++) {
				//文件路径
				if(folderPath.equals(lkbFolderPath)){
					lessKeyBig(appendPath(sBuilder,Lkb + "_"+i+"_"+j+".txt"),lkbSize);
				}else if(folderPath.equals(mksFolderPath)){
					multiKeySmall(appendPath(sBuilder,mks + "_"+i+"_"+j+".txt"),mksSize);
				}else{
					System.out.println("出错了");
				}
				//清除上次的文件名称
				sBuilder.delete(sBuilder.toString().lastIndexOf("/"), sBuilder.length());
			}
			//清除上次的文件夹名称
			sBuilder.delete(sBuilder.toString().lastIndexOf("/"), sBuilder.length());
		}
	}
	
	/**
	 * 新建文件夹
	 * @param folder
	 * @return boolean
	 */
	public static boolean createFolder(File folder){
		if (!folder.exists()) {
			return folder.mkdirs();
		}
		return true;
	}
	
	/**
	 * 根据路径生成指定名称的文件夹
	 * @param path
	 * @param folerName
	 */
	static void makeFolders(StringBuilder sourcepath,String folerName){
		if(sourcepath != null){
			File folder = new File(appendPath(sourcepath, folerName));
			createFolder(folder);
		}
	}
	
	static String appendPath(StringBuilder sourcePath,String targetPath){
		return sourcePath.append("/"+targetPath).toString();
	}
	/**
	 * 少key大容量
	 * 
	 * @param path
	 */
	protected static void lessKeyBig(String path,long fileSize) {
		try {
			StringBuilder content = new StringBuilder();
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			//100000000 1-2G	10000000 190M
			for (int i = 0; i < fileSize; i++) {
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
	/**
	 * 多key小容量
	 * 
	 * @param path
	 */
	protected static void multiKeySmall(String path,long fileSize) {
		try {
			StringBuilder content = new StringBuilder();
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < fileSize; i++) {
				for (int j = 0; j < mkscolumns; j++) {
					content.append(randomContent() + " ");
				}
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
}
