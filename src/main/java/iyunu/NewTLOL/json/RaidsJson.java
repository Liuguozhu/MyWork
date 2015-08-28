package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.raids.instance.RaidsInfo;
import iyunu.NewTLOL.model.raids.res.RaidsInfoRes;
import iyunu.NewTLOL.model.trials.instance.TrialsInfo;
import iyunu.NewTLOL.model.trials.res.TrialsInfoRes;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedHashMultimap;

public final class RaidsJson {

	/**
	 * 私有构造方法
	 */
	private RaidsJson() {

	}

	private static RaidsJson instance = new RaidsJson();
	private static final String RAIDS = "json/" + ServerManager.SERVER_RES + "/Raids.json.txt";
	private static final String TRIALS = "json/" + ServerManager.SERVER_RES + "/Trials.json.txt";

	private Map<Integer, RaidsInfo> raidsMap = new HashMap<Integer, RaidsInfo>(); // <副本编号，副本对象>
	private LinkedHashMultimap<Integer, RaidsInfo> raidsListMap = LinkedHashMultimap.create(); // <index，副本集合>
	// private Map<Integer, ArrayList<RaidsInfo>> raidsListMap = new
	// HashMap<Integer, ArrayList<RaidsInfo>>(); // <index，副本集合>
	private Map<Integer, TrialsInfo> trialsMap = new HashMap<Integer, TrialsInfo>(); // <试炼编号，试炼对象>
	private ArrayListMultimap<Integer, TrialsInfo> trialsListMap = ArrayListMultimap.create(); // <难度，试炼集合>
//	private Map<Integer, ArrayList<TrialsInfo>> trialsListMap = new HashMap<Integer, ArrayList<TrialsInfo>>(); // <难度，试炼集合>

	/**
	 * 获取单例对象
	 * 
	 * @return 单例对象
	 */
	public static RaidsJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		raidsMap.clear();
		raidsListMap.clear();
		trialsMap.clear();
		trialsListMap.clear();
	}

	/**
	 * 初始化
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<RaidsInfoRes> raidsInfoResList = JsonImporter.fileImporter(RAIDS, new TypeReference<List<RaidsInfoRes>>() {
		});

		for (RaidsInfoRes raidsRes : raidsInfoResList) {

			RaidsInfo raidsInfo = raidsRes.toRaidsInfo();
			raidsMap.put(raidsInfo.getRaidsId(), raidsInfo);

			raidsListMap.put(raidsInfo.getIndex(), raidsInfo);
			// int index = raidsInfo.getIndex();
			// if (raidsListMap.containsKey(index)) {
			// raidsListMap.get(index).add(raidsInfo);
			// } else {
			// ArrayList<RaidsInfo> arrayList = new ArrayList<RaidsInfo>();
			// arrayList.add(raidsInfo);
			// raidsListMap.put(index, arrayList);
			// }

			// RaidsManager.raidsNumberLimit.put(raidsRes.getId(),
			// raidsRes.getNumberLimit());
		}

		List<TrialsInfoRes> trialsInfoResList = JsonImporter.fileImporter(TRIALS, new TypeReference<List<TrialsInfoRes>>() {
		});

		for (TrialsInfoRes trialsInfoRes : trialsInfoResList) {
			TrialsInfo trialsInfo = trialsInfoRes.toTrialsInfo();
			trialsMap.put(trialsInfoRes.getId(), trialsInfo);
			trialsListMap.put(trialsInfo.getDegree(), trialsInfo);
//			if (trialsListMap.containsKey(trialsInfo.getDegree())) {
//				trialsListMap.get(trialsInfo.getDegree()).add(trialsInfo);
//			} else {
//				ArrayList<TrialsInfo> list = new ArrayList<TrialsInfo>();
//				list.add(trialsInfo);
//				trialsListMap.put(trialsInfo.getDegree(), list);
//			}
		}
		long end = System.currentTimeMillis();
		System.out.println("副本脚本加载耗时：" + (end - start));
	}

	/**
	 * 根据副本编号获取副本对象
	 * 
	 * @param raidsId
	 *            副本编号
	 * @return 副本对象
	 */
	public RaidsInfo getRaidsById(int raidsId) {
		if (raidsMap.containsKey(raidsId)) {
			return raidsMap.get(raidsId);
		}
		return null;
	}

	public List<TrialsInfo> getTrialsInfoList(int raidsId) {
		int degree = 0;
		if (trialsMap.containsKey(raidsId)) {
			degree = trialsMap.get(raidsId).getDegree();
		}

		if (trialsListMap.containsKey(degree)) {
			return trialsListMap.get(degree);
		}
		return new ArrayList<TrialsInfo>();
	}

	/**
	 * @return the raidsListMap
	 */
	public LinkedHashMultimap<Integer, RaidsInfo> getRaidsListMap() {
		return raidsListMap;
	}

	public Map<Integer, TrialsInfo> getTrialsMap() {
		return trialsMap;
	}

	/**
	 * @return the trialsListMap
	 */
	public ArrayListMultimap<Integer, TrialsInfo> getTrialsListMap() {
		return trialsListMap;
	}

	
//	public Map<Integer, ArrayList<TrialsInfo>> getTrialsListMap() {
//		return trialsListMap;
//	}

//	public void setTrialsListMap(Map<Integer, ArrayList<TrialsInfo>> trialsListMap) {
//		this.trialsListMap = trialsListMap;
//	}

}
