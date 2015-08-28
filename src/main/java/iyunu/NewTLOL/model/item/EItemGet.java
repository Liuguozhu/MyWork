package iyunu.NewTLOL.model.item;

import iyunu.NewTLOL.util.StringControl;

public enum EItemGet {

	mall("商城购买", false), // 商城==========================不发送通知=================
	shop("商店购买", false), // 商店==========================不发送通知=================
	gangShop("帮派商店兑换", false), // 帮派商店==========================不发送通知=================
	monster(StringControl.grn("打怪"), true), // 野外怪物
	raidsBoss(StringControl.grn("打败副本BOSS"), true), // 副本BOSS
	raidsBox(StringControl.grn("打开多人副本宝箱"), true), // 副本宝箱
	huntSkillBox(StringControl.grn("打开猎技宝箱"), true), // 猎技宝箱
	box(StringControl.grn("打开野外宝箱"), true), // 野外宝箱
	mail("邮件", false), // 邮件==========================不发送通知=================
	sysMail("系统邮件", false), // 系统邮件==========================不发送通知=================
	auctionMail("拍卖行邮件", false), // 拍卖行邮件==========================不发送通知=================
	onlineGift(StringControl.grn("领取在线礼包"), true), // 在线礼包
	sign(StringControl.grn("领取签到礼包"), true), // 签到礼包
	logon(StringControl.grn("领取连续登录礼包"), true), // 连续登录礼包
	liveness(StringControl.grn("活跃度兑换"), true), // 活跃度
	firstPayGift(StringControl.grn("打开首充礼包"), true), // 首充礼包
	levelGift(StringControl.grn("打开等级礼包"), true), // 等级礼包
	huntTreasure(StringControl.grn("天赐寻宝"), true), // 天赐寻宝
	invasion(StringControl.grn("帮派入侵"), true), // 帮派入侵
	rotatio(StringControl.grn("帮派转盘"), true), // 帮派转盘
	task(StringControl.grn("完成任务"), true), // 任务
	trials(StringControl.grn("试炼"), true), // 试炼
	openGift(StringControl.grn("开礼包"), true), // 开礼包
	openTreasure(StringControl.grn("挖宝图"), true), // 挖宝图
	wearEquip("穿装备", false), // 穿装备==========================不发送通知=================
	unloadEquip("卸装备", false), // 卸装备==========================不发送通知=================
	stoneInlay("宝石镶嵌", false), // 宝石镶嵌==========================不发送通知=================
	stoneMake(StringControl.grn("宝石制作"), true), // 宝石制作
	stoneUnload("卸下宝石", false), // 卸下宝石==========================不发送通知=================
	stoneUp(StringControl.grn("宝石合成"), true), // 宝石合成
	stoneUpFail("宝石合成失败", false), // 宝石合成失败==========================不发送通知=================
	fragmentMake(StringControl.grn("碎片合成"), false), // 碎片合成==========================不发送通知=================
	makeShenbing(StringControl.grn("制作神兵"), true), // 制作神兵
	upShenbing(StringControl.grn("升级神兵"), true), // 升级神兵
	getWarehouse("仓库取出", false), // 仓库取出==========================不发送通知=================
	activityNew(StringControl.grn("新服活动"), true), // 新服活动
	openXiangzi(StringControl.pur("神奇宝盒"), true), // 开箱子
	itemMake(StringControl.grn("物品合成"), false), // 物品合成==========================不发送通知=================
	cdKey(StringControl.grn("激活码兑换"), true), // 激活码
	equipResolve(StringControl.grn("装备分解"), false), // 装备分解==========================不发送通知=================
	qiancengta(StringControl.grn("千层塔"), true), // 千层塔
	createRole("创建角色", false), // 创建角色==========================不发送通知=================
	pick("任务采集", false), // 任务采集==========================不发送通知=================
	partnerOut("解雇伙伴", false), // 解雇伙伴==========================不发送通知=================
	sys("系统发送", false), // 系统发送==========================不发送通知=================
	paySingle(StringControl.grn("单笔充值活动"), true), // 单笔充值活动
	payAccumulateWeek(StringControl.grn("每周累计充值"), true), //
	chongjikuangren(StringControl.grn("冲击狂人"), true), //
	zuijiadadang(StringControl.grn("最佳搭档"), true), //
	zhuangbeiduandao(StringControl.grn("装备铸造"), true), //
	jianghudengjibang(StringControl.grn("江湖等级榜"), true), //
	doupodongtian(StringControl.grn("斗破洞天"), true), //
	jueshishenbing(StringControl.grn("绝世神兵"), true), //
	zuiqiangzhanli(StringControl.grn("最强战力"), true), //
	sevenGift(StringControl.grn("七天奖励"), true), // 七天奖励
	gangTask(StringControl.grn("帮派任务"), false), //
	newActivity(StringControl.grn("新服活动"), false), //
	plusMoney(StringControl.grn("累计充值"), true), //
	signItem(StringControl.grn("签到物品"), false), //
	daily(StringControl.grn("日常任务"), false), //
	;

	private String name;
	private boolean isNotice;

	EItemGet(String name, boolean isNotice) {
		this.name = name;
		this.isNotice = isNotice;
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the isNotice
	 */
	public boolean isNotice() {
		return isNotice;
	}

}
