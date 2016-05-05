package tudou.thread.frequencycount;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MakeFile {
	private static MakeFileUtil obj = null;
	StringBuilder stringBuilder = new StringBuilder();
	@Before
	public void init() {
		obj = new MakeFileUtil();
		stringBuilder.append("D:/io_read_file/lkb");
	}
	
	@Ignore
	public void  testMakeFolders() {
		
		String folerName = "test1";
		MakeFileUtil.makeFolders(stringBuilder, folerName);
	}
	@Ignore
	public void testLastIndexof(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("abcadfsd");
		System.out.println(stringBuilder.delete(stringBuilder.toString().lastIndexOf("a"), stringBuilder.length()));
	}
	@Test
	public void testMakeFiles(){
		//MakeFileUtil.makeFiles(5, 5, 50000);
	}
}
