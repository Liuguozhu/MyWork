package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.gift.res.GiftVipRes;
import iyunu.NewTLOL.model.gift.res.LevelGiftRes;
import iyunu.NewTLOL.model.gift.res.SevenGiftRes;
import iyunu.NewTLOL.model.gift.res.SurpriseRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class GiftConverter {

	/**
	 * @function 等级礼包资源转换器
	 * @author LuoSR
	 * @date 2014年4月29日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/礼包.xlsx";

		String levelGift = "src/main/resources/json/" + serverRes + "/LevelGift.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, levelGift, LevelGiftRes.class, "等级礼包");
		String surprise = "src/main/resources/json/" + serverRes + "/Surprise.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, surprise, SurpriseRes.class, "大礼包");
		String giftVip = "src/main/resources/json/" + serverRes + "/GiftVip.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, giftVip, GiftVipRes.class, "VIP奖励");
		String sevenGift = "src/main/resources/json/" + serverRes + "/SevenGift.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, sevenGift, SevenGiftRes.class, "七天");

	}

}