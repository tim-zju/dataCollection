package cn.ddcollection.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年2月29日 上午11:23:08
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class SerializeUtils {
	
	
	/**
	 * 
	 * @Title: SerializeObject
	 * @Description: 序列化对象
	 * @param o
	 * @param path
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return: void
	 */
	public static void SerializeObject(Object o, String path) throws FileNotFoundException, IOException {
		// ObjectOutputStream
		// 对象输出流，将Person对象存储到E盘的Person.txt文件中，完成对Person对象的序列化操作
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File(path)));
		oo.writeObject(o);
		System.out.println("Person对象序列化成功！");
		oo.close();
	}
	 
	 /**
	  * 
	  * @Title: DeserializePerson
	  * @Description: 反序列化对象
	  * @param path
	  * @return
	  * @throws Exception
	  * @throws IOException
	  * @return: Object
	  */
	public static Object DeserializePerson(String path) throws Exception, IOException {
	        @SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
	                new File(path)));
	        Object o = (Object) ois.readObject();
	        System.out.println("Person对象反序列化成功！");
	        return o;
	    }
}
