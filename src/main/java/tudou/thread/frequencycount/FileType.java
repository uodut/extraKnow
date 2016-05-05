package tudou.thread.frequencycount;

public enum FileType {
	TXT(".txt"),
	DOC(".doc"),
	DOCX(".docx");
	
	private String type;
	//枚举类型的构造函数只能私有
	private FileType(String realType){
		this.type = realType;
	}
	public String getType() {
		return type;
	}
	public void setType(String realType) {
		this.type = realType;
	}
}
