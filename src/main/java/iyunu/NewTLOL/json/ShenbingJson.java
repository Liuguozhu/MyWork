package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.shenbing.res.ShenbingAddPropertyRes;
import iyunu.NewTLOL.model.shenbing.res.ShenbingResetRes;
import iyunu.NewTLOL.model.shenbing.res.ShenbingUpRes;
import iyunu.NewTLOL.model.shenbing.res.ShenbingUpStarRes;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ArrayListMultimap;

/**
 * @function 神兵
 * @author LuoSR
 * @date 2014年7月14日
 */
public final class ShenbingJson {

	/**
	 * 私有构造方法
	 */
	private ShenbingJson() {

	}

	private static ShenbingJson instance = new ShenbingJson();
	private static final String SHENBING_UP = "json/" + ServerManager.SERVER_RES + "/ShenbingUp.json.txt";
	private static final String SHENBING_RESET = "json/" + ServerManager.SERVER_RES + "/ShenbingReset.json.txt";
	private static final String SHENBING_UPSTAR = "json/" + ServerManager.SERVER_RES + "/ShenbingUpStar.json.txt";
	private static final String SHENBING_ADD_PROPERTY = "json/" + ServerManager.SERVER_RES + "/ShenbingAddProperty.json.txt";

	private Map<Integer, ShenbingUpRes> shenbingUpMap = new HashMap<Integer, ShenbingUpRes>();
	private ArrayListMultimap<Integer, ShenbingResetRes> shenbingResetMap = ArrayListMultimap.create();
//	private Map<Integer, List<ShenbingResetRes>> shenbingResetMap = new HashMap<Integer, List<ShenbingResetRes>>();
	private Map<Integer, ShenbingUpStarRes> shenbingUpStarMap = new HashMap<Integer, ShenbingUpStarRes>();
	private Map<Integer, Integer> shenbingAddProperty = new HashMap<Integer, Integer>();

	/**
	 * 获取EquipUpGradeJson对象
	 * 
	 * @return EquipUpGradeJson对象
	 */
	public static ShenbingJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		shenbingUpMap.clear();
		shenbingResetMap.clear();
		shenbingUpStarMap.clear();
		shenbingAddProperty.clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<ShenbingUpRes> shenbingUpStartResList = JsonImporter.fileImporter(SHENBING_UP, new TypeReference<List<ShenbingUpRes>>() {
		});
		for (ShenbingUpRes shenbingUpStartRes : shenbingUpStartResList) {
			shenbingUpMap.put(shenbingUpStartRes.getId(), shenbingUpStartRes);
		}

		List<ShenbingResetRes> shenbingSubjoinResList = JsonImporter.fileImporter(SHENBING_RESET, new TypeReference<List<ShenbingResetRes>>() {
		});
		for (ShenbingResetRes shenbingSubjoinRes : shenbingSubjoinResList) {
			
			shenbingResetMap.put(shenbingSubjoinRes.getSteps(), shenbingSubjoinRes);
//			if (shenbingResetMap.containsKey(shenbingSubjoinRes.getSteps())) {
//				shenbingResetMap.get(shenbingSubjoinRes.getSteps()).add(shenbingSubjoinRes);
//			} else {
//				List<ShenbingResetRes> shenbingSubjoinRess = new ArrayList<ShenbingResetRes>();
//				shenbingSubjoinRess.add(shenbingSubjoinRes);
//				shenbingResetMap.put(shenbingSubjoinRes.getSteps(), shenbingSubjoinRess);
//			}
		}

		List<ShenbingUpStarRes> shenbingUpStarResList = JsonImporter.fileImporter(SHENBING_UPSTAR, new TypeReference<List<ShenbingUpStarRes>>() {
		});
		for (ShenbingUpStarRes shenbingUpStarRes : shenbingUpStarResList) {
			shenbingUpStarMap.put(shenbingUpStarRes.getIndex(), shenbingUpStarRes);
		}

		List<ShenbingAddPropertyRes> shenbingAddPropertyResList = JsonImporter.fileImporter(SHENBING_ADD_PROPERTY, new TypeReference<List<ShenbingAddPropertyRes>>() {
		});
		for (ShenbingAddPropertyRes shenbingAddPropertyRes : shenbingAddPropertyResList) {
			shenbingAddProperty.put(shenbingAddPropertyRes.getNum(), shenbingAddPropertyRes.getProbability());
		}

		long end = System.currentTimeMillis();
		System.out.println("神兵脚本加载耗时：" + (end - start));
	}

	/**
	 * 获取神兵升级信息
	 * 
	 * @param oldId
	 *            旧神兵编号
	 * @return 神兵升级信息
	 */
	public ShenbingUpRes getShenbingUpById(int oldId) {
		return shenbingUpMap.get(oldId);
	}

	/**
	 * @function 根据神兵阶段得到附加属性列表
	 * @author LuoSR
	 * @param steps
	 *            阶段
	 * @return 附加属性列表
	 * @date 2014年7月14日
	 */
	public List<ShenbingResetRes> getShenbingSubjoinResList(int steps) {
		return shenbingResetMap.get(steps);
	}

	public ShenbingResetRes randomProperty(int steps) {
		if (shenbingResetMap.containsKey(steps)) {
			List<ShenbingResetRes> list = shenbingResetMap.get(steps);
			int finalRate = Util.getRandom(10000);
			int num = 0;
			for (ShenbingResetRes shenbingResetRes : list) {
				num += shenbingResetRes.getWeight();
				if (finalRate <= num) {
					return shenbingResetRes;
				}
			}
		}
		return null;
	}

	public int getProbability(int num) {
		if (shenbingAddProperty.containsKey(num)) {
			return shenbingAddProperty.get(num);
		}
		return 0;
	}

	public Map<Integer, ShenbingUpRes> getShenbingUpResMap() {
		return shenbingUpMap;
	}

	public Map<Integer, ShenbingUpStarRes> getShenbingUpStarResMap() {
		return shenbingUpStarMap;
	}

}
