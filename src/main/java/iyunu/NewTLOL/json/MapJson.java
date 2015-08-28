package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.model.map.instance.CollectedInfo;
import iyunu.NewTLOL.model.map.instance.MapGangFightInfo;
import iyunu.NewTLOL.model.map.instance.MapGangInfo;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.model.map.instance.NpcInfo;
import iyunu.NewTLOL.model.map.instance.TransferInfo;
import iyunu.NewTLOL.model.map.res.CollectedInfoRes;
import iyunu.NewTLOL.model.map.res.MapInfoRes;
import iyunu.NewTLOL.model.map.res.NpcInfoRes;
import iyunu.NewTLOL.model.map.res.TransferInfoRes;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.TypeReference;

public final class MapJson {

	/**
	 * 私有构造方法
	 */
	private MapJson() {

	}

	private static MapJson instance = new MapJson();
	private static final String MAP_INFO = "json/" + ServerManager.SERVER_RES + "/MapInfo.json.txt";
	private static final String NCP_INFO = "json/" + ServerManager.SERVER_RES + "/MapNpc.json.txt";
	private static final String TRANSFER_INFO = "json/" + ServerManager.SERVER_RES + "/MapTransfer.json.txt";
	private static final String COLLECTED_INFO = "json/" + ServerManager.SERVER_RES + "/MapCollected.json.txt";
	
	private Map<Integer, NpcInfo> npcInfos = new HashMap<Integer, NpcInfo>();
	private Map<Integer, TransferInfo> transferInfos = new HashMap<Integer, TransferInfo>();
	private Map<Integer, CollectedInfo> collectedInfos = new HashMap<Integer, CollectedInfo>();
	private Map<Integer, BaseMap> mapInfos = new HashMap<Integer, BaseMap>();
	private Map<Integer, MapRaidsInfo> mapRaidsInfos = new HashMap<Integer, MapRaidsInfo>();
	private MapGangInfo mapGangInfo;
	private MapGangFightInfo mapGangFightInfo;

	/**
	 * 获取MonsterJson对象
	 * 
	 * @return MonsterJson对象
	 */
	public static MapJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		npcInfos.clear();
		transferInfos.clear();
		mapInfos.clear();
		collectedInfos.clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<MapInfoRes> mapInfoResList = JsonImporter.fileImporter(MAP_INFO, new TypeReference<List<MapInfoRes>>() {
		});
		for (MapInfoRes mapInfoRes : mapInfoResList) {
			mapInfos.put(mapInfoRes.getId(), mapInfoRes.toMapInfo());
		}

		List<NpcInfoRes> npcInfoResList = JsonImporter.fileImporter(NCP_INFO, new TypeReference<List<NpcInfoRes>>() {
		});
		for (NpcInfoRes npcInfoRes : npcInfoResList) {
			npcInfos.put(npcInfoRes.getId(), npcInfoRes.toNpcInfo());
		}

		List<TransferInfoRes> transferInfoResList = JsonImporter.fileImporter(TRANSFER_INFO, new TypeReference<List<TransferInfoRes>>() {
		});
		for (TransferInfoRes transferInfoRes : transferInfoResList) {
			transferInfos.put(transferInfoRes.getId(), transferInfoRes.toTransferInfo());
		}

		List<CollectedInfoRes> collectedInfoResList = JsonImporter.fileImporter(COLLECTED_INFO, new TypeReference<List<CollectedInfoRes>>() {
		});
		for (CollectedInfoRes collectedInfoRes : collectedInfoResList) {
			collectedInfos.put(collectedInfoRes.getId(), collectedInfoRes.toCollectedInfo());
		}

		Set<Entry<Integer, BaseMap>> set = mapInfos.entrySet();
		for (Iterator<Entry<Integer, BaseMap>> it = set.iterator(); it.hasNext();) {
			Entry<Integer, BaseMap> entry = it.next();
			BaseMap baseMap = entry.getValue();

			if (baseMap.getType().equals(EMapType.gang)) {
				mapGangInfo = (MapGangInfo) baseMap;
			} else if (baseMap.getType().equals(EMapType.raids)) {
				mapRaidsInfos.put(baseMap.getId(), (MapRaidsInfo) baseMap);
			} else if (baseMap.getType().equals(EMapType.gangFight)) {
				mapGangFightInfo = (MapGangFightInfo) baseMap;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("地图脚本加载耗时：" + (end - start));
	}

	/**
	 * 获取帮派地图
	 * 
	 * @return 帮派地图
	 */
	public MapGangInfo getMapGangInfo(Gang gang) {
		MapGangInfo map = mapGangInfo.copy();
		map.setGang(gang);
		return map;
	}

	/**
	 * 获取副本地图
	 * 
	 * @param mapId
	 *            地图编号
	 * @return 副本地图
	 */
	public MapRaidsInfo getMapRaidsInfo(int mapId) {
		if (mapRaidsInfos.containsKey(mapId)) {
			return mapRaidsInfos.get(mapId).copy();
		}
		return null;
	}

	/**
	 * 根据编号获取地图信息
	 * 
	 * @param id
	 *            地图编号
	 * @return 地图信息对象
	 */
	public BaseMap getMapById(int id) {
		if (mapInfos.containsKey(id)) {
			return mapInfos.get(id);
		}
		return null;
	}

	/**
	 * 根据编号获取NPC信息
	 * 
	 * @param id
	 *            NPC编号
	 * @return NPC信息对象
	 */
	public NpcInfo getNpcById(int id) {
		if (npcInfos.containsKey(id)) {
			return npcInfos.get(id);
		}
		return null;
	}

	/**
	 * 根据编号获取传送点信息
	 * 
	 * @param id
	 *            传送点编号
	 * @return 传送点信息对象
	 */
	public TransferInfo getTransferById(int id) {
		if (transferInfos.containsKey(id)) {
			return transferInfos.get(id);
		}
		return null;
	}

	public CollectedInfo getCollectedInfoById(int id) {
		if (collectedInfos.containsKey(id)) {
			return collectedInfos.get(id);
		}
		return null;
	}

	/**
	 * @function 获取帮派战地图
	 * @author LuoSR
	 * @param index
	 *            帮派战地图索引
	 * @return 帮派战地图信息对象
	 * @date 2014年6月30日
	 */
	public MapGangFightInfo getMapGangFightInfo() {
		return mapGangFightInfo.copy();
		// map.setIndex(index);
		// map;
	}

}
