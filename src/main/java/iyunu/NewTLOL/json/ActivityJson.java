package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.ActivityRotationManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.activity.ActivityFairyland;
import iyunu.NewTLOL.model.activity.ActivityRotation;
import iyunu.NewTLOL.model.activity.DrawingSite;
import iyunu.NewTLOL.model.activity.HuntTreasureInfo;
import iyunu.NewTLOL.model.activity.InvasionMonster;
import iyunu.NewTLOL.model.activity.InvasionSite;
import iyunu.NewTLOL.model.activity.PayExchange;
import iyunu.NewTLOL.model.activity.PayExchangeRes;
import iyunu.NewTLOL.model.activity.PayExchangeShowRes;
import iyunu.NewTLOL.model.activity.QiDaERen;
import iyunu.NewTLOL.model.activity.change.ChangeCoinRes;
import iyunu.NewTLOL.model.activity.change.ChangeGoldRes;
import iyunu.NewTLOL.model.activity.fbl.ActivityFblRes;
import iyunu.NewTLOL.model.activity.instance.DrawingAward;
import iyunu.NewTLOL.model.activity.instance.OnlineAwardInfo;
import iyunu.NewTLOL.model.activity.plusMoney.PlusMoney;
import iyunu.NewTLOL.model.activity.plusMoney.PlusMoneyRes;
import iyunu.NewTLOL.model.activity.res.DrawingAwardRes;
import iyunu.NewTLOL.model.activity.res.OnlineAwardInfoRes;
import iyunu.NewTLOL.model.pay.PayFirstInfo;
import iyunu.NewTLOL.model.pay.PayFirstRes;
import iyunu.NewTLOL.model.payActivity.instance.PayEveryday;
import iyunu.NewTLOL.model.payActivity.res.PayEverydayRes;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ArrayListMultimap;

public class ActivityJson {

	/**
	 * 私有构造方法
	 */
	private ActivityJson() {

	}

	private static final ActivityJson instance = new ActivityJson();

	public static ActivityJson instance() {
		return instance;
	}

	private static final String INVASION_MONSTER = "json/" + ServerManager.SERVER_RES + "/InvasionMonster.json.txt";
	private static final String INVASION_SITE = "json/" + ServerManager.SERVER_RES + "/InvasionSite.json.txt";
	private static final String PAY_FIRST = "json/" + ServerManager.SERVER_RES + "/PayFirst.json.txt";
	private static final String ACTIVITY_ROTATION = "json/" + ServerManager.SERVER_RES + "/ActivityRotation.json.txt";
	private static final String ACTIVITY_ONLINE_AWARD = "json/" + ServerManager.SERVER_RES + "/ActivityOnlineAward.json.txt";
	private static final String ACTIVITY_DRAWING_SITE = "json/" + ServerManager.SERVER_RES + "/ActivityDrawingSite.json.txt";
	private static final String ACTIVITY_DRAWING_AWARD = "json/" + ServerManager.SERVER_RES + "/ActivityDrawingAward.json.txt";
	private static final String ACTIVITY_HUNT_TREASURE = "json/" + ServerManager.SERVER_RES + "/ActivityHuntTreasure.json.txt";
	private static final String ACTIVITY_QIDAEREN = "json/" + ServerManager.SERVER_RES + "/ActivityQiDaERen.json.txt";
	private static final String ACTIVITY_PAYEVERYDAY = "json/" + ServerManager.SERVER_RES + "/ActivityPayEveryday.json.txt";
	private static final String ACTIVITY_FBL = "json/" + ServerManager.SERVER_RES + "/ActivityFbl.json.txt";
	private static final String ACTIVITY_PLUSMONEY = "json/" + ServerManager.SERVER_RES + "/PlusMoney.json.txt";
	private static final String ACTIVITY_CHANGE_COIN = "json/" + ServerManager.SERVER_RES + "/ChangeCoin.json.txt";
	private static final String ACTIVITY_CHANGE_GOLD = "json/" + ServerManager.SERVER_RES + "/ChangeGold.json.txt";
	private static final String ACTIVITY_FAIRYLAND = "json/" + ServerManager.SERVER_RES + "/ActivityFairyland.json.txt";
	private static final String PAY_EXCHANGE = "json/" + ServerManager.SERVER_RES + "/payExchange.json.txt";
	private static final String PAY_EXCHANGE_SHOW = "json/" + ServerManager.SERVER_RES + "/payExchangeShow.json.txt";

