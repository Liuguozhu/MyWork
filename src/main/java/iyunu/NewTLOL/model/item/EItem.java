package iyunu.NewTLOL.model.item;

import iyunu.NewTLOL.model.item.instance.BagExtend;
import iyunu.NewTLOL.model.item.instance.BangPaiLing;
import iyunu.NewTLOL.model.item.instance.Book;
import iyunu.NewTLOL.model.item.instance.BuffExp;
import iyunu.NewTLOL.model.item.instance.BuffGold;
import iyunu.NewTLOL.model.item.instance.BuffHp;
import iyunu.NewTLOL.model.item.instance.BuffMonster;
import iyunu.NewTLOL.model.item.instance.BuffMp;
import iyunu.NewTLOL.model.item.instance.BuffPartnerHp;
import iyunu.NewTLOL.model.item.instance.CoinCard;
import iyunu.NewTLOL.model.item.instance.Dakongqi;
import iyunu.NewTLOL.model.item.instance.Drawing;
import iyunu.NewTLOL.model.item.instance.Drug;
import iyunu.NewTLOL.model.item.instance.DrugP;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.item.instance.Fabuling;
import iyunu.NewTLOL.model.item.instance.Fragment;
import iyunu.NewTLOL.model.item.instance.Fushi;
import iyunu.NewTLOL.model.item.instance.Gaiming;
import iyunu.NewTLOL.model.item.instance.Gift;
import iyunu.NewTLOL.model.item.instance.GiftPartner;
import iyunu.NewTLOL.model.item.instance.GiftShizhuang;
import iyunu.NewTLOL.model.item.instance.GiftWeapon;
import iyunu.NewTLOL.model.item.instance.GoldCard;
import iyunu.NewTLOL.model.item.instance.ItemEnergy;
import iyunu.NewTLOL.model.item.instance.JadeA;
import iyunu.NewTLOL.model.item.instance.JadeJia;
import iyunu.NewTLOL.model.item.instance.JadeJin;
import iyunu.NewTLOL.model.item.instance.JadeLong;
import iyunu.NewTLOL.model.item.instance.JadeMo;
import iyunu.NewTLOL.model.item.instance.JadeQian;
import iyunu.NewTLOL.model.item.instance.JadeTian;
import iyunu.NewTLOL.model.item.instance.JadeYe;
import iyunu.NewTLOL.model.item.instance.Jichushi;
import iyunu.NewTLOL.model.item.instance.Jiefeng;
import iyunu.NewTLOL.model.item.instance.JuanZong;
import iyunu.NewTLOL.model.item.instance.Lingshi;
import iyunu.NewTLOL.model.item.instance.Lingzhi;
import iyunu.NewTLOL.model.item.instance.MaterialOil;
import iyunu.NewTLOL.model.item.instance.MaterialPurified;
import iyunu.NewTLOL.model.item.instance.MaterialStone;
import iyunu.NewTLOL.model.item.instance.PartnerCapabilityInfo;
import iyunu.NewTLOL.model.item.instance.PartnerExpCard;
import iyunu.NewTLOL.model.item.instance.RoleReset;
import iyunu.NewTLOL.model.item.instance.Shengxingshi;
import iyunu.NewTLOL.model.item.instance.Shuijingshi;
import iyunu.NewTLOL.model.item.instance.Stone;
import iyunu.NewTLOL.model.item.instance.TaskItem;
import iyunu.NewTLOL.model.item.instance.Trumpet;
import iyunu.NewTLOL.model.item.instance.VipItem;
import iyunu.NewTLOL.model.item.instance.WarehouseExtend;
import iyunu.NewTLOL.model.item.instance.XiDianQuan;
import iyunu.NewTLOL.model.item.instance.Xiangzi;
import iyunu.NewTLOL.model.item.instance.XunYiCao;
import iyunu.NewTLOL.model.item.instance.Yaoqinghan;
import iyunu.NewTLOL.model.item.instance.Yingxiongtie;
import iyunu.NewTLOL.model.item.instance.YuanQi;
import iyunu.NewTLOL.model.item.instance.YuanbaoCard;
import iyunu.NewTLOL.model.item.instance.Zhaomuling;

