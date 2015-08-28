package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.intensify.instance.BodyInfo;
import iyunu.NewTLOL.model.intensify.instance.IntensifyResolve;
import iyunu.NewTLOL.model.intensify.instance.StoneRecipe;
import iyunu.NewTLOL.model.intensify.res.BodyInfoRes;
import iyunu.NewTLOL.model.intensify.res.EquipStarRes;
import iyunu.NewTLOL.model.intensify.res.IntensifyResolveRes;
import iyunu.NewTLOL.model.intensify.res.IntensifyStoneUpRes;
import iyunu.NewTLOL.model.intensify.res.ItemMakeRes;
import iyunu.NewTLOL.model.intensify.res.StoneRecipeRes;
import iyunu.NewTLOL.model.intensify.res.StoneUp;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

/**
 * @function 装备锻造
 * @author LuoSR
 * @date 2013年12月6日
 */
public final class IntensifyJson {

	/**
	 * 私有构造方法
	 */
	private IntensifyJson() {

	}

	private static IntensifyJson instance = new IntensifyJson();
	private static final String WEAPON_INTENSIFY = "json/" + ServerManager.SERVER_RES + "/IntensifyWeapon.json.txt";
	private static final String COAT_INTENSIFY = "json/" + ServerManager.SERVER_RES + "/IntensifyCoat.json.txt";
	private static final String SHOE_INTENSIFY = "json/" + ServerManager.SERVER_RES + "/IntensifyShoe.json.txt";
	private static final String BELT_INTENSIFY = "json/" + ServerManager.SERVER_RES + "/IntensifyBelt.json.txt";
	private static final String RING_INTENSIFY = "json/" + ServerManager.SERVER_RES + "/IntensifyRing.json.txt";
	private static final String NECKLACE_INTENSIFY = "json/" + ServerManager.SERVER_RES + "/IntensifyNecklace.json.txt";
	private static final String STONE_MAKE = "json/" + ServerManager.SERVER_RES + "/StoneMake.json.txt";
	private static final String STONE_UP = "json/" + ServerManager.SERVER_RES + "/StoneUp.json.txt";
	private static final String EQUIP_STAR = "json/" + ServerManager.SERVER_RES + "/EquipStar.json.txt";
	private static final String ITEM_MAKE = "json/" + ServerManager.SERVER_RES + "/ItemMake.json.txt";
	private static final String INTENSIFY_RESOLVE = "json/" + ServerManager.SERVER_RES + "/IntensifyResolve.json.txt";
	private static final String INTENSIFY_STONE_UP = "json/" + ServerManager.SERVER_RES + "/IntensifyStoneUp.json.txt";

	Map<Integer, BodyInfo> weaponIntensifyMap = new HashMap<Integer, BodyInfo>();
	Map<Integer, BodyInfo> coatIntensifyMap = new HashMap<Integer, BodyInfo>();
	Map<Integer, BodyInfo> shoeIntensifyMap = new HashMap<Integer, BodyInfo>();
	Map<Integer, BodyInfo> capIntensifyMap = new HashMap<Integer, BodyInfo>();
	Map<Integer, BodyInfo> ringIntensifyMap = new HashMap<Integer, BodyInfo>();
	Map<Integer, BodyInfo> necklaceIntensifyMap = new HashMap<Integer, BodyInfo>();
	Map<Integer, StoneRecipe> equipStoneMap = new HashMap<Integer, StoneRecipe>();
	Map<Integer, StoneUp> stoneUp = new HashMap<Integer, StoneUp>();
	Map<Integer, EquipStarRes> equipStar = new HashMap<Integer, EquipStarRes>();
	Map<Integer, ItemMakeRes> itemMakeResMap = new HashMap<Integer, ItemMakeRes>();
	Map<Integer, IntensifyResolve> intensifyResolveMap = new HashMap<Integer, IntensifyResolve>();
	Map<Integer, IntensifyStoneUpRes> intensifyStoneUpResMap = new HashMap<Integer, IntensifyStoneUpRes>(); // level,升星对象

