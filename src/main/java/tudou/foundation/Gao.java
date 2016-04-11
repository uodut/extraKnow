package tudou.foundation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;

public class Gao extends Tudou {
	public static void main(String[] args) {
		System.out.println(new Gao().getName());
		try {
			Method[] methods = Class.forName("tudou.foundation.Person").getMethods();
			Field[] fields = Person.class.getFields();
			String modifier;
			for(Method method:methods){
				modifier = Modifier.toString(method.getModifiers());
				System.out.println(modifier );
			}
			for (Field field : fields) {
				modifier = Modifier.toString(field.getModifiers());
				System.out.println(modifier);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
