package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.activity.ActivityFairyland;
import iyunu.NewTLOL.model.activity.ActivityRotation;
import iyunu.NewTLOL.model.activity.DrawingSite;
import iyunu.NewTLOL.model.activity.HuntTreasureInfo;
import iyunu.NewTLOL.model.activity.InvasionMonster;
import iyunu.NewTLOL.model.activity.InvasionSite;
import iyunu.NewTLOL.model.activity.PayExchangeRes;
import iyunu.NewTLOL.model.activity.PayExchangeShowRes;
import iyunu.NewTLOL.model.activity.QiDaERen;
import iyunu.NewTLOL.model.activity.change.ChangeCoinRes;
import iyunu.NewTLOL.model.activity.change.ChangeGoldRes;
import iyunu.NewTLOL.model.activity.fbl.ActivityFblRes;
import iyunu.NewTLOL.model.activity.plusMoney.PlusMoneyRes;
import iyunu.NewTLOL.model.activity.res.DrawingAwardRes;
import iyunu.NewTLOL.model.activity.res.OnlineAwardInfoRes;
import iyunu.NewTLOL.model.pay.PayFirstRes;
import iyunu.NewTLOL.model.payActivity.res.PayEverydayRes;
import iyunu.NewTLOL.util.json.JsonExproter;

/**
 * 活动
 * 
 * @author SunHonglei
 * 
 */
public final class ActivityConverter {

	public static void converter(String serverRes) {

		String xlsxFile = "docs/资源文档/" + serverRes + "/活动.xlsx";

		String invasionMonster = "src/main/resources/json/" + serverRes + "/InvasionMonster.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, invasionMonster, InvasionMonster.class, "帮派入侵怪物组合");
		String invasionSite = "src/main/resources/json/" + serverRes + "/InvasionSite.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, invasionSite, InvasionSite.class, "帮派入侵怪物位置");
		String payFirst = "src/main/resources/json/" + serverRes + "/PayFirst.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, payFirst, PayFirstRes.class, "首充");
		String rotation = "src/main/resources/json/" + serverRes + "/ActivityRotation.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, rotation, ActivityRotation.class, "转盘");
		String onlineAward = "src/main/resources/json/" + serverRes + "/ActivityOnlineAward.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, onlineAward, OnlineAwardInfoRes.class, "在线礼包");
		String drawingSite = "src/main/resources/json/" + serverRes + "/ActivityDrawingSite.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, drawingSite, DrawingSite.class, "藏宝图坐标");
		String huntTreasureInfo = "src/main/resources/json/" + serverRes + "/ActivityHuntTreasure.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, huntTreasureInfo, HuntTreasureInfo.class, "寻宝");
		String activityDrawingAward = "src/main/resources/json/" + serverRes + "/ActivityDrawingAward.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, activityDrawingAward, DrawingAwardRes.class, "藏宝图奖励");
		String activityQiDaERen = "src/main/resources/json/" + serverRes + "/ActivityQiDaERen.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, activityQiDaERen, QiDaERen.class, "七大恶人");
		String activityPayEveryday = "src/main/resources/json/" + serverRes + "/ActivityPayEveryday.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, activityPayEveryday, PayEverydayRes.class, "每日充值优惠");
		String activityFbl = "src/main/resources/json/" + serverRes + "/ActivityFbl.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, activityFbl, ActivityFblRes.class, "任务发布令");
		String plusmoney = "src/main/resources/json/" + serverRes + "/PlusMoney.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, plusmoney, PlusMoneyRes.class, "累计充值");

		String changeCoin = "src/main/resources/json/" + serverRes + "/ChangeCoin.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, changeCoin, ChangeCoinRes.class, "兑换银两");
		String changeGold = "src/main/resources/json/" + serverRes + "/ChangeGold.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, changeGold, ChangeGoldRes.class, "兑换绑银");
		String activityFairyland = "src/main/resources/json/" + serverRes + "/ActivityFairyland.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, activityFairyland, ActivityFairyland.class, "秘境");
		String payExchange = "src/main/resources/json/" + serverRes + "/payExchange.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, payExchange, PayExchangeRes.class, "积分榜");
		String payExchangeShow = "src/main/resources/json/" + serverRes + "/payExchangeShow.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, payExchangeShow, PayExchangeShowRes.class, "积分活动展示");
	}
}