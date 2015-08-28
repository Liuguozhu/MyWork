package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.mining.emoMap.EmoMapDesRes;
import iyunu.NewTLOL.model.mining.emoMap.EmoMapInfoRes;
import iyunu.NewTLOL.model.mining.emoMap.EmoMapRes;
import iyunu.NewTLOL.model.mining.res.MiningRes;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public final class MiningJson {

	/**
	 * 私有构造方法
	 */
	private MiningJson() {

	}

	private static MiningJson instance = new MiningJson();
	private static final String MINING = "json/" + ServerManager.SERVER_RES + "/Mining.json.txt";
	Map<Integer, MiningRes> mining = new HashMap<Integer, MiningRes>();
	private static final String EMOMAP1 = "json/" + ServerManager.SERVER_RES + "/EmoMap1.json.txt";
	private static final String EMOMAP2 = "json/" + ServerManager.SERVER_RES + "/EmoMap2.json.txt";
	Map<Integer, EmoMapRes> emoMap1 = new HashMap<>();
	Map<Integer, EmoMapRes> emoMap2 = new HashMap<>();
	private static final String EMOMAPINFO = "json/" + ServerManager.SERVER_RES + "/EmoMapInfo.json.txt";
	EmoMapInfoRes emoMapInfoRes = new EmoMapInfoRes();
	private static final String EMOMAPDES = "json/" + ServerManager.SERVER_RES + "/EmoMapDes.json.txt";
	Map<Integer, EmoMapDesRes> emoDes = new HashMap<>();

	/**
	 * 获取单例对象
	 * 
	 * @return 单例对象
	 */
	public static MiningJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		mining.clear();
		emoMap1.clear();
		emoMap2.clear();
		emoMapInfoRes = new EmoMapInfoRes();
		emoDes.clear();
	}

	/**
	 * 初始化
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<MiningRes> list = JsonImporter.fileImporter(MINING, new TypeReference<List<MiningRes>>() {
		});

		for (MiningRes miningRes : list) {
			mining.put(miningRes.getIndex(), miningRes);
		}

		List<EmoMapRes> list1 = JsonImporter.fileImporter(EMOMAP1, new TypeReference<List<EmoMapRes>>() {
		});

		for (EmoMapRes e : list1) {
			emoMap1.put(e.getId(), e);
		}
		List<EmoMapRes> list4 = JsonImporter.fileImporter(EMOMAP2, new TypeReference<List<EmoMapRes>>() {
		});

		for (EmoMapRes e : list4) {
			emoMap2.put(e.getId(), e);
		}
		List<EmoMapInfoRes> list2 = JsonImporter.fileImporter(EMOMAPINFO, new TypeReference<List<EmoMapInfoRes>>() {
		});
		for (EmoMapInfoRes e : list2) {
			emoMapInfoRes.setEmo1Map(e.getEmo1Map());
			emoMapInfoRes.setEmo2Map(e.getEmo2Map());
		}

		List<EmoMapDesRes> list3 = JsonImporter.fileImporter(EMOMAPDES, new TypeReference<List<EmoMapDesRes>>() {
		});
		for (EmoMapDesRes e : list3) {
			emoDes.put(e.getMapId(), e);
		}
		long end = System.currentTimeMillis();
		System.out.println("矿区脚本加载耗时：" + (end - start));
	}

	/**
	 * @return the mining
	 */
	public Map<Integer, MiningRes> getMining() {
		return mining;
	}

	/**
	 * @param mining
	 *            the mining to set
	 */
	public void setMining(Map<Integer, MiningRes> mining) {
		this.mining = mining;
	}

	/**
	 * @return the emoMap1
	 */
	public Map<Integer, EmoMapRes> getEmoMap1() {
		return emoMap1;
	}

	/**
	 * @param emoMap1
	 *            the emoMap1 to set
	 */
	public void setEmoMap1(Map<Integer, EmoMapRes> emoMap1) {
		this.emoMap1 = emoMap1;
	}

	/**
	 * @return the emoMapInfoRes
	 */
	public EmoMapInfoRes getEmoMapInfoRes() {
		return emoMapInfoRes;
	}

	/**
	 * @param emoMapInfoRes
	 *            the emoMapInfoRes to set
	 */
	public void setEmoMapInfoRes(EmoMapInfoRes emoMapInfoRes) {
		this.emoMapInfoRes = emoMapInfoRes;
	}

	/**
	 * @return the emoDes
	 */
	public Map<Integer, EmoMapDesRes> getEmoDes() {
		return emoDes;
	}

	/**
	 * @param emoDes
	 *            the emoDes to set
	 */
	public void setEmoDes(Map<Integer, EmoMapDesRes> emoDes) {
		this.emoDes = emoDes;
	}

	/**
	 * @return the emoMap2
	 */
	public Map<Integer, EmoMapRes> getEmoMap2() {
		return emoMap2;
	}

	/**
	 * @param emoMap2
	 *            the emoMap2 to set
	 */
	public void setEmoMap2(Map<Integer, EmoMapRes> emoMap2) {
		this.emoMap2 = emoMap2;
	}

}
