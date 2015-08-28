package iyunu.NewTLOL.main;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.BloodJson;
import iyunu.NewTLOL.json.GangJson;
import iyunu.NewTLOL.json.GiftJson;
import iyunu.NewTLOL.json.HelperJson;
import iyunu.NewTLOL.json.HuntskillJson;
import iyunu.NewTLOL.json.IlllegalWordJson;
import iyunu.NewTLOL.json.IntensifyJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.JingMaiJson;
import iyunu.NewTLOL.json.Kuai3Json;
import iyunu.NewTLOL.json.MallJson;
import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.json.MiningJson;
import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.json.PayJson;
import iyunu.NewTLOL.json.QiancengtaJson;
import iyunu.NewTLOL.json.QuestionJson;
import iyunu.NewTLOL.json.RaidsJson;
import iyunu.NewTLOL.json.RoleJson;
import iyunu.NewTLOL.json.ShenbingJson;
import iyunu.NewTLOL.json.SkillJson;
import iyunu.NewTLOL.json.TaskJson;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.MultipleManager;
import iyunu.NewTLOL.manager.OperationManager;
import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.util.log.LogManager;
import iyunu.NewTLOL.util.mail.Email;

import java.io.File;

import com.liteProto.LlpJava;

public final class TLOLManager {

	/**
	 * 私有构造方法
	 */
	private TLOLManager() {

	}

	/**
	 * 加载协议文件
	 * 
	 * @throws Exception
	 *             加载异常
	 */
	public static void initLpb() {
		try {
			// 登录服务器协议文件
			File hallLpb = new File("./lpb/hall.mes.lpb");
			LlpJava.instance().regMessage(hallLpb.getAbsolutePath());
			// 拍卖行协议
			File auctionLpb = new File("./lpb/auction.mes.lpb");
			LlpJava.instance().regMessage(auctionLpb.getAbsolutePath());
			// 战斗协议
			File battleLpb = new File("./lpb/battle.mes.lpb");
			LlpJava.instance().regMessage(battleLpb.getAbsolutePath());
			// 公告协议
			File bulletinLpb = new File("./lpb/bulletin.mes.lpb");
			LlpJava.instance().regMessage(bulletinLpb.getAbsolutePath());
			// 聊天协议
			File chatLpb = new File("./lpb/chat.mes.lpb");
			LlpJava.instance().regMessage(chatLpb.getAbsolutePath());
			// 强化协议
			File equipmentLpb = new File("./lpb/intensify.mes.lpb");
			LlpJava.instance().regMessage(equipmentLpb.getAbsolutePath());
			// 好友协议
			File friendLpb = new File("./lpb/friend.mes.lpb");
			LlpJava.instance().regMessage(friendLpb.getAbsolutePath());
			// 心跳协议
			File liveLpb = new File("./lpb/live.mes.lpb");
			LlpJava.instance().regMessage(liveLpb.getAbsolutePath());
			// 邮件协议
			File mailLpb = new File("./lpb/mail.mes.lpb");
			LlpJava.instance().regMessage(mailLpb.getAbsolutePath());
			// 地图协议
			File mapLpb = new File("./lpb/map.mes.lpb");
			LlpJava.instance().regMessage(mapLpb.getAbsolutePath());
			// 任务协议
			File bagLpb = new File("./lpb/bag.mes.lpb");
			LlpJava.instance().regMessage(bagLpb.getAbsolutePath());
			// 伙伴协议
			File partnerLpb = new File("./lpb/partner.mes.lpb");
			LlpJava.instance().regMessage(partnerLpb.getAbsolutePath());
			// 角色协议和选择角色协议
			File roleLpb = new File("./lpb/role.mes.lpb");
			LlpJava.instance().regMessage(roleLpb.getAbsolutePath());
			// 队伍协议
			File teamLpb = new File("./lpb/team.mes.lpb");
			LlpJava.instance().regMessage(teamLpb.getAbsolutePath());
			// 猎技协议
			File huntSkillLpb = new File("./lpb/huntskill.mes.lpb");
			LlpJava.instance().regMessage(huntSkillLpb.getAbsolutePath());
			// 任务协议
			File taskLpb = new File("./lpb/task.mes.lpb");
			LlpJava.instance().regMessage(taskLpb.getAbsolutePath());
			// 排行榜协议
			File billboardLpb = new File("./lpb/billboard.mes.lpb");
			LlpJava.instance().regMessage(billboardLpb.getAbsolutePath());
			// 聚魂协议
			File juhunLpb = new File("./lpb/juhun.mes.lpb");
			LlpJava.instance().regMessage(juhunLpb.getAbsolutePath());
			// 帮派协议
			File gangLpb = new File("./lpb/gang.mes.lpb");
			LlpJava.instance().regMessage(gangLpb.getAbsolutePath());
			// 经脉协议
			File jingMaiLpb = new File("./lpb/jingMai.mes.lpb");
			LlpJava.instance().regMessage(jingMaiLpb.getAbsolutePath());
			// 充值协议
			File pay = new File("./lpb/pay.mes.lpb");
			LlpJava.instance().regMessage(pay.getAbsolutePath());
			// 副本协议
			File raids = new File("./lpb/raids.mes.lpb");
			LlpJava.instance().regMessage(raids.getAbsolutePath());
			// 快3协议
			File kuai3 = new File("./lpb/kuai3.mes.lpb");
			LlpJava.instance().regMessage(kuai3.getAbsolutePath());
			// 商城协议
			File mall = new File("./lpb/mall.mes.lpb");
			LlpJava.instance().regMessage(mall.getAbsolutePath());
			// 答题协议
			File question = new File("./lpb/question.mes.lpb");
			LlpJava.instance().regMessage(question.getAbsolutePath());
			// 试炼协议
			File trials = new File("./lpb/trials.mes.lpb");
			LlpJava.instance().regMessage(trials.getAbsolutePath());
			// 等级礼包协议
			File levelGift = new File("./lpb/levelGift.mes.lpb");
			LlpJava.instance().regMessage(levelGift.getAbsolutePath());
			// 小助手协议
			File helper = new File("./lpb/helper.mes.lpb");
			LlpJava.instance().regMessage(helper.getAbsolutePath());
			// 登录协议
			File logon = new File("./lpb/logon.mes.lpb");
			LlpJava.instance().regMessage(logon.getAbsolutePath());
			// 离线协议
			File offLineExp = new File("./lpb/offLineExp.mes.lpb");
			LlpJava.instance().regMessage(offLineExp.getAbsolutePath());
			// BUFF协议
			File buff = new File("./lpb/buff.mes.lpb");
			LlpJava.instance().regMessage(buff.getAbsolutePath());
			// 客服协议
			File kefu = new File("./lpb/kefu.mes.lpb");
			LlpJava.instance().regMessage(kefu.getAbsolutePath());
			// 刷新协议
			File refresh = new File("./lpb/refresh.mes.lpb");
			LlpJava.instance().regMessage(refresh.getAbsolutePath());
			// 活动协议
			File activity = new File("./lpb/activity.mes.lpb");
			LlpJava.instance().regMessage(activity.getAbsolutePath());
			// 新手引导协议
			File lzjh = new File("./lpb/lzjh.mes.lpb");
			LlpJava.instance().regMessage(lzjh.getAbsolutePath());
			// 客户端日志协议
			File clientLog = new File("./lpb/clientLog.mes.lpb");
			LlpJava.instance().regMessage(clientLog.getAbsolutePath());
			// 帮派战协议
			File gangFight = new File("./lpb/gangFight.mes.lpb");
			LlpJava.instance().regMessage(gangFight.getAbsolutePath());
			// 血战协议
			File blood = new File("./lpb/blood.mes.lpb");
			LlpJava.instance().regMessage(blood.getAbsolutePath());
			LlpJava.instance().regMessage(gangFight.getAbsolutePath());
			// 神兵协议
			File shenBing = new File("./lpb/shenbing.mes.lpb");
			LlpJava.instance().regMessage(shenBing.getAbsolutePath());
			// 千层塔协议
			File qiancengta = new File("./lpb/qiancengta.mes.lpb");
			LlpJava.instance().regMessage(qiancengta.getAbsolutePath());
			// 矿区协议
			File mining = new File("./lpb/mining.mes.lpb");
			LlpJava.instance().regMessage(mining.getAbsolutePath());

			LogManager.info("【协议文件】加载完成");
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.error("【协议文件】加载失败！！！！！！！！！！！！！！！！");
		}

	}

