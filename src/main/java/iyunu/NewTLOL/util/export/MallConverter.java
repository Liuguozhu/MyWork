package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.lessItem.LessItemRes;
import iyunu.NewTLOL.model.mall.res.MallRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class MallConverter {

	/**
	 * @function 商城资源转换器
	 * @author LuoSR
	 * @date 2013年12月30日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" + serverRes + "/商城.xlsx";
		String mallShop = "src/main/resources/json/" + serverRes + "/MallShop.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mallShop, MallRes.class, "商店");
		String mallGang = "src/main/resources/json/" + serverRes + "/MallGang.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mallGang, MallRes.class, "帮派商店");
		String mallDrug = "src/main/resources/json/" + serverRes + "/MallDrug.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mallDrug, MallRes.class, "药品");
		String mallCailiao = "src/main/resources/json/" + serverRes + "/MallCailiao.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mallCailiao, MallRes.class, "材料");
		String mallShizhuang = "src/main/resources/json/" + serverRes + "/MallShizhuang.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mallShizhuang, MallRes.class, "时装");
		String mallStone = "src/main/resources/json/" + serverRes + "/MallStone.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mallStone, MallRes.class, "宝石");
		String mallOther = "src/main/resources/json/" + serverRes + "/MallOther.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mallOther, MallRes.class, "其他");
		String mallCoin = "src/main/resources/json/" + serverRes + "/MallCoin.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mallCoin, MallRes.class, "银两商城");
		String mallVip = "src/main/resources/json/" + serverRes + "/MallVip.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mallVip, MallRes.class, "Vip专属");
		String lessItem = "src/main/resources/json/" + serverRes + "/LessItem.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, lessItem, LessItemRes.class, "单独购买");
	}
}