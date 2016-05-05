package tudou.thread.frequencycount;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import cn.nubia.framework.util.StringUtil;

/**
 * 数据处理
 * 	包括对读出的文件内容进行分词，个数统计和排序
 * @author tudou
 *
 */
public class DataHandle {
	private Map<String,Long> wordCountMap = new ConcurrentHashMap<String,Long>();
//	private AtomicLong atomicLong;
	public Map<String, Long> getWordCountMap() {
		return wordCountMap;
	}
	
	/**
	 * 根据指定的分隔符分割字符串
	 * @param content
	 * @param regex
	 * @return
	 */
	protected String[] wordSegment(String content,String regex){
		if(content!=null){
			if(StringUtil.isEmpty(regex)){
				return null;
			}
			return content.split(regex);
		}
		return null;
	}
	/**
	 * 单词出现次数统计，确保线程安全性
	 * @param key 不会为null
	 */
	protected void wordCount(String key){
		//key:1.存在2.不存在
		Long result = wordCountMap.putIfAbsent(key,(long)0);
		long finalResult = result == null?0:result;
		wordCountMap.replace(key, ++finalResult);
	}
	/**
	 * TODO
	 * @param sourceMap
	 */
	protected void sort(ConcurrentHashMap<String, Long> sourceMap){
		
	}
}