	/**
	 * 获取EquipUpGradeJson对象
	 * 
	 * @return EquipUpGradeJson对象
	 */
	public static IntensifyJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		weaponIntensifyMap.clear();
		coatIntensifyMap.clear();
		shoeIntensifyMap.clear();
		capIntensifyMap.clear();
		ringIntensifyMap.clear();
		necklaceIntensifyMap.clear();
		equipStoneMap.clear();
		stoneUp.clear();
		equipStar.clear();
		itemMakeResMap.clear();
		intensifyResolveMap.clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<BodyInfoRes> weaponIntensifyResList = JsonImporter.fileImporter(WEAPON_INTENSIFY, new TypeReference<List<BodyInfoRes>>() {
		});
		for (BodyInfoRes equipIntensifyRes : weaponIntensifyResList) {
			weaponIntensifyMap.put(equipIntensifyRes.getLevel(), equipIntensifyRes.toEquipIntensify());
		}

		List<BodyInfoRes> coatIntensifyResList = JsonImporter.fileImporter(COAT_INTENSIFY, new TypeReference<List<BodyInfoRes>>() {
		});
		for (BodyInfoRes equipIntensifyRes : coatIntensifyResList) {
			coatIntensifyMap.put(equipIntensifyRes.getLevel(), equipIntensifyRes.toEquipIntensify());
		}

		List<BodyInfoRes> shoeIntensifyResList = JsonImporter.fileImporter(SHOE_INTENSIFY, new TypeReference<List<BodyInfoRes>>() {
		});
		for (BodyInfoRes equipIntensifyRes : shoeIntensifyResList) {
			shoeIntensifyMap.put(equipIntensifyRes.getLevel(), equipIntensifyRes.toEquipIntensify());
		}

		List<BodyInfoRes> capIntensifyResList = JsonImporter.fileImporter(BELT_INTENSIFY, new TypeReference<List<BodyInfoRes>>() {
		});
		for (BodyInfoRes equipIntensifyRes : capIntensifyResList) {
			capIntensifyMap.put(equipIntensifyRes.getLevel(), equipIntensifyRes.toEquipIntensify());
		}

		List<BodyInfoRes> ringIntensifyResList = JsonImporter.fileImporter(RING_INTENSIFY, new TypeReference<List<BodyInfoRes>>() {
		});
		for (BodyInfoRes equipIntensifyRes : ringIntensifyResList) {
			ringIntensifyMap.put(equipIntensifyRes.getLevel(), equipIntensifyRes.toEquipIntensify());
		}

		List<BodyInfoRes> necklaceIntensifyResList = JsonImporter.fileImporter(NECKLACE_INTENSIFY, new TypeReference<List<BodyInfoRes>>() {
		});
		for (BodyInfoRes equipIntensifyRes : necklaceIntensifyResList) {
			necklaceIntensifyMap.put(equipIntensifyRes.getLevel(), equipIntensifyRes.toEquipIntensify());
		}

		List<StoneRecipeRes> equipStoneResList = JsonImporter.fileImporter(STONE_MAKE, new TypeReference<List<StoneRecipeRes>>() {
		});
		for (StoneRecipeRes stoneRes : equipStoneResList) {
			equipStoneMap.put(stoneRes.getId(), stoneRes.toEquipStone());
		}

		List<StoneUp> stoneUpList = JsonImporter.fileImporter(STONE_UP, new TypeReference<List<StoneUp>>() {
		});
		for (StoneUp stoneUp1 : stoneUpList) {
			stoneUp.put(stoneUp1.getId(), stoneUp1);
		}

		List<EquipStarRes> equipStarResList = JsonImporter.fileImporter(EQUIP_STAR, new TypeReference<List<EquipStarRes>>() {
		});
		for (EquipStarRes equipStarRes : equipStarResList) {
			equipStar.put(equipStarRes.getIndex(), equipStarRes);
		}

		List<ItemMakeRes> itemMakeResList = JsonImporter.fileImporter(ITEM_MAKE, new TypeReference<List<ItemMakeRes>>() {
		});
		for (ItemMakeRes itemMakeRes : itemMakeResList) {
			itemMakeResMap.put(itemMakeRes.getOldId(), itemMakeRes);
		}