import java.util.ArrayList;
import java.util.List;

/**
 * 物品类型
 * 
 * @author SunHonglei
 * 
 */

public enum EItem {
	equip(Equip.class), // 装备
	stone(Stone.class), // 宝石
	book(Book.class), // 技能书
	drug(Drug.class), // 药品
	drugP(DrugP.class), // 伙伴药品
	gift(Gift.class), // 礼包
	weaponGift(GiftWeapon.class), // 武器礼包
	taskItem(TaskItem.class), // 任务物品
	xidianquan(XiDianQuan.class), // 洗点券
	dakongqi(Dakongqi.class), // 打孔器
	materialStone(MaterialStone.class), // 材料宝石
	materialOil(MaterialOil.class), // 精油
	materialPurified(MaterialPurified.class), // 净化水
	yuanqi(YuanQi.class), // 培元丹
	partnerExp(PartnerExpCard.class), // 伙伴经验卡
	gold(GoldCard.class), // 金钱袋
	bag(BagExtend.class), // 背包扩展
	warehouse(WarehouseExtend.class), // 仓库扩展
	lingzhi(Lingzhi.class), // 灵芝
	vipItem(VipItem.class), // Vip月卡
	xunyicao(XunYiCao.class), // 薰衣草
	buffHp(BuffHp.class), // 自动恢复生命值
	buffMp(BuffMp.class), // 自动恢复内力值
	reset(RoleReset.class), // 角色属性重置
	buffPartnerHp(BuffPartnerHp.class), // 自动恢复伙伴生命值
	bangpailing(BangPaiLing.class), // 帮派令
	jiefeng(Jiefeng.class), // 解封丹
	jadeTian(JadeTian.class), // 天众残玉
	jadeLong(JadeLong.class), // 龙众残玉
	jadeYe(JadeYe.class), // 夜叉残玉
	jadeQian(JadeQian.class), // 乾达婆残玉
	jadeA(JadeA.class), // 阿修罗残玉
	jadeJia(JadeJia.class), // 迦楼罗残玉
	jadeJin(JadeJin.class), // 紧那罗残玉
	jadeMo(JadeMo.class), // 摩呼罗迦残玉
	fragment(Fragment.class), // 碎片
	drawing(Drawing.class), // 藏宝图
	buffExp(BuffExp.class), // 双倍经验
	buffGold(BuffGold.class), // 双倍绑银
	lingshi(Lingshi.class), // 灵石
	xiangzi(Xiangzi.class), // 箱子
	yuanbao(YuanbaoCard.class), // 元宝卡
	trumpet(Trumpet.class), // 喇叭
	jichushi(Jichushi.class), // 基础石
	shuijingshi(Shuijingshi.class), // 基础石
	fushi(Fushi.class), // 符石
	gaiming(Gaiming.class), // 改名卡
	shengxingshi(Shengxingshi.class), // 升星石
	zhaomuling(Zhaomuling.class), // 招募令
	juanzong(JuanZong.class), // 卷宗
	partnerGift(GiftPartner.class), // 伙伴礼包
	shizhuangGift(GiftShizhuang.class), // 时装礼包
	yingxiongtie(Yingxiongtie.class), // 英雄帖
	coin(CoinCard.class), // 银两袋
	fabuling(Fabuling.class), // 任务发布令
	yaoqinghan(Yaoqinghan.class), // 邀请函
	buffMonster(BuffMonster.class), // 杀怪加倍
	energy(ItemEnergy.class), // 增加活力值
	partnerCapability(PartnerCapabilityInfo.class), // 增加潜力值
	;

	private Class<? extends Item> object;
	private static List<EItem> list = new ArrayList<EItem>();

	EItem(Class<? extends Item> object) {
		this.object = object;
	}

	public Class<? extends Item> getObject() {
		return object;
	}

	public static List<EItem> getJade() {
		if (list.isEmpty()) {
			list.add(jadeTian);
			list.add(jadeLong);
			list.add(jadeYe);
			list.add(jadeQian);
			list.add(jadeA);
			list.add(jadeJia);
			list.add(jadeJin);
			list.add(jadeMo);
		}
		return list;
	}
}
