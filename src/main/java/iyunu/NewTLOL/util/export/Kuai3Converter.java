package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.kuai3.res.Kuai3HeZhiRes;
import iyunu.NewTLOL.model.kuai3.res.Kuai3Res;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class Kuai3Converter {

	/**
	 * @function 快3资源转换器
	 * @author LuoSR
	 * @param args
	 * @date 2013年12月30日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/猜猜看数值.xlsx";
		
		String kuai3Num = "src/main/resources/json/" + serverRes+ "/kuai3.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, kuai3Num, Kuai3Res.class, "猜猜看");
		String kuai3HeZhi = "src/main/resources/json/" + serverRes+ "/kuai3HeZhi.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, kuai3HeZhi, Kuai3HeZhiRes.class, "和值");

	}
}