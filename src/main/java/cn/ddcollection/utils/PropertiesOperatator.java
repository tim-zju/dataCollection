package cn.ddcollection.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.ddcollection.model.AccessKey;
import cn.ddcollection.model.Region;

/**
 * 
 * @ClassName: PropertiesOperatator
 * @Description: Propertie文件操作类
 * @author: 大鹤
 * @date: 2016年2月24日 下午9:50:36
 */
public class PropertiesOperatator {
	/**
	 * 
	 * @Title: GetValueByKey
	 * @Description: 根据Key读取Value
	 * @param filePath
	 * @param key
	 * @return
	 * @return: String
	 */
	public static String GetValueByKey(String filePath, String key) {
		Properties pps = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			pps.load(in);
			String value = pps.getProperty(key);
			System.out.println(key + " = " + value);
			return value;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Title: GetAllProperties
	 * @Description: 读取Properties的全部信息
	 * @param filePath
	 * @throws IOException
	 * @return: void
	 */
	public static Map<String, String> GetAllProperties(String filePath) throws IOException {
		Map<String, String> contentMap = new HashMap<String, String>();
		Properties pps = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		pps.load(in);
		Enumeration<?> en = pps.propertyNames(); // 得到配置文件的名字

		while (en.hasMoreElements()) {
			String strKey = (String) en.nextElement();
			String strValue = pps.getProperty(strKey);
			contentMap.put(strKey, strValue);
		}
		return contentMap;
	}

	/**
	 * 
	 * @Title: WriteProperties
	 * @Description: 写入Properties信息
	 * @param filePath
	 * @param pKey
	 * @param pValue
	 * @throws IOException
	 * @return: void
	 */
	public static void WriteProperties(String filePath, String pKey, String pValue) throws IOException {
		Properties pps = new Properties();
		InputStream in = new FileInputStream(filePath);
		// 从输入流中读取属性列表（键和元素对）
		pps.load(in);
		// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
		// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
		OutputStream out = new FileOutputStream(filePath);
		pps.setProperty(pKey, pValue);
		// 以适合使用 load 方法加载到 Properties 表中的格式，
		// 将此 Properties 表中的属性列表（键和元素对）写入输出流
		pps.store(out, "Update " + pKey + " name");
	}

	/**
	 * 
	 * @Title: getUserName
	 * @Description: 获取配置文件中用户名和一对awsAccessKey和awsSecretAccessKey
	 * @param filePath
	 * @return
	 * @return: List<AccessKey>
	 */
	public static List<AccessKey> getUserName(String filePath) {
		Properties pps = new Properties();
		InputStream in;
		List<AccessKey> accessKeys = new ArrayList<AccessKey>();
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			pps.load(in);
			Enumeration<?> en = pps.propertyNames(); // 得到配置文件的名字
			Map<String, String> userNamesMap = new HashMap<String, String>();
			while (en.hasMoreElements()) {
				String strKey = (String) en.nextElement();
				String strValue = pps.getProperty(strKey);
				userNamesMap.put(strKey, strValue);
				accessKeys.add(new AccessKey(strKey, strValue));
			}
			return accessKeys;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Title: getRegionName
	 * @Description: TODO获取regions
	 * @param filePath
	 * @return
	 * @return: List<Region>
	 */
	public static List<Region> getRegionName(String filePath) {
		Properties pps = new Properties();
		InputStream in;
		List<Region> regions = new ArrayList<Region>();
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			pps.load(in);
			Enumeration<?> en = pps.propertyNames(); // 得到配置文件的名字
			Map<String, String> userNamesMap = new HashMap<String, String>();
			while (en.hasMoreElements()) {
				String strKey = (String) en.nextElement();
				String strValue = pps.getProperty(strKey);
				userNamesMap.put(StringTranscoding.decodeUnicode(strKey), strValue);
				regions.add(new Region(strKey, strValue));
			}
			return regions;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getUserkey(String username,String filePath) {
		Properties pps = new Properties();
		InputStream in;
		String value=null;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			pps.load(in);
			Enumeration<?> en = pps.propertyNames(); // 得到配置文件的名字
			
			while (en.hasMoreElements()) {
				String strKey = (String) en.nextElement();
				if(strKey.equals(username)){
					value = pps.getProperty(strKey);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return value;
	}
}
