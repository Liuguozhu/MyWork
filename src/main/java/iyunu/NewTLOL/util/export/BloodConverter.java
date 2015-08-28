package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.blood.BloodAward;
import iyunu.NewTLOL.model.blood.BloodDesc;
import iyunu.NewTLOL.model.blood.BloodMonster;
import iyunu.NewTLOL.model.blood.BloodRes1;
import iyunu.NewTLOL.model.blood.BloodRes2;
import iyunu.NewTLOL.model.blood.BloodWord;
import iyunu.NewTLOL.model.blood.KillWord;
import iyunu.NewTLOL.model.blood.ReBornRes;
import iyunu.NewTLOL.util.json.JsonExproter;

/**
 * 血战
 * 
 * @author SunHonglei
 * 
 */
public final class BloodConverter {

	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/世界战.xlsx";
		
		String blood1 = "src/main/resources/json/" + serverRes + "/blood1.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, blood1, BloodRes1.class, "世界战地图1");
		String blood2 = "src/main/resources/json/" + serverRes + "/blood2.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, blood2, BloodRes2.class, "世界战地图2");
		String reBorn = "src/main/resources/json/" + serverRes + "/reBorn.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, reBorn, ReBornRes.class, "复活");
		String monster = "src/main/resources/json/" + serverRes + "/bloodMonster.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, monster, BloodMonster.class, "怪物");
		String word = "src/main/resources/json/" + serverRes + "/bloodWord.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, word, BloodWord.class, "提示语");
		String killWord = "src/main/resources/json/" + serverRes + "/killWord.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, killWord, KillWord.class, "连杀语");
		String bloodAward = "src/main/resources/json/" + serverRes + "/bloodAward.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, bloodAward, BloodAward.class, "奖励");
		String bloodDesc = "src/main/resources/json/" + serverRes + "/BloodDesc.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, bloodDesc, BloodDesc.class, "描述");
	}
}