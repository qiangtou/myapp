package cn.jiuling.distributedmanagement.utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	public static Logger logger = Logger.getLogger(PropertiesUtil.class);

	public static String get(String key) {
		String value = "";
		Properties p = new Properties();
		try {
			InputStream fis = PropertiesUtil.class.getClassLoader().getResourceAsStream("parameter.properties");
			p.load(fis);
			value = p.getProperty(key);
		} catch (Exception e) {
			logger.error("读取属性文件出错!!", e);
		}
		return value;
	}

	public static void main(String[] args) {
		String host = PropertiesUtil.get("server.status.host");
		System.out.println(host);
	}

}
