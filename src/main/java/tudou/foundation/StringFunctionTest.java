package tudou.foundation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StringFunctionTest {
	public static void main(String[] args) {
		String dealStr = "wangtuodu";
		//从第三个字符‘n’开始（因为数字下标从零开始），到第五个字符结束（包括第三个字符和第四个字符）
		String finalStr = dealStr.substring(2,5);
		System.out.println(dealStr.toCharArray()[2]);
		System.out.println(finalStr);
		
		//UUID
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		System.out.println(uuid);
		System.out.println(UUID.randomUUID().toString());
		
		System.out.println(test());
	}
	static boolean test(){
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		int count = 0;
		for(String s:list){
			count++;
		}
		if(count == list.size()){
			return true;
		}
		return false;
	}
}
