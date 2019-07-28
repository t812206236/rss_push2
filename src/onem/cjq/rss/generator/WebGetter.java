package onem.cjq.rss.generator;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import onem.cjq.rss.generator.utils.SslUtils;

public class WebGetter {

	public WebGetter() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取静态网页的方法，有些网页不能正常访问，需要优化
	 * @param webPath
	 * @param encode
	 * @return
	 * @throws Exception 网络无法连接异常
	 */
	public String get(String webPath, String encode) throws Exception {
		String result = "";

		URL url = new URL(webPath);
		if ("https".equalsIgnoreCase(url.getProtocol())) {
			SslUtils.ignoreSsl();
		}
		URLConnection urlc = url.openConnection();
		urlc.setConnectTimeout(10000);
		urlc.setReadTimeout(20000);
		urlc.setRequestProperty("user-agent",
				"Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:48.0) Gecko/20100101 Firefox/48.0");
		InputStream ins = urlc.getInputStream();
		String contentEncoding = urlc.getHeaderField("Content-Encoding");
		if (contentEncoding != null) {
			if (contentEncoding.toLowerCase().contains("gzip")) {
				ins = new GZIPInputStream(ins);
			}
		}
		InputStreamReader insr = new InputStreamReader(ins, encode);
		Scanner s = new Scanner(insr);
		while (s.hasNext()) {
			result += s.nextLine() + "\n";
		}
		s.close();

		return result;
	}
}
