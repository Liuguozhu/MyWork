package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.intensify.res.BodyInfoRes;
import iyunu.NewTLOL.model.intensify.res.EquipStarRes;
import iyunu.NewTLOL.model.intensify.res.IntensifyResolveRes;
import iyunu.NewTLOL.model.intensify.res.IntensifyStoneUpRes;
import iyunu.NewTLOL.model.intensify.res.ItemMakeRes;
import iyunu.NewTLOL.model.intensify.res.StoneRecipeRes;
import iyunu.NewTLOL.model.intensify.res.StoneUp;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class IntensifyConverter {

	/**
	 * @function 装备锻造资源转换器
	 * @author LuoSR
	 * @date 2013年12月30日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" + serverRes + "/锻造.xlsx";

		String weaponIntensify = "src/main/resources/json/" + serverRes + "/IntensifyWeapon.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, weaponIntensify, BodyInfoRes.class, "强化武器");

		String coatIntensify = "src/main/resources/json/" + serverRes + "/IntensifyCoat.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, coatIntensify, BodyInfoRes.class, "强化衣服");

		String shoeIntensify = "src/main/resources/json/" + serverRes + "/IntensifyShoe.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, shoeIntensify, BodyInfoRes.class, "强化鞋子");

		String capIntensify = "src/main/resources/json/" + serverRes + "/IntensifyBelt.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, capIntensify, BodyInfoRes.class, "强化腰带");

		String ringIntensify = "src/main/resources/json/" + serverRes + "/IntensifyRing.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, ringIntensify, BodyInfoRes.class, "强化戒指");

		String necklaceIntensify = "src/main/resources/json/" + serverRes + "/IntensifyNecklace.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, necklaceIntensify, BodyInfoRes.class, "强化项链");

		String stone = "src/main/resources/json/" + serverRes + "/StoneMake.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, stone, StoneRecipeRes.class, "宝石生成");

		String stoneUp = "src/main/resources/json/" + serverRes + "/StoneUp.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, stoneUp, StoneUp.class, "宝石合成概率");

		String equipStar = "src/main/resources/json/" + serverRes + "/EquipStar.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, equipStar, EquipStarRes.class, "装备升星");

		String itemMake = "src/main/resources/json/" + serverRes + "/ItemMake.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, itemMake, ItemMakeRes.class, "物品合成");

		String intensifyResolve = "src/main/resources/json/" + serverRes + "/IntensifyResolve.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, intensifyResolve, IntensifyResolveRes.class, "装备分解");
		
		String intensifyStoneUp = "src/main/resources/json/" + serverRes + "/IntensifyStoneUp.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, intensifyStoneUp, IntensifyStoneUpRes.class, "宝石镶嵌升级");
	}

}