	private PayFirstInfo payFirstInfo;
	private Map<Integer, OnlineAwardInfo> activityOnlineMap = new HashMap<Integer, OnlineAwardInfo>(); // <奖励编号，奖励对象>
	private List<OnlineAwardInfo> activityOnlineList = new ArrayList<OnlineAwardInfo>();
	private List<DrawingSite> drawingSites = new ArrayList<DrawingSite>();
	private ArrayListMultimap<Integer, DrawingSite> drawingMapSites = ArrayListMultimap.create();
	// private Map<Integer, List<DrawingSite>> drawingMapSites = new
	// HashMap<Integer, List<DrawingSite>>();
	private List<HuntTreasureInfo> huntTreasureInfos = new ArrayList<HuntTreasureInfo>();
	private Map<Integer, DrawingAward> drawingAwards = new HashMap<Integer, DrawingAward>(); // 藏宝图奖励<奖励索引，奖励对象>
	private List<QiDaERen> qiDaERens = new ArrayList<QiDaERen>(); // 七大恶人<怪物组合编号>
	private Map<Integer, ActivityFblRes> activityFbls = new LinkedHashMap<Integer, ActivityFblRes>(); // 发布令活动
	public static PayEveryday PAY_EVERYDAY;
	private Map<Integer, PlusMoney> plusMoney = new HashMap<>();// 累计充值
	private Map<Integer, Integer> changeCoin = new HashMap<Integer, Integer>(); // 兑换银两
	private Map<Integer, Integer> changeGold = new HashMap<Integer, Integer>(); // 兑换绑银
	private Map<Integer, ActivityFairyland> activityFairylands = new HashMap<Integer, ActivityFairyland>(); // 兑换绑银
	private Map<Integer, PayExchange> payExchange = new HashMap<>();// 积分榜
	public PayExchangeShowRes payExchangeShow = new PayExchangeShowRes();// 积分榜展示数据

	public void clear() {
		ActivityRotationManager.clearActivityRotations();
		drawingSites.clear();
		activityOnlineMap.clear();
		activityOnlineList.clear();
		huntTreasureInfos.clear();
		drawingAwards.clear();
		drawingMapSites.clear();
		qiDaERens.clear();
		activityFbls.clear();
		plusMoney.clear();
		changeCoin.clear();
		changeGold.clear();
		activityFairylands.clear();
		payExchange.clear();
		payExchangeShow = new PayExchangeShowRes();
	}

