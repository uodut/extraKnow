package tudou.mavenTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import tudou.httpclient.HttpClientUltimate;

public class HttpClientUltimate2Test {

	static HttpClientUltimate obj ;
	@Before
	public  void init() {
		obj = new HttpClientUltimate();
	}
	
	@Test
	public void testSetParams() {
		List<String> testValue = new ArrayList<>();
		testValue.add("key,value");
		List<NameValuePair> result = obj.setParams(testValue);
		//这样的话测试出了什么
		for(NameValuePair ele : result) {
			Assert.assertEquals("key", ele.getName());
			Assert.assertEquals("value", ele.getValue());
		}
		
	}
}
