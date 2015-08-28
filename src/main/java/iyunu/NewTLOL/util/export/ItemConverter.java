package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.item.res.BookRes;
import iyunu.NewTLOL.model.item.res.DrawingRes;
import iyunu.NewTLOL.model.item.res.DrugRes;
import iyunu.NewTLOL.model.item.res.EquipRes;
import iyunu.NewTLOL.model.item.res.ItemCostRes;
import iyunu.NewTLOL.model.item.res.ItemGiftRes;
import iyunu.NewTLOL.model.item.res.ItemUseRes;
import iyunu.NewTLOL.model.item.res.StoneRes;
import iyunu.NewTLOL.model.item.res.TaskItemRes;
import iyunu.NewTLOL.model.item.res.VipItemRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class ItemConverter {

	/**
	 * @function 物品资源转换器
	 * @author LuoSR
	 * @date 2013年12月30日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/物品.xlsx";

		String equip = "src/main/resources/json/" + serverRes + "/ItemEquip.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, equip, EquipRes.class, "装备");

		String stone = "src/main/resources/json/" + serverRes + "/ItemStone.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, stone, StoneRes.class, "宝石");

		String book = "src/main/resources/json/" + serverRes + "/ItemBook.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, book, BookRes.class, "技能书");

		String drug = "src/main/resources/json/" + serverRes + "/ItemDrug.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, drug, DrugRes.class, "药品");

		String gift = "src/main/resources/json/" + serverRes + "/ItemGift.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, gift, ItemGiftRes.class, "礼包");

		String taskItem = "src/main/resources/json/" + serverRes + "/ItemTask.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskItem, TaskItemRes.class, "任务物品");

		String materialStone = "src/main/resources/json/" + serverRes + "/ItemUse.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, materialStone, ItemUseRes.class, "使用道具");

		String materialOil = "src/main/resources/json/" + serverRes + "/ItemCost.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, materialOil, ItemCostRes.class, "消耗道具");

		String vip = "src/main/resources/json/" + serverRes + "/ItemVip.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, vip, VipItemRes.class, "VIP");

		String drawing = "src/main/resources/json/" + serverRes + "/ItemDrawing.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, drawing, DrawingRes.class, "藏宝图");

		String shenbing = "src/main/resources/json/" + serverRes + "/ItemShenbing.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, shenbing, EquipRes.class, "神兵");

		String shizhuangRes = "src/main/resources/json/" + serverRes + "/ItemShizhuangRes.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, shizhuangRes, EquipRes.class, "时装");

	}

}