	public static void initConf() {
		ServerManager.instance().init();
		PayManager.instance().init();
		RoleManager.init();
		Email.init();
		OperationManager.init();
		LogManager.info("【配置文件】加载完成");
	}

	/**
	 * 加载JSON资源文件
	 * 
	 * @throws Exception
	 */
	public static void initJson() throws Exception {
		ItemJson.instance().init(); // 物品
		PartnerJson.instance().init(); // 伙伴
		HuntskillJson.instance().init(); // 猎技
		MallJson.instance().init(); // 商城（必须在物品信息之后加载）
		// ShopJson.instance().init(); // 商店（必须在物品信息之后加载）
		SkillJson.instance().init();// 角色技能
		GangJson.instance().init();// 帮派（必须在物品信息之后加载）
		MonsterJson.instance().init(); // 怪物（必须在物品信息、技能之后加载）
		MapJson.instance().init(); // 地图信息（必须在怪物信息之后加载）
		TaskJson.instance().init(); // 任务信息（必须在怪物信息、物品之后加载）
		JingMaiJson.instance().init(); // 经脉
		RoleJson.instance().init(); // 角色
		RaidsJson.instance().init(); // 副本（在地图之后）
		IntensifyJson.instance().init(); // 强化
		PayJson.instance().init(); // 充值比例
		ActivityJson.instance().init(); // 活动
		Kuai3Json.instance().init();// 猜猜看
		QuestionJson.instance().init();// 题库
		GiftJson.instance().init();// 等级礼包
		HelperJson.instance().init(); // 小助手
		BloodJson.instance().init();// 血战
		ShenbingJson.instance().init();// 神兵
		QiancengtaJson.instance().init(); // 千层塔
		MiningJson.instance().init();// 矿区
		IlllegalWordJson.instance().init();// 非法文字
		LogManager.info("【JSON资源文件】加载完成");
	}

	/**
	 * 加载公告
	 */
	public static void initBulletin() {
		BulletinManager.instance().init();
		LogManager.info("【公告】加载完成");
	}

	/**
	 * 加载双倍
	 */
	public static void initMultiple() {
		MultipleManager.init();
		LogManager.info("【双倍】加载完成");
	}
}