		List<IntensifyResolveRes> intensifyResolveResList = JsonImporter.fileImporter(INTENSIFY_RESOLVE, new TypeReference<List<IntensifyResolveRes>>() {
		});
		for (IntensifyResolveRes intensifyResolveRes : intensifyResolveResList) {
			intensifyResolveMap.put(intensifyResolveRes.getStart(), intensifyResolveRes.toIntensifyResolve());
		}

		List<IntensifyStoneUpRes> intensifyStoneUpResList = JsonImporter.fileImporter(INTENSIFY_STONE_UP, new TypeReference<List<IntensifyStoneUpRes>>() {
		});
		for (IntensifyStoneUpRes intensifyStoneUpRes : intensifyStoneUpResList) {
			intensifyStoneUpResMap.put(intensifyStoneUpRes.getOldId(), intensifyStoneUpRes);
		}

		long end = System.currentTimeMillis();
		System.out.println("强化脚本加载耗时：" + (end - start));
	}

	public IntensifyStoneUpRes getIntensifyStoneUp(int oldId) {
		if (intensifyStoneUpResMap.containsKey(oldId)) {
			return intensifyStoneUpResMap.get(oldId);
		}
		return null;
	}

	/**
	 * 根据星获得分解物品
	 * 
	 * @param start
	 * @return
	 */
	public IntensifyResolve getIntensifyResolve(int start) {
		if (intensifyResolveMap.containsKey(start)) {
			return intensifyResolveMap.get(start);
		}
		return null;
	}

	/**
	 * 根据不同的部位获得不同的强化数据信息
	 * 
	 * @param part
	 *            部位
	 * @return 装备强化数据信息
	 */
	public BodyInfo getEquipIntensifyMap(EEquip part, int level) {
		BodyInfo equipIntensify = null;
		switch (part) {
		case weapon:
			equipIntensify = weaponIntensifyMap.get(level);
			break;
		case coat:
			equipIntensify = coatIntensifyMap.get(level);
			break;
		case shoe:
			equipIntensify = shoeIntensifyMap.get(level);
			break;
		case belt:
			equipIntensify = capIntensifyMap.get(level);
			break;
		case ring:
			equipIntensify = ringIntensifyMap.get(level);
			break;
		case necklace:
			equipIntensify = necklaceIntensifyMap.get(level);
			break;
		default:
			break;
		}
		return equipIntensify;
	}

	/**
	 * @function 根据颜色获取宝石
	 * @author LuoSR
	 * @param index
	 *            制作索引
	 * @return 宝石制作对象
	 * @date 2014年8月27日
	 */
	public StoneRecipe getEquipStoneByColor(int index) {
		return equipStoneMap.get(index);
	}

	public EquipStarRes getEquipStarRes(int star) {
		if (equipStar.containsKey(star)) {
			return equipStar.get(star);
		}
		return null;
	}

	public Map<Integer, BodyInfo> getWeaponIntensifyMap() {
		return weaponIntensifyMap;
	}

	public Map<Integer, BodyInfo> getCoatIntensifyMap() {
		return coatIntensifyMap;
	}

	public Map<Integer, BodyInfo> getShoeIntensifyMap() {
		return shoeIntensifyMap;
	}

	public Map<Integer, BodyInfo> getCapIntensifyMap() {
		return capIntensifyMap;
	}

	public Map<Integer, BodyInfo> getRingIntensifyMap() {
		return ringIntensifyMap;
	}

	public Map<Integer, BodyInfo> getNecklaceIntensifyMap() {
		return necklaceIntensifyMap;
	}

	public Map<Integer, StoneRecipe> getEquipStoneMap() {
		return equipStoneMap;
	}

	/**
	 * @function 根据物品编号获取物品合成信息
	 * @author LuoSR
	 * @param id
	 *            灵石编号
	 * @return 灵石升星跟升阶信息
	 * @date 2014年7月14日
	 */
	public ItemMakeRes getItemMakeRes(int id) {
		return itemMakeResMap.get(id);
	}

	/**
	 * @return the stoneUp
	 */
	public Map<Integer, StoneUp> getStoneUp() {
		return stoneUp;
	}

	/**
	 * @param stoneUp
	 *            the stoneUp to set
	 */
	public void setStoneUp(Map<Integer, StoneUp> stoneUp) {
		this.stoneUp = stoneUp;
	}

}
