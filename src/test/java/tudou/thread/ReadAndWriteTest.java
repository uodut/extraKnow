package tudou.thread;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ReadAndWriteTest {

	static ReadAndWrite readAndWrite;
	@Before
	public void InitialContext(){
		readAndWrite = new ReadAndWrite();
	}
	
	@Test
	public void testRead() throws IOException{
		File file = new File("../foundation/src/main/java/tudou/thread/test");
		ReadAndWrite.read(file);
		String expectContent = "acc";
		//assertEquals(expectContent, readContent);
	}
	
}
