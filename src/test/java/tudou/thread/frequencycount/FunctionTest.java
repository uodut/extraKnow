package tudou.thread.frequencycount;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 函数测试
 * 	computeIfAbsent
 * @author tudou
 *
 */
public class FunctionTest {
	public static void main(String[] args) {
		Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();
		/*map.computeIfAbsent("a", k->map.size());
		map.computeIfAbsent("a", k->map.size());
		map.computeIfAbsent("b", k->map.size());
		map.computeIfAbsent("c", k->map.size());*/
		
		map.put("a", 1);
		
		Set<String> set=map.keySet();
		for(String key:set){
			System.out.println(key+map.get(key));
		}
	}
}
