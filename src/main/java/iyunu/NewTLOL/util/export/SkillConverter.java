package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.battle.buff.BattleBuff;
import iyunu.NewTLOL.model.skill.res.PartnerSkillRes;
import iyunu.NewTLOL.model.skill.res.RoleSkillRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class SkillConverter {

	/**
	 * @function 技能资源转换器
	 * @author LuoSR
	 * @param args
	 * @date 2013年12月30日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/技能.xlsx";
		
		String skillPartner = "src/main/resources/json/" + serverRes + "/SkillPartner.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, skillPartner, PartnerSkillRes.class, "伙伴技能");
		String skillRole = "src/main/resources/json/" + serverRes + "/SkillRole.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, skillRole, RoleSkillRes.class, "角色技能");
		String buff = "src/main/resources/json/" + serverRes + "/BuffBattle.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, buff, BattleBuff.class, "BUFF");
	}
}