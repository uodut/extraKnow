package tudou.thread.frequencycount;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * 测试按行读取和一次读取多个字符的效率TODO
 * @author tudou
 *
 */
public class ReadFileFunctionTest {
	private ReadFileFunction obj;
	@Before
	public void init(){
		obj= new ReadFileFunction();
	}
	
	@Test
	public void testReadFileByLine() throws IOException{
		String filePath = "../foundation/src/test/java/tudou/thread/frequencycount/IOTest.txt";
		File file = new File(filePath);
		//ReadFileFunction.readFileByLine(file);
		ReadFileFunction.readFileByChars(filePath);
	}
	
}
