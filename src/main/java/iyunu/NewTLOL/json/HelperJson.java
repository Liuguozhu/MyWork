package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.HelperManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.helper.instance.HelperAward;
import iyunu.NewTLOL.model.helper.res.DailyInfo;
import iyunu.NewTLOL.model.helper.res.HelperAwardRes;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public class HelperJson {

	/**
	 * 私有构造方法
	 */
	private HelperJson() {

	}

	private static final HelperJson instance = new HelperJson();

	public static HelperJson instance() {
		return instance;
	}

	private static final String HELPER_AWARD = "json/" + ServerManager.SERVER_RES + "/HelperAwardRes.json.txt";
	private static final String DAILY_INFO = "json/" + ServerManager.SERVER_RES + "/DailyInfo.json.txt";

	private List<HelperAward> helperAwards = new ArrayList<HelperAward>(); // <小助手奖励对象>
	private Map<Integer, HelperAward> helperAwardMap = new HashMap<Integer, HelperAward>(); // <小助手奖励对象>
	private List<DailyInfo> dailyInfos = new ArrayList<DailyInfo>(); // 每日活动

	public void clear() {
		helperAwards.clear();
		helperAwardMap.clear();
		dailyInfos.clear();
	}

	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<HelperAwardRes> helperAwardResList = JsonImporter.fileImporter(HELPER_AWARD, new TypeReference<List<HelperAwardRes>>() {
		});

		for (HelperAwardRes helperAwardRes : helperAwardResList) {
			helperAwards.add(helperAwardRes.toHelperAward());
			helperAwardMap.put(helperAwardRes.getScore(), helperAwardRes.toHelperAward());
		}

		List<DailyInfo> dailyActiveResList = JsonImporter.fileImporter(DAILY_INFO, new TypeReference<List<DailyInfo>>() {
		});
		for (DailyInfo dailyInfoRes : dailyActiveResList) {
			dailyInfos.add(dailyInfoRes);
			dailyInfoRes.getType().setNeed(dailyInfoRes.getNeed());
			dailyInfoRes.getType().setScore(dailyInfoRes.getScore());

			switch (dailyInfoRes.getType()) {
			case answer:
				HelperManager.DAILYINFO_ANSWER = dailyInfoRes;
				break;
			case xuezhan:
				HelperManager.DAILYINFO_XUEZHAN = dailyInfoRes;
				break;
			default:
				dailyInfoRes.setState(0);
				break;
			}
		}

		long end = System.currentTimeMillis();
		System.out.println("小助手脚本加载耗时：" + (end - start));
	}

	/**
	 * 根据类型获取小助手信息
	 * 
	 * @param type
	 *            类型
	 * @return 小助手
	 */
	// public Helper getHelperByType(EHelper type) {
	// return helperTypes.get(type);
	// }
	//
	// /**
	// * @return the helpers
	// */
	// public Map<Integer, Helper> getHelpers() {
	// return helpers;
	// }

	public List<HelperAward> getHelperAwards() {
		return helperAwards;
	}

	public Map<Integer, HelperAward> getHelperAwardMap() {
		return helperAwardMap;
	}

	public void setHelperAwardMap(Map<Integer, HelperAward> helperAwardMap) {
		this.helperAwardMap = helperAwardMap;
	}

	/**
	 * @return the dailyInfos
	 */
	public List<DailyInfo> getDailyInfos() {
		return dailyInfos;
	}

	/**
	 * @return the dailyActives
	 */
	// public List<DailyActive> getDailyActives() {
	// return dailyActives;
	// }

}
