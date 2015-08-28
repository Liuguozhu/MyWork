package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.skillBook.HuntskillMapInfo;
import iyunu.NewTLOL.model.skillBook.instance.HuntskillCoord;
import iyunu.NewTLOL.model.skillBook.res.HuntskillCoordRes;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.TypeReference;

public final class HuntskillJson {

	/**
	 * 私有构造方法
	 */
	private HuntskillJson() {

	}

	private static HuntskillJson instance = new HuntskillJson();
	private static final String HUNTSKILLCOORD = "json/" + ServerManager.SERVER_RES + "/HuntskillCoord.json.txt";
	private static final String HUNTSKILLMAPINFO = "json/" + ServerManager.SERVER_RES + "/HuntskillMapInfo.json.txt";

	private List<HuntskillCoord> huntskillCoordList = new ArrayList<HuntskillCoord>();
	private HuntskillMapInfo huntskillMapInfo;

	/**
	 * 获取SkillBookJson对象
	 * 
	 * @return SkillBookJson对象
	 */
	public static HuntskillJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		// exchangeRatioMap.clear();
		huntskillCoordList.clear();
		huntskillMapInfo = null;
	}

	/**
	 * 初始化物品模板
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		// List<ExchangeBookRes> exchangeBookResList =
		// JsonImporter.fileImporter(EXCHANGERATIO, new
		// TypeReference<List<ExchangeBookRes>>() {
		// });
		// for (ExchangeBookRes exchangeBookRes : exchangeBookResList) {
		// exchangeRatioMap.put(exchangeBookRes.getId(),
		// exchangeBookRes.toExchangeBook());
		// }

		List<HuntskillCoordRes> huntskillCoordResList = JsonImporter.fileImporter(HUNTSKILLCOORD, new TypeReference<List<HuntskillCoordRes>>() {
		});
		for (HuntskillCoordRes huntskillCoordRes : huntskillCoordResList) {
			huntskillCoordList.add(huntskillCoordRes.toHuntskillCoord());
		}

		List<HuntskillMapInfo> huntskillMapInfoList = JsonImporter.fileImporter(HUNTSKILLMAPINFO, new TypeReference<List<HuntskillMapInfo>>() {
		});
		huntskillMapInfo = huntskillMapInfoList.get(0);

		long end = System.currentTimeMillis();
		System.out.println("猎技脚本加载耗时：" + (end - start));
	}

	public HuntskillCoord radomHuntskillCoord() {
		return huntskillCoordList.get(Util.getRandom(huntskillCoordList.size()));
	}

	// public Map<Integer, ExchangeBook> getExchangeRatioMap() {
	// return exchangeRatioMap;
	// }

	/**
	 * @return the huntskillMapInfo
	 */
	public HuntskillMapInfo getHuntskillMapInfo() {
		return huntskillMapInfo;
	}

	/**
	 * @param huntskillMapInfo
	 *            the huntskillMapInfo to set
	 */
	public void setHuntskillMapInfo(HuntskillMapInfo huntskillMapInfo) {
		this.huntskillMapInfo = huntskillMapInfo;
	}

}
