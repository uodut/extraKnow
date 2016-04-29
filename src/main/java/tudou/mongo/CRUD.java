package tudou.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

/**
 * mongo基础增删改查
 * 
 * @author tudou
 *
 */
public class CRUD {
	public static void main(String[] args) throws Exception {
		saveEntity();
	}
	//第一种：常规插入操作
	static void basicInsert(){
		
	}
	public static void saveEntity() throws Exception {
		// 第一：实例化mongo对象，连接mongodb服务器 包含所有的数据库

		// 默认构造方法，默认是连接本机，端口号，默认是27017
		// 相当于Mongo mongo =new Mongo("localhost",27017)
		Mongo mongo = new Mongo();

		// 第二：连接具体的数据库
		// 其中参数是具体数据库的名称，若服务器中不存在，会自动创建
		DB db = mongo.getDB("basic_usage");

		// 第三：操作具体的表
		// 在mongodb中没有表的概念，而是指集合
		// 其中参数是数据库中表，若不存在，会自动创建
		DBCollection collection = db.getCollection("user");

		/*// 添加操作
		// 在mongodb中没有行的概念，而是指文档
		BasicDBObject document = new BasicDBObject();

		document.put("id", 1);
		document.put("name", "小明");
		// //然后保存到集合中
		// // collection.insert(document);

		// 当然我也可以保存这样的json串
		
		 * { "id":1, "name","小明", "address": { "city":"beijing", "code":"065000"
		 * } }
		 
		// 实现上述json串思路如下：
		// 第一种：类似xml时，不断添加
		BasicDBObject addressDocument = new BasicDBObject();
		addressDocument.put("city", "beijing");
		addressDocument.put("code", "065000");
		document.put("address", addressDocument);
		// 然后保存数据库中
		collection.insert(document);*/

		// 第二种：直接把json存到数据库中
		
		String jsonTest = "{'id':1,'name':'小明',"
				+ "'address':{'city':'beijing','code':'065000'}" + "}";
		DBObject dbobjct = (DBObject) JSON.parse(jsonTest);
		collection.insert(dbobjct);

	}

}
