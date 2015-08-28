package iyunu.NewTLOL.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class JsonTool {

	public static String encode(Object object) {
		return JSON.toJSONString(object);
	}

	public static <T> T decode(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}

	public static <T> T decode(String json, TypeReference<T> type) {
		return JSON.parseObject(json, type);
	}
}
