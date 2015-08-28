package iyunu.NewTLOL.util.json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.iyunu.xlsxToJson.json.XlsxToObjects;

public final class JsonExproter {

	/**
	 * json文件导出工具类
	 */
	private JsonExproter() {

	}

	private static final int LINE = 4;
	private static Logger logger = LoggerFactory.getLogger(JsonExproter.class);

	/**
	 * 将对象导出为目标文件
	 * 
	 * @param targetFile
	 *            目标文件
	 * @param data
	 *            要导出的对象
	 * @param filters
	 *            属性过滤器
	 */
	public static void fileExporter(String targetFile, Object data, PropertyFilter... filters) {
		File file = new File(targetFile);

		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		for (PropertyFilter filter : filters) {
			serializer.getPropertyFilters().add(filter);
		}

		serializer.write(data);

		try {
			FileOutputStream fos = new FileOutputStream(file);
			out.writeTo(fos, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * xlsx文件中的功能性道具sheet转换成json文件
	 * 
	 * @param xlsxFile
	 *            xlsx文件
	 * @param targetFile
	 *            模板json文件
	 * @param clazz
	 *            对应类
	 * @param sheet
	 *            工作簿名称
	 */
	public static <T> void convertToJsonFile(String xlsxFile, String targetFile, Class<T> clazz, String sheet) {
		List<T> list = XlsxToObjects.toObjects(xlsxFile, LINE, sheet, clazz);

		PropertyFilter filter = new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (!"useables".equals(name)) {
					return true;
				}
				return false;
			}
		};

		JsonExproter.fileExporter(targetFile, list, filter);
		logger.info("生成【" + sheet + "】JSON文件完成！" + targetFile);
	}
}
