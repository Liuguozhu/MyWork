package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.res.BookRes;
import iyunu.NewTLOL.model.item.res.DrawingRes;
import iyunu.NewTLOL.model.item.res.DrugRes;
import iyunu.NewTLOL.model.item.res.EquipRes;
import iyunu.NewTLOL.model.item.res.ItemCostRes;
import iyunu.NewTLOL.model.item.res.ItemGiftRes;
import iyunu.NewTLOL.model.item.res.ItemUseRes;
import iyunu.NewTLOL.model.item.res.StoneRes;
import iyunu.NewTLOL.model.item.res.TaskItemRes;
import iyunu.NewTLOL.model.item.res.VipItemRes;
import iyunu.NewTLOL.service.impl.item.ItemServer;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public final class ItemJson {

	/**
	 * 私有构造方法
	 */
	private ItemJson() {

	}

	private static ItemJson instance = new ItemJson();
	private static final String EQUIPMENT = "json/" + ServerManager.SERVER_RES + "/ItemEquip.json.txt";
	private static final String SONTE = "json/" + ServerManager.SERVER_RES + "/ItemStone.json.txt";
	private static final String BOOK = "json/" + ServerManager.SERVER_RES + "/ItemBook.json.txt";
	private static final String DRUG = "json/" + ServerManager.SERVER_RES + "/ItemDrug.json.txt";
	private static final String GIFT = "json/" + ServerManager.SERVER_RES + "/ItemGift.json.txt";
	private static final String TASKITEM = "json/" + ServerManager.SERVER_RES + "/ItemTask.json.txt";
	private static final String USE = "json/" + ServerManager.SERVER_RES + "/ItemUse.json.txt";
	private static final String COST = "json/" + ServerManager.SERVER_RES + "/ItemCost.json.txt";
	private static final String VIP = "json/" + ServerManager.SERVER_RES + "/ItemVip.json.txt";
	private static final String DRAWING = "json/" + ServerManager.SERVER_RES + "/ItemDrawing.json.txt";
	private static final String SHENBING = "json/" + ServerManager.SERVER_RES + "/ItemShenbing.json.txt";
	private static final String SHI_ZHUANG = "json/" + ServerManager.SERVER_RES + "/ItemShizhuangRes.json.txt";

	Map<Integer, Item> items = new HashMap<Integer, Item>();

	/**
	 * 获取EquipJson对象
	 * 
	 * @return EquipJson对象
	 */
	public static ItemJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		items.clear();
	}

	/**
	 * 初始化物品模板
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {
		long start = System.currentTimeMillis();
		clear();
		List<EquipRes> equipResList = JsonImporter.fileImporter(EQUIPMENT, new TypeReference<List<EquipRes>>() {
		});
		for (EquipRes equipRes : equipResList) {
			if (equipRes.getId() != 0) {
				items.put(equipRes.getId(), equipRes.toItem());
			}
		}

		List<StoneRes> stoneResList = JsonImporter.fileImporter(SONTE, new TypeReference<List<StoneRes>>() {
		});
		for (StoneRes stoneRes : stoneResList) {
			if (stoneRes.getId() != 0) {
				items.put(stoneRes.getId(), stoneRes.toItem());
			}
		}

		List<BookRes> bookResList = JsonImporter.fileImporter(BOOK, new TypeReference<List<BookRes>>() {
		});
		for (BookRes bookRes : bookResList) {
			if (bookRes.getId() != 0) {
				items.put(bookRes.getId(), bookRes.toItem());
			}
		}

		List<DrugRes> drugResList = JsonImporter.fileImporter(DRUG, new TypeReference<List<DrugRes>>() {
		});
		for (DrugRes drugRes : drugResList) {
			if (drugRes.getId() != 0) {
				items.put(drugRes.getId(), drugRes.toItem());
			}
		}

		List<TaskItemRes> taskItemList = JsonImporter.fileImporter(TASKITEM, new TypeReference<List<TaskItemRes>>() {
		});
		for (TaskItemRes taskItemRes : taskItemList) {
			if (taskItemRes.getId() != 0) {
				items.put(taskItemRes.getId(), taskItemRes.toItem());
			}
		}

		List<ItemUseRes> itemUseList = JsonImporter.fileImporter(USE, new TypeReference<List<ItemUseRes>>() {
		});
		for (ItemUseRes itemUseRes : itemUseList) {
			if (itemUseRes.getId() != 0) {
				items.put(itemUseRes.getId(), itemUseRes.toItem());
			}
		}

		List<ItemCostRes> itemCostList = JsonImporter.fileImporter(COST, new TypeReference<List<ItemCostRes>>() {
		});
		for (ItemCostRes itemCostRes : itemCostList) {
			if (itemCostRes.getId() != 0) {
				items.put(itemCostRes.getId(), itemCostRes.toItem());
			}
		}

		List<VipItemRes> vipItemList = JsonImporter.fileImporter(VIP, new TypeReference<List<VipItemRes>>() {
		});
		for (VipItemRes vipItemRes : vipItemList) {
			if (vipItemRes.getId() != 0) {
				items.put(vipItemRes.getId(), vipItemRes.toItem());
			}
		}

		List<DrawingRes> drawingResList = JsonImporter.fileImporter(DRAWING, new TypeReference<List<DrawingRes>>() {
		});
		for (DrawingRes drawingRes : drawingResList) {
			if (drawingRes.getId() != 0) {
				items.put(drawingRes.getId(), drawingRes.toItem());
			}
		}

		List<EquipRes> shenbingResList = JsonImporter.fileImporter(SHENBING, new TypeReference<List<EquipRes>>() {
		});
		for (EquipRes equipRes : shenbingResList) {
			if (equipRes.getId() != 0) {
				items.put(equipRes.getId(), equipRes.toItem());
			}
		}

		List<EquipRes> shizhuangList = JsonImporter.fileImporter(SHI_ZHUANG, new TypeReference<List<EquipRes>>() {
		});

		for (EquipRes equipRes : shizhuangList) {
			if (equipRes.getId() != 0) {
				items.put(equipRes.getId(), equipRes.toItem());
			}
		}

		// ======礼包（要放在最后）======
		List<ItemGiftRes> giftList = JsonImporter.fileImporter(GIFT, new TypeReference<List<ItemGiftRes>>() {
		});
		for (ItemGiftRes giftRes : giftList) {
			if (giftRes.getId() != 0) {
				items.put(giftRes.getId(), giftRes.toItem());
			}
		}

		long end = System.currentTimeMillis();
		System.out.println("物品脚本加载耗时：" + (end - start));
	}

	/**
	 * @param id
	 *            物品编号
	 * @return 物品对象
	 */
	public Item getItem(int id) {
		if (items.containsKey(id)) {
			return ItemServer.setUid(items.get(id).copy());
		}
		return null;
	}

	public Item getItem(int id, String uid) {
		if (items.containsKey(id)) {
			Item item = items.get(id).copy();
			item.setUid(uid);
			return item;
		}
		return null;
	}

}
