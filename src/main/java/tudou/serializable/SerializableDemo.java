package tudou.serializable;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableDemo implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;

	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public static void main(String[] args) {
		SerializableDemo myBox = new SerializableDemo();
		myBox.setWidth(50);
		myBox.setHeight(30);

		try {
			FileOutputStream fs = new FileOutputStream("foo.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(myBox);
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}