package cn.jiuling.distributedmanagement.utils;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtils {
	/**
	 * get抓取
	 * 一个简单的抓取工具类,使用get请求
	 * @param url
	 * @return response 响应文本
	 */
	public static String get(String url) {
		HttpClient client = new DefaultHttpClient();
		String response = "";
		HttpGet g = new HttpGet(url);
		HttpResponse response1;
		try {
			response1 = client.execute(g);
			HttpEntity entity1 = response1.getEntity();
			InputStream in = entity1.getContent();
			byte[] b = new byte[2048];
			int len = in.read(b);
			response = new String(b, 0, len, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			g.abort();
		}
		return response;
	}
	public static void main(String[] args) {
		String url="http://192.168.1.60/querySlvNodeInfo.php?id=1&sid=0.21323045389726758";
		String res=HttpUtils.get(url);
		System.out.println(res);
	}
}
