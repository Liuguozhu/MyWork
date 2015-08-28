package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.qiancengta.res.QiancengtaInfoRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class QianCengTaConverter {

	/**
	 * 怪物资源转换器
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/千层塔.xlsx";

		String qiancengtaFile = "src/main/resources/json/" + serverRes + "/Qiancengta.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, qiancengtaFile, QiancengtaInfoRes.class, "千层塔信息");
	}

}