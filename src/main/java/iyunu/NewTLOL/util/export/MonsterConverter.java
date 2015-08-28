package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.monster.res.MonsterBoxRes;
import iyunu.NewTLOL.model.monster.res.MonsterGroupRes;
import iyunu.NewTLOL.model.monster.res.MonsterInvasionRes;
import iyunu.NewTLOL.model.monster.res.MonsterRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class MonsterConverter {

	/**
	 * 怪物资源转换器
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/怪物.xlsx";

		String monsterFile = "src/main/resources/json/" + serverRes + "/Monster.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, monsterFile, MonsterRes.class, "野外怪物");

		String monsterActivityFile = "src/main/resources/json/" + serverRes + "/MonsterActivity.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, monsterActivityFile, MonsterRes.class, "活动怪物");

		String monsterBoxFile = "src/main/resources/json/" + serverRes + "/MonsterBox.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, monsterBoxFile, MonsterBoxRes.class, "宝箱");

		String monsterGroupFile = "src/main/resources/json/" + serverRes + "/MonsterGroup.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, monsterGroupFile, MonsterGroupRes.class, "怪物组合");

		String trialsMonsterFile = "src/main/resources/json/" + serverRes + "/MonsterTrials.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, trialsMonsterFile, MonsterRes.class, "试炼怪物");

		String monsterRaids = "src/main/resources/json/" + serverRes + "/MonsterRaids.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, monsterRaids, MonsterRes.class, "副本怪物");

		String monsterQiancengta = "src/main/resources/json/" + serverRes + "/MonsterQiancengta.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, monsterQiancengta, MonsterRes.class, "福地怪物");

		String monsterInvasion = "src/main/resources/json/" + serverRes + "/MonsterInvasion.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, monsterInvasion, MonsterInvasionRes.class, "帮派入侵");
	}

}