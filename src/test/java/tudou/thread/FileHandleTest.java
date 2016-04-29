package tudou.thread;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class FileHandleTest {

	private static FileHandle obj = null;
	@Before
	public void init() {
		obj = new FileHandle();
	}
	
	@Test
	public void testFileExtensionName(){
		//String extensionName = FileHandle.fileExtensionName("");
		String extensionName1 = FileHandle.getFileExtensionName("asf..sdfsd");
		System.out.println(extensionName1);
		assertEquals("sdfsd", extensionName1);
	}
	
	
	
}
