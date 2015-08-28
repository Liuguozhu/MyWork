package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.pay.PayHelp;
import iyunu.NewTLOL.model.pay.PayInfo;
import iyunu.NewTLOL.model.pay.PayRatio;
import iyunu.NewTLOL.util.json.JsonExproter;

public class PayConverter {

	/**
	 * 充值资源转换器
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/充值.xlsx";

		String payInfo = "src/main/resources/json/" + serverRes + "/PayInfo.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, payInfo, PayInfo.class, "充值比例");

		String payRatio = "src/main/resources/json/" + serverRes + "/PayRatio.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, payRatio, PayRatio.class, "充值比率");

		String payHelp = "src/main/resources/json/" + serverRes + "/PayHelp.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, payHelp, PayHelp.class, "充值帮助");
	}

}