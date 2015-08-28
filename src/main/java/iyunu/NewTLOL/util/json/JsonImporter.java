package iyunu.NewTLOL.util.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.alibaba.fastjson.TypeReference;

public final class JsonImporter {

	/**
	 * 私有构造函数
	 */
	private JsonImporter() {

	}

	/**
	 * json文件反序列化
	 * 
	 * @param jsonFile
	 *            json文件
	 * @param <T>
	 *            对象泛型
	 * @param typeReference
	 *            返回对象的类型
	 * @return 反序列化后的对象
	 */
	public static <T> T fileImporter(String jsonFile, TypeReference<T> typeReference) {
		File file = new File(jsonFile);
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				sb.append(str);
			}
			T data = JsonTool.decode(sb.toString(), typeReference);
			bufferedReader.close();
			return data;

		} catch (IOException ie) {
			ie.printStackTrace();
			return null;
		}
	}
}
