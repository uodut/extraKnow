package tudou.thread;
/**
 * pojo
 * @author tudou
 *	Integer.MAX_VALUE:2^31 
 */
public class _File {
	private String formate;//文件格式 txt word xml
	private long size;//文件大小
	private String filename;//文件名
	private String filepath;//文件路径
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFormate() {
		return formate;
	}
	public void setFormate(String formate) {
		this.formate = formate;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
}
