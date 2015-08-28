package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.skillBook.HuntskillMapInfo;
import iyunu.NewTLOL.model.skillBook.res.HuntskillCoordRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class HuntskillConverter {

	/**
	 * @function 猎技资源转换器
	 * @author LuoSR
	 * @param args
	 * @date 2013年12月30日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/猎技.xlsx";
		
		String huntskillCoord = "src/main/resources/json/" + serverRes + "/HuntskillCoord.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, huntskillCoord, HuntskillCoordRes.class, "猎技宝箱坐标");

		String huntskillMapInfo = "src/main/resources/json/" + serverRes + "/HuntskillMapInfo.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, huntskillMapInfo, HuntskillMapInfo.class, "猎技地图信息");
	}
}