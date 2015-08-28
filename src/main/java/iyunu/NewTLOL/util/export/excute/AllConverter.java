package iyunu.NewTLOL.util.export.excute;

import iyunu.NewTLOL.util.export.ActivityConverter;
import iyunu.NewTLOL.util.export.BloodConverter;
import iyunu.NewTLOL.util.export.GangConverter;
import iyunu.NewTLOL.util.export.GiftConverter;
import iyunu.NewTLOL.util.export.HelperConverter;
import iyunu.NewTLOL.util.export.HuntskillConverter;
import iyunu.NewTLOL.util.export.IlllegalWordConverter;
import iyunu.NewTLOL.util.export.IntensifyConverter;
import iyunu.NewTLOL.util.export.ItemConverter;
import iyunu.NewTLOL.util.export.Kuai3Converter;
import iyunu.NewTLOL.util.export.MallConverter;
import iyunu.NewTLOL.util.export.MapConverter;
import iyunu.NewTLOL.util.export.MiningConverter;
import iyunu.NewTLOL.util.export.MonsterConverter;
import iyunu.NewTLOL.util.export.PartnerConverter;
import iyunu.NewTLOL.util.export.PayConverter;
import iyunu.NewTLOL.util.export.QianCengTaConverter;
import iyunu.NewTLOL.util.export.QuestionConverter;
import iyunu.NewTLOL.util.export.RaidsConverter;
import iyunu.NewTLOL.util.export.RandomNameConverter;
import iyunu.NewTLOL.util.export.RoleConverter;
import iyunu.NewTLOL.util.export.ShenbingConverter;
import iyunu.NewTLOL.util.export.SkillConverter;
import iyunu.NewTLOL.util.export.TaskConverter;
import iyunu.NewTLOL.util.export.VipConverter;
import iyunu.NewTLOL.util.log.LogManager;

/**
 * 所有脚本转换器
 * 
 * @author SunHonglei
 * 
 */
public final class AllConverter {

	/**
	 * 私有构造函数
	 */
	private AllConverter() {

	}

	/**
	 * 将策划录入的excel表格转换为json文件
	 * 
	 * @param args
	 *            传入参数
	 */
	public static void main(String[] args) {
		String serverRes = "latx";

		MapConverter.converter(serverRes); // 加载地图
		MonsterConverter.converter(serverRes); // 加载怪物资源
		IntensifyConverter.converter(serverRes); // 加载装备锻造资源
		HuntskillConverter.converter(serverRes); // 加载猎技资源
		ItemConverter.converter(serverRes); // 加载物品资源
		MallConverter.converter(serverRes); // 加载商城资源
		PartnerConverter.converter(serverRes); // 加载伙伴资源
		RoleConverter.converter(serverRes); // 加载人物资源
		SkillConverter.converter(serverRes); // 加载技能资源
		TaskConverter.converter(serverRes); // 加载任务资源
		RaidsConverter.converter(serverRes); // 加载副本资源
		GangConverter.converter(serverRes);// 加载帮派资源
		QuestionConverter.converter(serverRes); // 加载答题资源
		RandomNameConverter.converter(serverRes);// 加载随机名称资源
		ActivityConverter.converter(serverRes);// 加载活动资源
		HelperConverter.converter(serverRes); // 加载小助手资源
		Kuai3Converter.converter(serverRes); // 加载猜猜看资源
		GiftConverter.converter(serverRes); // 加载等级礼包资源
		PayConverter.converter(serverRes); // 加载充值资源
		VipConverter.converter(serverRes); // vip描述资源
		BloodConverter.converter(serverRes);// 加载血战资源
		ShenbingConverter.converter(serverRes);// 加载神兵资源
		QianCengTaConverter.converter(serverRes);// 加载千层塔资源
		MiningConverter.converter(serverRes);// 加载矿区资源
		IlllegalWordConverter.converter(serverRes); // 加载屏蔽字资源
		LogManager.info("全部脚本加载完成");
	}

}