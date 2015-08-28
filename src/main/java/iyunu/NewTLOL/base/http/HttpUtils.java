package iyunu.NewTLOL.base.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: http utils
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author LiLu
 * @version 1.0
 */
public final class HttpUtils {

	private static final String URL_PARAM_CONNECT_FLAG = "&";

	/**
	 * 构造方法
	 */
	private HttpUtils() {
	}

	/**
	 * GET METHOD
	 * 
	 * @param strUrl
	 *            String
	 * @param map
	 *            Map
	 * @throws IOException
	 * @return List
	 * @throws IOException
	 *             IO异常
	 */
	public static List<String> urlGet(String strUrl, Map<String, String> map) throws IOException {
		String strtTotalURL = "";
		List<String> result = new ArrayList<String>();
		if (strtTotalURL.indexOf("?") == -1) {
			strtTotalURL = strUrl + "?" + getUrl(map);
		} else {
			strtTotalURL = strUrl + "&" + getUrl(map);
		}
		System.out.println("TotalURL:" + strtTotalURL);
		URL url = new URL(strtTotalURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		HttpURLConnection.setFollowRedirects(true);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		while (true) {
			String line = in.readLine();
			if (line == null) {
				break;
			} else {
				result.add(line);
			}
		}
		in.close();
		return (result);
	}

	/**
	 * POST METHOD
	 * 
	 * @param strUrl
	 *            String
	 * @param map
	 *            Map
	 * @throws IOException
	 * @return String
	 * @throws IOException
	 *             IO异常
	 */
	public static String urlPost(String strUrl, Map<String, String> map) throws IOException {

		String content = getUrl(map);
		String totalURL = strUrl;
		if (content != null && !content.equals("")) {
			if (strUrl.indexOf("?") == -1) {
				totalURL += "?" + content;
			} else {
				totalURL += "&" + content;
			}
		}

		System.out.println("strUrl:" + strUrl);
		System.out.println("TotalURL:" + totalURL);
		URL url = new URL(totalURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setAllowUserInteraction(false);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
		BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		// bout.write(content);
		bout.flush();
		bout.close();
		BufferedReader bin = new BufferedReader(new InputStreamReader(con.getInputStream()));

		return bin.readLine();
	}

	/**
	 * 获得URL
	 * 
	 * @param map
	 *            Map
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String getUrl(Map map) {
		if (null == map || map.keySet().size() == 0) {
			return "";
		}
		StringBuffer url = new StringBuffer();
		Set keys = map.keySet();
		for (Iterator i = keys.iterator(); i.hasNext();) {
			String key = String.valueOf(i.next());
			if (map.containsKey(key)) {
				Object val = map.get(key);
				String str = val != null ? val.toString() : "";
				try {
					str = URLEncoder.encode(str, "GBK");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = "";
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return strURL;
	}

	/**
	 * @param a
	 *            URL
	 * @throws Exception
	 *             异常
	 */
	public static void visit(String a) throws Exception {
		URL url = new URL(a);

		URLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
		connection.connect();
		InputStream urlStream = connection.getInputStream();

		BufferedReader reader = new BufferedReader(new InputStreamReader(urlStream, "UTF-8"));
		String sCurrentLine = "";

		while ((sCurrentLine = reader.readLine()) != null) {
			// HTTP协议返回三个空行
			if (!"".equals(sCurrentLine)) {
				// System.out.println(sCurrentLine);
			}

			if (sCurrentLine.contains("KRXHLYDivT")) {
				System.out.println(sCurrentLine);
			}
		}
		urlStream.close();
	}
}
