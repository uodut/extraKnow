package tudou.thread.frequencycount;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class MainTest {
	private Main obj;
	@Before
	public void init(){
		obj = new Main();
	}
	@Test
	public void testSearchFile(){
		File file = new File(Path.readFolderPathName);
		Main.searchFile(file,FileType.TXT.getType());
	}
}
