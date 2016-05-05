package tudou.thread.frequencycount;

import org.junit.Before;
import org.junit.Test;

/**
 * @author tudou
 *
 */
public class DataHandleTest {
	private DataHandle obj;
	@Before
	public void init(){
		obj=new DataHandle();
	}
	
	@Test
	public void testWordSegment(){
		String content ="aa aa bb bb cc dd";
		String regex = " ";
		String[] strings = obj.wordSegment(content, regex);
		for(String s:strings){
			obj.wordCount(s);
		}
		System.out.println(strings.length);
		for(String s:obj.getWordCountMap().keySet()){
			System.out.println(s+"_"+obj.getWordCountMap().get(s));
		}
	}
}
