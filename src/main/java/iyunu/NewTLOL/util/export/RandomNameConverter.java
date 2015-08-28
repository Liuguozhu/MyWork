package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.role.RandomName;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class RandomNameConverter {

	/**
	 * 随机名称
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/随机名称.xlsx";
		
		String randomNameCommon = "src/main/resources/json/" + serverRes + "/RandomName.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, randomNameCommon, RandomName.class, "名字");
	}
}