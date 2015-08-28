package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.gang.GangFightMapInfo;
import iyunu.NewTLOL.model.gang.res.GangActiveEventRes;
import iyunu.NewTLOL.model.gang.res.GangActivityRes;
import iyunu.NewTLOL.model.gang.res.GangLevelRes;
import iyunu.NewTLOL.model.gang.res.GangTask;
import iyunu.NewTLOL.model.gang.res.GangTaskAllAwardRes;
import iyunu.NewTLOL.model.gang.res.GangTaskRes;
import iyunu.NewTLOL.model.gang.res.GangTributeEventRes;
import iyunu.NewTLOL.model.gang.res.ShaoXiangRes;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public final class GangJson {

	/**
	 * 私有构造方法
	 */
	private GangJson() {

	}

	private static GangJson instance = new GangJson();
	private static final String GANG_LEVEL = "json/" + ServerManager.SERVER_RES + "/GangLevel.json.txt";
	private static final String GANG_SHAOXIANG = "json/" + ServerManager.SERVER_RES + "/GangShaoXiang.json.txt";
	private static final String GANG_TRIBUTE = "json/" + ServerManager.SERVER_RES + "/GangTribute.json.txt";
	private static final String GANG_ACTIVE = "json/" + ServerManager.SERVER_RES + "/GangActiveEvent.json.txt";
	private static final String GANG_ACTIVITY = "json/" + ServerManager.SERVER_RES + "/GangActivity.json.txt";
	private static final String GANG_GANGFIGHTMAPINFO = "json/" + ServerManager.SERVER_RES + "/GangFightMapInfo.json.txt";
	private static final String GANG_TASKALLWARD = "json/" + ServerManager.SERVER_RES + "/gangTaskAllAward.json.txt";
	private static final String GANG_TASK = "json/" + ServerManager.SERVER_RES + "/gangTask.json.txt";
	
	private Map<Integer, GangLevelRes> level = new HashMap<Integer, GangLevelRes>();
	private List<GangActivityRes> gangActivities = new ArrayList<GangActivityRes>();
	private GangFightMapInfo gangFightMapInfo;

	private Map<Integer, MonsterDropItem> gangTaskAllAward = new HashMap<>();
	/** 前是 gangTask的 zoneId,后面是 gangTask的index */
	private Map<Integer, List<Integer>> gangTaskZone = new HashMap<>();
	/** 前是 gangtask index ，后面是gangTask的对象 */
	private Map<Integer, GangTask> gangTask = new HashMap<>();

	/**
	 * 获取单例对象
	 * 
	 * @return 单例对象
	 */
	public static GangJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		// shops.clear();
		level.clear();
		gangActivities.clear();
		// gangFightDesc = null;
		gangFightMapInfo = null;
		gangTaskAllAward.clear();
		gangTaskZone.clear();
		gangTask.clear();
	}

	/**
	 * 初始化
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		// List<GangShopRes> gangShopResList =
		// JsonImporter.fileImporter(GANG_SHOP, new
		// TypeReference<List<GangShopRes>>() {
		// });

		// for (GangShopRes shopRes : gangShopResList) {
		// shops.put(shopRes.getIndex(), shopRes.toGangShop());
		// }
		List<GangLevelRes> gangLevelResList = JsonImporter.fileImporter(GANG_LEVEL, new TypeReference<List<GangLevelRes>>() {
		});

		for (GangLevelRes gangLevelRes : gangLevelResList) {
			level.put(gangLevelRes.getLevel(), gangLevelRes);
		}

		List<ShaoXiangRes> shaoXiangResList = JsonImporter.fileImporter(GANG_SHAOXIANG, new TypeReference<List<ShaoXiangRes>>() {
		});
		for (ShaoXiangRes shaoXiangRes : shaoXiangResList) {
			shaoXiangRes.getType().setExp(shaoXiangRes.getExp());
			shaoXiangRes.getType().setGoldBuffId(shaoXiangRes.getGoldBuff());
			shaoXiangRes.getType().setExpBuffId(shaoXiangRes.getExpBuff());
		}

		List<GangTributeEventRes> gangTributeEventResList = JsonImporter.fileImporter(GANG_TRIBUTE, new TypeReference<List<GangTributeEventRes>>() {
		});
		for (GangTributeEventRes gangTributeEventRes : gangTributeEventResList) {
			gangTributeEventRes.getAction().setExp(gangTributeEventRes.getExp());
			gangTributeEventRes.getAction().setTribute(gangTributeEventRes.getTribute());
			gangTributeEventRes.getAction().setGangTribute(gangTributeEventRes.getGangTribute());
			gangTributeEventRes.getAction().setGangActivity(gangTributeEventRes.getGangActivity());
		}

		List<GangActiveEventRes> gangActiveEventResList = JsonImporter.fileImporter(GANG_ACTIVE, new TypeReference<List<GangActiveEventRes>>() {
		});
		for (GangActiveEventRes gangActiveEventRes : gangActiveEventResList) {
			gangActiveEventRes.getAction().setActive(gangActiveEventRes.getExp());
		}

		gangActivities = JsonImporter.fileImporter(GANG_ACTIVITY, new TypeReference<List<GangActivityRes>>() {
		});

		// List<GangFightDescRes> gangFightDescList =
		// JsonImporter.fileImporter(GANG_FIGHT_DESC, new
		// TypeReference<List<GangFightDescRes>>() {
		// });

		// gangFightDesc = gangFightDescList.get(0).toGangFightDesc();

		List<GangFightMapInfo> gangFightMapInfoList = JsonImporter.fileImporter(GANG_GANGFIGHTMAPINFO, new TypeReference<List<GangFightMapInfo>>() {
		});
		gangFightMapInfo = gangFightMapInfoList.get(0);

		List<GangTaskAllAwardRes> gangTaskAllAwardList = JsonImporter.fileImporter(GANG_TASKALLWARD, new TypeReference<List<GangTaskAllAwardRes>>() {
		});
		for (GangTaskAllAwardRes gangTaskAllAwardRes : gangTaskAllAwardList) {
			gangTaskAllAward.put(gangTaskAllAwardRes.getIndex(), gangTaskAllAwardRes.toGangTaskAllAward().getItems().get(0));
		}
		List<GangTaskRes> gangTaskResList = JsonImporter.fileImporter(GANG_TASK, new TypeReference<List<GangTaskRes>>() {
		});
		for (GangTaskRes gangTaskRes : gangTaskResList) {
			gangTask.put(gangTaskRes.getIndex(), gangTaskRes.toGangTask());
			if (gangTaskZone.containsKey(gangTaskRes.getZone())) {
				gangTaskZone.get(gangTaskRes.getZone()).add(gangTaskRes.getIndex());
			} else {
				List<Integer> list = new ArrayList<>();
				list.add(gangTaskRes.getIndex());
				gangTaskZone.put(gangTaskRes.getZone(), list);
			}

		}

		long end = System.currentTimeMillis();
		System.out.println("帮派脚本加载耗时：" + (end - start));
	}

	/**
	 * 根据索引获取商店物品
	 * 
	 * @param index
	 *            索引
	 * @return 商店物品
	 */
	// public GangShop getShopByIndex(int index) {
	// if (shops.containsKey(index)) {
	// return shops.get(index);
	// }
	// return null;
	// }

	/**
	 * 根据级别获取经验表
	 * 
	 * @param index
	 *            索引
	 * @return 等级表
	 */
	public GangLevelRes getGangLevelByIndex(int index) {
		if (level.containsKey(index)) {
			return level.get(index);
		}
		return null;
	}

	/**
	 * 根据经验和原等级看是否升级了 *
	 * 
	 * @param exp
	 *            现总经验
	 * @return true可以升级，false不可升级
	 */
	public boolean getUpdate(int exp, int oldLevel) {
		if (oldLevel < 5) {
			GangLevelRes gangLevel = level.get(oldLevel + 1);
			if (exp > gangLevel.getExp()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 根据等级查看最大人数
	 * 
	 * @param oldLevel
	 *            现等级
	 * @return int 人数
	 */
	public int getMaxNum(int oldLevel) {
		if (level.containsKey(oldLevel)) {
			return level.get(oldLevel).getUpNum();
		}
		return level.get(1).getUpNum();
	}

	/**
	 * @return the shops
	 */
	// public Map<Integer, GangShop> getShops() {
	// return shops;
	// }

	/**
	 * @return the gangActivities
	 */
	public List<GangActivityRes> getGangActivities() {
		return gangActivities;
	}

	// public GangFightDesc getGangFightDesc() {
	// return gangFightDesc;
	// }
	//
	// public void setGangFightDesc(GangFightDesc gangFightDesc) {
	// this.gangFightDesc = gangFightDesc;
	// }

	public GangFightMapInfo getGangFightMapInfo() {
		return gangFightMapInfo;
	}

	public void setGangFightMapInfo(GangFightMapInfo gangFightMapInfo) {
		this.gangFightMapInfo = gangFightMapInfo;
	}

	/**
	 * @return the gangTaskAllAward
	 */
	public Map<Integer, MonsterDropItem> getGangTaskAllAward() {
		return gangTaskAllAward;
	}

	/**
	 * @param gangTaskAllAward
	 *            the gangTaskAllAward to set
	 */
	public void setGangTaskAllAward(Map<Integer, MonsterDropItem> gangTaskAllAward) {
		this.gangTaskAllAward = gangTaskAllAward;
	}

	/**
	 * @return the gangTaskZone
	 */
	public Map<Integer, List<Integer>> getGangTaskZone() {
		return gangTaskZone;
	}

	/**
	 * @param gangTaskZone
	 *            the gangTaskZone to set
	 */
	public void setGangTaskZone(Map<Integer, List<Integer>> gangTaskZone) {
		this.gangTaskZone = gangTaskZone;
	}

	/**
	 * @return the gangTask
	 */
	public Map<Integer, GangTask> getGangTask() {
		return gangTask;
	}

	/**
	 * @param gangTask
	 *            the gangTask to set
	 */
	public void setGangTask(Map<Integer, GangTask> gangTask) {
		this.gangTask = gangTask;
	}

}
