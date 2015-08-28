package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.buffRole.res.BuffRoleRes;
import iyunu.NewTLOL.model.jingMai.res.JingMaiRes;
import iyunu.NewTLOL.model.role.res.ExpMaxRes;
import iyunu.NewTLOL.model.role.res.FigureRes;
import iyunu.NewTLOL.model.role.res.RoleGuildRes;
import iyunu.NewTLOL.model.role.res.RoleSignItemRes;
import iyunu.NewTLOL.model.role.res.RoleSignRes;
import iyunu.NewTLOL.model.role.title.res.TitleRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class RoleConverter {

	/**
	 * @function 角色资源转换器
	 * @author LuoSR
	 * @date 2013年12月30日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" + serverRes + "/角色.xlsx";

		String roleExp = "src/main/resources/json/" + serverRes + "/RoleExp.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, roleExp, ExpMaxRes.class, "经验");

		String roleFigure = "src/main/resources/json/" + serverRes + "/RoleFigure.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, roleFigure, FigureRes.class, "角色");
		
		String roleGuild = "src/main/resources/json/" + serverRes + "/RoleGuild.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, roleGuild, RoleGuildRes.class, "门派");

		String jingMai = "src/main/resources/json/" + serverRes + "/JingMai.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, jingMai, JingMaiRes.class, "经脉");

		String roleSign = "src/main/resources/json/" + serverRes + "/roleSign.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, roleSign, RoleSignRes.class, "签到");
		
		String roleSignItem = "src/main/resources/json/" + serverRes + "/roleSignItem.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, roleSignItem, RoleSignItemRes.class, "签到物品");
		// String continueLogon = "src/main/resources/json/" + serverRes +
		// "/continueLogon.json.txt";
		// JsonExproter.convertToJsonFile(xlsxFile, continueLogon,
		// ContinueLogonRes.class, "连续登陆");

		String buffRole = "src/main/resources/json/" + serverRes + "/BuffRole.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, buffRole, BuffRoleRes.class, "增益状态");

		String roleTitle = "src/main/resources/json/" + serverRes + "/RoleTitle.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, roleTitle, TitleRes.class, "称号");
	}
}