	public void init() {
		long start = System.currentTimeMillis();
		clear();
		List<InvasionMonster> activityInvasions = JsonImporter.fileImporter(INVASION_MONSTER, new TypeReference<List<InvasionMonster>>() {
		});
		for (InvasionMonster activityInvasion : activityInvasions) {
			ActivityManager.addMonsterGroup(activityInvasion.getMonsterGroup());
		}

		List<InvasionSite> invasionSites = JsonImporter.fileImporter(INVASION_SITE, new TypeReference<List<InvasionSite>>() {
		});
		for (InvasionSite invasionSite : invasionSites) {
			String[] str = invasionSite.getSite().split(";");
			ActivityManager.addSite(Translate.stringToInt(str[0]), Translate.stringToInt(str[1]));
		}

		List<PayFirstRes> payFirstResList = JsonImporter.fileImporter(PAY_FIRST, new TypeReference<List<PayFirstRes>>() {
		});
		for (PayFirstRes payFirstRes : payFirstResList) {
			this.payFirstInfo = payFirstRes.toPayFirstInfo();
		}

		List<ActivityRotation> activityRotationList = JsonImporter.fileImporter(ACTIVITY_ROTATION, new TypeReference<List<ActivityRotation>>() {
		});
		for (ActivityRotation activityRotation : activityRotationList) {
			ActivityRotationManager.addRotation(activityRotation);
		}

		List<OnlineAwardInfoRes> onlineAwardInfoResList = JsonImporter.fileImporter(ACTIVITY_ONLINE_AWARD, new TypeReference<List<OnlineAwardInfoRes>>() {
		});
		for (OnlineAwardInfoRes onlineAwardInfoRes : onlineAwardInfoResList) {
			activityOnlineMap.put(onlineAwardInfoRes.getId(), onlineAwardInfoRes.toOnlineAwardInfo());
			activityOnlineList.add(onlineAwardInfoRes.toOnlineAwardInfo());
		}

		List<DrawingSite> drawingSiteList = JsonImporter.fileImporter(ACTIVITY_DRAWING_SITE, new TypeReference<List<DrawingSite>>() {
		});
		for (DrawingSite drawingSite : drawingSiteList) {
			if (drawingSite.getIsDrawing() == 1) {
				drawingSites.add(drawingSite);
			}

			drawingMapSites.put(drawingSite.getMapId(), drawingSite);
			// if (drawingMapSites.containsKey(drawingSite.getMapId())) {
			// drawingMapSites.get(drawingSite.getMapId()).add(drawingSite);
			// } else {
			// List<DrawingSite> list = new ArrayList<DrawingSite>();
			// list.add(drawingSite);
			// drawingMapSites.put(drawingSite.getMapId(), list);
			// }

		}

		List<DrawingAwardRes> drawingAwardResList = JsonImporter.fileImporter(ACTIVITY_DRAWING_AWARD, new TypeReference<List<DrawingAwardRes>>() {
		});
		for (DrawingAwardRes drawingAwardRes : drawingAwardResList) {
			drawingAwards.put(drawingAwardRes.getIndex(), drawingAwardRes.toDrawingAward());
		}

		huntTreasureInfos = JsonImporter.fileImporter(ACTIVITY_HUNT_TREASURE, new TypeReference<List<HuntTreasureInfo>>() {
		});

		qiDaERens.addAll(JsonImporter.fileImporter(ACTIVITY_QIDAEREN, new TypeReference<List<QiDaERen>>() {
		}));

		List<PayEverydayRes> everydayResList = JsonImporter.fileImporter(ACTIVITY_PAYEVERYDAY, new TypeReference<List<PayEverydayRes>>() {
		});
		for (PayEverydayRes everydayRes : everydayResList) {
			PAY_EVERYDAY = everydayRes.toPayEveryday();
		}

		List<ActivityFblRes> activityFblResList = JsonImporter.fileImporter(ACTIVITY_FBL, new TypeReference<List<ActivityFblRes>>() {
		});
		for (ActivityFblRes activityFblRes : activityFblResList) {
			activityFbls.put(activityFblRes.getIndex(), activityFblRes);
		}

		List<PlusMoneyRes> plusMoneyList = JsonImporter.fileImporter(ACTIVITY_PLUSMONEY, new TypeReference<List<PlusMoneyRes>>() {
		});
		for (PlusMoneyRes p : plusMoneyList) {
			plusMoney.put(p.getId(), p.toPlusMoney());
		}

		List<ChangeCoinRes> changeCoinResList = JsonImporter.fileImporter(ACTIVITY_CHANGE_COIN, new TypeReference<List<ChangeCoinRes>>() {
		});
		for (ChangeCoinRes changeCoinRes : changeCoinResList) {
			changeCoin.put(changeCoinRes.getMoney(), changeCoinRes.getCoin());
		}
		List<ChangeGoldRes> changeGoldResList = JsonImporter.fileImporter(ACTIVITY_CHANGE_GOLD, new TypeReference<List<ChangeGoldRes>>() {
		});
		for (ChangeGoldRes changeGoldRes : changeGoldResList) {
			changeGold.put(changeGoldRes.getMoney(), changeGoldRes.getGold());
		}

		List<ActivityFairyland> activityFairylandList = JsonImporter.fileImporter(ACTIVITY_FAIRYLAND, new TypeReference<List<ActivityFairyland>>() {
		});
		for (ActivityFairyland activityFairyland : activityFairylandList) {
			activityFairylands.put(activityFairyland.getId(), activityFairyland);
		}

		List<PayExchangeRes> payExchangeList = JsonImporter.fileImporter(PAY_EXCHANGE, new TypeReference<List<PayExchangeRes>>() {
		});
		for (PayExchangeRes payEverydayRes : payExchangeList) {
			payExchange.put(payEverydayRes.getId(), payEverydayRes.toPayeExchange());
		}
		List<PayExchangeShowRes> payExchangeShowList = JsonImporter.fileImporter(PAY_EXCHANGE_SHOW, new TypeReference<List<PayExchangeShowRes>>() {
		});
		for (PayExchangeShowRes p : payExchangeShowList) {
			payExchangeShow = p;
		}

		long end = System.currentTimeMillis();
		System.out.println("活动脚本加载耗时：" + (end - start));
	}

