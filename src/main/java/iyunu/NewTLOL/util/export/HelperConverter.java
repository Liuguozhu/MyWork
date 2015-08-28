package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.helper.res.DailyInfo;
import iyunu.NewTLOL.model.helper.res.HelperAwardRes;
import iyunu.NewTLOL.util.json.JsonExproter;

/**
 * 小助手
 * 
 * @author SunHonglei
 * 
 */
public final class HelperConverter {

	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" + serverRes + "/小助手.xlsx";

//		String invasionMonster = "src/main/resources/json/" + serverRes + "/HelperRes.json.txt";
//		JsonExproter.convertToJsonFile(xlsxFile, invasionMonster, HelperRes.class, "小助手");
		String invasionSite = "src/main/resources/json/" + serverRes + "/HelperAwardRes.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, invasionSite, HelperAwardRes.class, "活跃度");
		String dailyInfo = "src/main/resources/json/" + serverRes + "/DailyInfo.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, dailyInfo, DailyInfo.class, "日常活动");
	}
}