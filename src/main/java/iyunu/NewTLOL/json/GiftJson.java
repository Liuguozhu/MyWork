package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.gift.instance.GiftVip;
import iyunu.NewTLOL.model.gift.instance.LevelGift;
import iyunu.NewTLOL.model.gift.instance.Surprise;
import iyunu.NewTLOL.model.gift.res.GiftVipRes;
import iyunu.NewTLOL.model.gift.res.LevelGiftRes;
import iyunu.NewTLOL.model.gift.res.SevenGiftRes;
import iyunu.NewTLOL.model.gift.res.SurpriseRes;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ArrayListMultimap;

public final class GiftJson {

	/**
	 * 私有构造方法
	 */
	private GiftJson() {

	}

	private static GiftJson instance = new GiftJson();
	private static final String LEVEL_GIFT = "json/" + ServerManager.SERVER_RES + "/LevelGift.json.txt";
	private static final String SURPRISE_GIFT = "json/" + ServerManager.SERVER_RES + "/Surprise.json.txt";
	private static final String GIFT_VIP = "json/" + ServerManager.SERVER_RES + "/GiftVip.json.txt";
	private static final String SEVEN_GIFT = "json/" + ServerManager.SERVER_RES + "/SevenGift.json.txt";

	private Map<Integer, LevelGift> levelGiftMap = new HashMap<Integer, LevelGift>();
	private List<LevelGift> levelGiftList = new ArrayList<LevelGift>();
	private Map<Integer, Surprise> surpriseMap = new HashMap<Integer, Surprise>();
	private Map<Integer, GiftVip> giftVipMap = new HashMap<Integer, GiftVip>();

	private ArrayListMultimap<Integer, MonsterDropItem> commonGift = ArrayListMultimap.create();// 普通七天物品奖励
	private ArrayListMultimap<Integer, MonsterDropItem> vipGift = ArrayListMultimap.create();// VIP七天物品奖励
	private ArrayListMultimap<Integer, MonsterDropItem> commonPGift = ArrayListMultimap.create();// 普通七天伙伴奖励
	private ArrayListMultimap<Integer, MonsterDropItem> vipPGift = ArrayListMultimap.create();// VIP七天伙伴奖励
	// private Map<Integer, List<MonsterDropItem>> commonGift = new
	// HashMap<>();// 普通七天物品奖励
	// private Map<Integer, List<MonsterDropItem>> vipGift = new HashMap<>();//
	// VIP七天物品奖励
	// private Map<Integer, List<MonsterDropItem>> commonPGift = new
	// HashMap<>();// 普通七天伙伴奖励
	// private Map<Integer, List<MonsterDropItem>> vipPGift = new HashMap<>();//
	// VIP七天伙伴奖励
	private Map<Integer, SevenGiftRes> sevenGiftMap = new HashMap<>();// VIP七天数值奖励

	/**
	 * 获取EquipJson对象
	 * 
	 * @return EquipJson对象
	 */
	public static GiftJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		levelGiftMap.clear();
		levelGiftList.clear();
		surpriseMap.clear();
		giftVipMap.clear();
		commonGift.clear();
		vipGift.clear();
		commonPGift.clear();
		vipPGift.clear();
		sevenGiftMap.clear();
	}

	/**
	 * 初始化物品模板
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {
		long start = System.currentTimeMillis();
		clear();

		List<LevelGiftRes> levelGiftResList = JsonImporter.fileImporter(LEVEL_GIFT, new TypeReference<List<LevelGiftRes>>() {
		});
		for (LevelGiftRes levelGiftRes : levelGiftResList) {
			levelGiftMap.put(levelGiftRes.getLevel(), levelGiftRes.toLevelGift());
			levelGiftList.add(levelGiftRes.toLevelGift());
		}

		List<SurpriseRes> surpriseResList = JsonImporter.fileImporter(SURPRISE_GIFT, new TypeReference<List<SurpriseRes>>() {
		});
		for (SurpriseRes surpriseRes : surpriseResList) {
			surpriseMap.put(surpriseRes.getId(), surpriseRes.toSurprise());
		}

		List<GiftVipRes> giftvList = JsonImporter.fileImporter(GIFT_VIP, new TypeReference<List<GiftVipRes>>() {
		});
		for (GiftVipRes Gift : giftvList) {
			giftVipMap.put(Gift.getType(), Gift.toGiftVip());
		}
		List<SevenGiftRes> sevenGiftvList = JsonImporter.fileImporter(SEVEN_GIFT, new TypeReference<List<SevenGiftRes>>() {
		});
		for (SevenGiftRes sevenGiftRes : sevenGiftvList) {
			commonGift.putAll(sevenGiftRes.getId(), sevenGiftRes.toCommonGift());
			commonPGift.putAll(sevenGiftRes.getId(), sevenGiftRes.toCommonPGift());
			vipGift.putAll(sevenGiftRes.getId(), sevenGiftRes.toVipGift());
			vipPGift.putAll(sevenGiftRes.getId(), sevenGiftRes.toVipPGift());

			sevenGiftMap.put(sevenGiftRes.getId(), sevenGiftRes);
		}

		long end = System.currentTimeMillis();
		System.out.println("礼包脚本加载耗时：" + (end - start));
	}

	public List<MonsterDropItem> getCommonGiftByDay(int day) {
		return commonGift.get(day);
	}

	public List<MonsterDropItem> getVipGiftByDay(int day) {
		return vipGift.get(day);
	}

	public List<MonsterDropItem> getCommonPGiftByDay(int day) {
		return commonPGift.get(day);
	}

	public List<MonsterDropItem> getVipPGiftByDay(int day) {
		return vipPGift.get(day);
	}

	public Map<Integer, LevelGift> getLevelGiftMap() {
		return levelGiftMap;
	}

	public List<LevelGift> getLevelGiftList() {
		return levelGiftList;
	}

	public Map<Integer, Surprise> getSurpriseMap() {
		return surpriseMap;
	}

	/**
	 * @return the giftVipMap
	 */
	public Map<Integer, GiftVip> getGiftVipMap() {
		return giftVipMap;
	}

	/**
	 * @param giftVipMap
	 *            the giftVipMap to set
	 */
	public void setGiftVipMap(Map<Integer, GiftVip> giftVipMap) {
		this.giftVipMap = giftVipMap;
	}

	/**
	 * @param vipPGift
	 *            the vipPGift to set
	 */

	/**
	 * @return the sevenGiftMap
	 */
	public Map<Integer, SevenGiftRes> getSevenGiftMap() {
		return sevenGiftMap;
	}

	/**
	 * @param sevenGiftMap
	 *            the sevenGiftMap to set
	 */
	public void setSevenGiftMap(Map<Integer, SevenGiftRes> sevenGiftMap) {
		this.sevenGiftMap = sevenGiftMap;
	}

}