	public ActivityFairyland getActivityFairyland(int id) {
		if (activityFairylands.containsKey(id)) {
			return activityFairylands.get(id);
		}
		return null;
	}

	public int getChangeCoin(int money) {
		if (changeCoin.containsKey(money)) {
			return changeCoin.get(money);
		}
		return 0;
	}

	public int getChangeGold(int money) {
		if (changeGold.containsKey(money)) {
			return changeGold.get(money);
		}
		return 0;
	}

	public List<Integer> randomQiDaERen(int level) {
		List<Integer> list = new ArrayList<Integer>();
		Collections.shuffle(qiDaERens);
		for (QiDaERen q : qiDaERens) {
			if (q.getLevel() <= level) {
				int monsterId = q.getMonster();
				if (!list.contains(monsterId)) {
					list.add(monsterId);
				}
			}
			if (list.size() >= 3) {
				break;
			}
		}
		return list;
	}

	public DrawingAward getDrawingAwards(int index) {
		return drawingAwards.get(index);
	}

	public HuntTreasureInfo getHuntTreasure(int index) {
		return huntTreasureInfos.get(index);
	}

	public HuntTreasureInfo randomHuntTreasure(int type) {
		int rate = 0;
		int finalRate = Util.getRandom(10000);
		for (HuntTreasureInfo huntTreasureInfo : huntTreasureInfos) {
			if (type == 2) {
				rate += huntTreasureInfo.getProbability2();
			} else {
				rate += huntTreasureInfo.getProbability1();
			}
			if (finalRate < rate) {
				return huntTreasureInfo;
			}
		}
		return null;
	}

	public DrawingSite randomSite() {
		return drawingSites.get(Util.getRandom(drawingSites.size()));
	}

	public DrawingSite randomSite(int mapId) {
		if (drawingMapSites.containsKey(mapId)) {
			List<DrawingSite> list = drawingMapSites.get(mapId);
			if (!list.isEmpty()) {
				return list.get(Util.getRandom(list.size()));
			}
		}

		return null;
	}

	public OnlineAwardInfo getOnlineAwardInfo(int id) {
		return activityOnlineMap.get(id);
	}

	/**
	 * @return the payFirstInfo
	 */
	public PayFirstInfo getPayFirstInfo() {
		return payFirstInfo;
	}

	/**
	 * @param payFirstInfo
	 *            the payFirstInfo to set
	 */
	public void setPayFirstInfo(PayFirstInfo payFirstInfo) {
		this.payFirstInfo = payFirstInfo;
	}

	public List<OnlineAwardInfo> getActivityOnlineList() {
		return activityOnlineList;
	}

	/**
	 * @return the activityFbls
	 */
	public Map<Integer, ActivityFblRes> getActivityFbls() {
		return activityFbls;
	}

	/**
	 * @return the plusMoney
	 */
	public Map<Integer, PlusMoney> getPlusMoney() {
		return plusMoney;
	}

	/**
	 * @param plusMoney
	 *            the plusMoney to set
	 */
	public void setPlusMoney(Map<Integer, PlusMoney> plusMoney) {
		this.plusMoney = plusMoney;
	}

	/**
	 * @return the payExchange
	 */
	public Map<Integer, PayExchange> getPayExchange() {
		return payExchange;
	}

	/**
	 * @param payExchange
	 *            the payExchange to set
	 */
	public void setPayExchange(Map<Integer, PayExchange> payExchange) {
		this.payExchange = payExchange;
	}

	/**
	 * @return the payExchangeShow
	 */
	public PayExchangeShowRes getPayExchangeShow() {
		return payExchangeShow;
	}

	/**
	 * @param payExchangeShow
	 *            the payExchangeShow to set
	 */
	public void setPayExchangeShow(PayExchangeShowRes payExchangeShow) {
		this.payExchangeShow = payExchangeShow;
	}

}