package tudou.foundation;

public abstract class Tudou implements Person{
	public String getName(){
		return "抽象类Tudou实现Person接口的getName()方法";
	}
	public final int getAge(){
		return 0;
	}
}
