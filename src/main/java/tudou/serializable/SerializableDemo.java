package tudou.serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.junit.Test;

public class SerializableDemo implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String fileName = "foo.ser";
	FileOutputStream fs = null;
	ObjectOutputStream os = null;
	FileInputStream fis = null;
	ObjectInputStream ois = null;
	SerializableDemo myBox;
	private int width;
	private int height;
	@Test
	public void test() {
		init();
		setSerializable(fileName);
		setDeSerializable(fileName);
	}
	private void init() {
		myBox = new SerializableDemo();
		myBox.setWidth(50);
		myBox.setHeight(30);
	}

	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	// 序列化Serializable
	private void setSerializable(String fileName) {
		try {
			fs = new FileOutputStream(fileName);
			os = new ObjectOutputStream(fs);
			os.writeObject(myBox);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (fs != null)
					fs.close();
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	// 反序列化Deserializable
	private void setDeSerializable(String fileName) {
		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			SerializableDemo st1 = (SerializableDemo) ois.readObject();
			System.out.println("Height = " + st1.getHeight());
			System.out.println("Width = " + st1.getWidth());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
				if (fis != null)
					fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
