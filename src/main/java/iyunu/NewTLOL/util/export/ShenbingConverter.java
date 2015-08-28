package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.shenbing.res.ShenbingAddPropertyRes;
import iyunu.NewTLOL.model.shenbing.res.ShenbingResetRes;
import iyunu.NewTLOL.model.shenbing.res.ShenbingUpRes;
import iyunu.NewTLOL.model.shenbing.res.ShenbingUpStarRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class ShenbingConverter {

	/**
	 * @function 物品资源转换器
	 * @author LuoSR
	 * @date 2013年12月30日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" + serverRes + "/神兵.xlsx";

		String shenbingUpStart = "src/main/resources/json/" + serverRes + "/ShenbingUp.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, shenbingUpStart, ShenbingUpRes.class, "神兵升级");

		String shenbingSubjoin = "src/main/resources/json/" + serverRes + "/ShenbingReset.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, shenbingSubjoin, ShenbingResetRes.class, "神兵重置");

		String shenbingUpStar = "src/main/resources/json/" + serverRes + "/ShenbingUpStar.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, shenbingUpStar, ShenbingUpStarRes.class, "神兵升星");

		String shenbingAddProperty = "src/main/resources/json/" + serverRes + "/ShenbingAddProperty.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, shenbingAddProperty, ShenbingAddPropertyRes.class, "神兵附加属性");
	}

}