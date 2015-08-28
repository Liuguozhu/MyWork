package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.gang.GangFightMapInfo;
import iyunu.NewTLOL.model.gang.res.GangActiveEventRes;
import iyunu.NewTLOL.model.gang.res.GangActivityRes;
import iyunu.NewTLOL.model.gang.res.GangFightDesRes;
import iyunu.NewTLOL.model.gang.res.GangLevelRes;
import iyunu.NewTLOL.model.gang.res.GangOperateMenuRes;
import iyunu.NewTLOL.model.gang.res.GangRelationRes;
import iyunu.NewTLOL.model.gang.res.GangTaskAllAwardRes;
import iyunu.NewTLOL.model.gang.res.GangTaskRes;
import iyunu.NewTLOL.model.gang.res.GangTributeEventRes;
import iyunu.NewTLOL.model.gang.res.ShaoXiangRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class GangConverter {

	/**
	 * @function 帮派资源转换器
	 * @author LuoSR
	 * @param args
	 * @date 2013年12月30日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/帮派.xlsx";
		
		String gangOperateMenu = "src/main/resources/json/" + serverRes + "/GangOperateMenu.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, gangOperateMenu, GangOperateMenuRes.class, "权限菜单名称");
		String gangRelation = "src/main/resources/json/" + serverRes + "/GangRelation.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, gangRelation, GangRelationRes.class, "权限关系");

		String gangLevel = "src/main/resources/json/" + serverRes + "/GangLevel.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, gangLevel, GangLevelRes.class, "帮派升级经验表");
		String shaoXiang = "src/main/resources/json/" + serverRes + "/GangShaoXiang.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, shaoXiang, ShaoXiangRes.class, "烧香");
		String tribute = "src/main/resources/json/" + serverRes + "/GangTribute.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, tribute, GangTributeEventRes.class, "帮派增加贡献表");
		String active = "src/main/resources/json/" + serverRes + "/GangActiveEvent.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, active, GangActiveEventRes.class, "帮派增加人气值表");
		String gangActivity = "src/main/resources/json/" + serverRes + "/GangActivity.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, gangActivity, GangActivityRes.class, "活动列表");

		String gangFightDesc = "src/main/resources/json/" + serverRes + "/GangFightDesc.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, gangFightDesc, GangFightDesRes.class, "帮派战描述");
		String gangFightMapInfo = "src/main/resources/json/" + serverRes + "/GangFightMapInfo.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, gangFightMapInfo, GangFightMapInfo.class, "帮派战地图信息");

		String gangTask = "src/main/resources/json/" + serverRes + "/gangTask.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, gangTask, GangTaskRes.class, "帮派任务");
		String gangTaskAllAward = "src/main/resources/json/" + serverRes + "/gangTaskAllAward.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, gangTaskAllAward, GangTaskAllAwardRes.class, "帮派任务总奖励");
	}
}