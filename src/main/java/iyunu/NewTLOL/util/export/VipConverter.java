package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.vip.VipDesc;
import iyunu.NewTLOL.util.json.JsonExproter;

/**
 * 活动
 * 
 * @author SunHonglei
 * 
 */
public final class VipConverter {

	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/Vip描述.xlsx";
		
		String VipDesc = "src/main/resources/json/" + serverRes + "/VipDesc.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, VipDesc, VipDesc.class, "VIP描述");
	}
}