package com.cyb.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyUtil {

	private static Properties p = null;
	static Log log = LogFactory.getLog(PropertyUtil.class);
	public synchronized static void init(String propertyName) throws Exception {
		InputStream inputstream = null;
		try {
			if (p == null) 
			{
				p = new Properties();
				String filePath = Contants.WEBPATH + "WEB-INF"+File.separator +"classes"+ File.separator + propertyName + ".properties";
				log.info("初始化属性文件:"+filePath);
				inputstream = new FileInputStream(filePath);
				p.load(inputstream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(inputstream!=null){
				inputstream.close();
				inputstream = null;
			}
		}
	}
public static void test(){
	String FILE_NAME = "App.properties";
//	PropertyUtil.this.class.getClass().getResourceAsStream(FILE_NAME) ;
	/*InputStream inputstream = Properties.class.getClassLoader().getResourceAsStream(FILE_NAME);
	String path1 = PropertyUtil.class.getClass().getResource(FILE_NAME).getPath();
	String path2 = PropertyUtil.class.getResource(FILE_NAME).getPath();
	String path3 = PropertyUtil.class.getClass().getResource("").getPath();*/
}
public static String getValueByKey(String propertyName, String key) {
		String result = "";
		try {
			if(p==null){
				init(propertyName);
			}
			result = p.getProperty(key);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
public static String get(String key) {
	String result = "";
	try {
		if(p==null){
			init("App");
		}
		result = getValueByKey("App",key);
		return result;
	} catch (Exception e) {
		e.printStackTrace();
		return "";
	}
}

	public static void main(String[] s) {
		 System.out.println(PropertyUtil.getValueByKey("App.properties","cfcenter"));
	}
}