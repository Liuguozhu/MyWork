package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.gift.res.LevelGiftRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class LevelGiftConverter {

	/**
	 * @function 等级礼包资源转换器
	 * @author LuoSR
	 * @date 2014年4月29日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/等级礼包.xlsx";

		String levelGift = "src/main/resources/json/" + serverRes + "/LevelGift.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, levelGift, LevelGiftRes.class, "等级礼包");

	}

}