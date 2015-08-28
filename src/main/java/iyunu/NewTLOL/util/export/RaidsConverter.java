package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.raids.res.RaidsInfoRes;
import iyunu.NewTLOL.model.trials.res.TrialsInfoRes;
import iyunu.NewTLOL.util.json.JsonExproter;

/**
 * 副本资源加载器
 * 
 * @author SunHonglei
 * 
 */
public class RaidsConverter {

	/**
	 * 副本资源转换器
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/副本.xlsx";
		
		String transcript = "src/main/resources/json/" + serverRes + "/Raids.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, transcript, RaidsInfoRes.class, "副本");

		String trials = "src/main/resources/json/" + serverRes + "/Trials.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, trials, TrialsInfoRes.class, "试炼");
	}

}