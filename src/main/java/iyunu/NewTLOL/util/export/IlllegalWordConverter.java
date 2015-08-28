package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.base.IlllegalWordInfo;
import iyunu.NewTLOL.util.json.JsonExproter;

/**
 * 屏蔽字
 * 
 * @author SunHonglei
 * 
 */
public final class IlllegalWordConverter {

	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" + serverRes + "/屏蔽字.xlsx";

		String illlegalWordInfo = "src/main/resources/json/" + serverRes + "/IlllegalWord.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, illlegalWordInfo, IlllegalWordInfo.class, "屏蔽字");
	}
}