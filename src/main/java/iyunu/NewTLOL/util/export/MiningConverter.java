package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.mining.emoMap.EmoMapDesRes;
import iyunu.NewTLOL.model.mining.emoMap.EmoMapInfoRes;
import iyunu.NewTLOL.model.mining.emoMap.EmoMapRes;
import iyunu.NewTLOL.model.mining.res.MiningRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class MiningConverter {

	/**
	 * @function 矿区转换器
	 * @author fhy
	 * @date 2014年10月9日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" + serverRes + "/矿区.xlsx";

		String mining = "src/main/resources/json/" + serverRes + "/Mining.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mining, MiningRes.class, "矿区");
		String emoMap1 = "src/main/resources/json/" + serverRes + "/EmoMap1.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, emoMap1, EmoMapRes.class, "恶魔岛怪物坐标1");
		String emoMap2 = "src/main/resources/json/" + serverRes + "/EmoMap2.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, emoMap2, EmoMapRes.class, "恶魔岛怪物坐标2");
		String emoMapInfo = "src/main/resources/json/" + serverRes + "/EmoMapInfo.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, emoMapInfo, EmoMapInfoRes.class, "恶魔岛地图");
		String emoMapDes = "src/main/resources/json/" + serverRes + "/EmoMapDes.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, emoMapDes, EmoMapDesRes.class, "恶魔岛描述");
	}

}