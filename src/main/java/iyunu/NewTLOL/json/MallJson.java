package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.lessItem.LessItemRes;
import iyunu.NewTLOL.model.mall.EMall;
import iyunu.NewTLOL.model.mall.instance.Mall;
import iyunu.NewTLOL.model.mall.res.MallRes;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public final class MallJson {

	/**
	 * 私有构造方法
	 */
	private MallJson() {

	}

	private static MallJson instance = new MallJson();

	private static final String MALL_SHOP = "json/" + ServerManager.SERVER_RES + "/MallShop.json.txt";
	private static final String MALL_GANG = "json/" + ServerManager.SERVER_RES + "/MallGang.json.txt";
	private static final String MALL_DRUG = "json/" + ServerManager.SERVER_RES + "/MallDrug.json.txt";
	private static final String MALL_CAILIAO = "json/" + ServerManager.SERVER_RES + "/MallCailiao.json.txt";
	private static final String MALL_SHIZHUANG = "json/" + ServerManager.SERVER_RES + "/MallShizhuang.json.txt";
	private static final String MALL_STONE = "json/" + ServerManager.SERVER_RES + "/MallStone.json.txt";
	private static final String MALL_OTHER = "json/" + ServerManager.SERVER_RES + "/MallOther.json.txt";
	private static final String MALL_COIN = "json/" + ServerManager.SERVER_RES + "/MallCoin.json.txt";
	private static final String MALL_VIP = "json/" + ServerManager.SERVER_RES + "/MallVip.json.txt";
	private static final String MALL_LESSITEM = "json/" + ServerManager.SERVER_RES + "/LessItem.json.txt";
	private Map<Integer, Mall> malls = new HashMap<Integer, Mall>();
	private Map<Integer, LessItemRes> lessItem = new HashMap<Integer, LessItemRes>();

	/**
	 * 获取MallJson对象
	 * 
	 * @return MallJson对象
	 */
	public static MallJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		malls.clear();
		lessItem.clear();
		for (EMall eMall : EMall.values()) {
			eMall.getMalls().clear();
		}
	}

	/**
	 * 初始化
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<MallRes> mallShopList = JsonImporter.fileImporter(MALL_SHOP, new TypeReference<List<MallRes>>() {
		});
		for (MallRes mallRes : mallShopList) {
			if (mallRes.getIndex() != 0) {
				Mall mall = mallRes.toMall();
				mall.setType(EMall.shop);
				malls.put(mallRes.getIndex(), mall);
				EMall.shop.getMalls().add(mall);
			}
		}

		List<MallRes> mallGangList = JsonImporter.fileImporter(MALL_GANG, new TypeReference<List<MallRes>>() {
		});
		for (MallRes mallRes : mallGangList) {
			if (mallRes.getIndex() != 0) {
				Mall mall = mallRes.toMall();
				mall.setType(EMall.gang);
				malls.put(mallRes.getIndex(), mall);
				EMall.gang.getMalls().add(mall);
			}
		}

		// ======= 药品 =======
		List<MallRes> mallDrugList = JsonImporter.fileImporter(MALL_DRUG, new TypeReference<List<MallRes>>() {
		});
		for (MallRes mallRes : mallDrugList) {
			if (mallRes.getIndex() != 0) {
				Mall mall = mallRes.toMall();
				mall.setType(EMall.drug);
				malls.put(mallRes.getIndex(), mall);
				EMall.drug.getMalls().add(mall);
			}
		}

		// ======= 材料 =======
		List<MallRes> mallCailiaoList = JsonImporter.fileImporter(MALL_CAILIAO, new TypeReference<List<MallRes>>() {
		});
		for (MallRes mallRes : mallCailiaoList) {
			if (mallRes.getIndex() != 0) {
				Mall mall = mallRes.toMall();
				mall.setType(EMall.cailiao);
				malls.put(mallRes.getIndex(), mall);
				EMall.cailiao.getMalls().add(mall);
			}
		}

		// ======= 时装 =======
		List<MallRes> mallShizhuangList = JsonImporter.fileImporter(MALL_SHIZHUANG, new TypeReference<List<MallRes>>() {
		});
		for (MallRes mallRes : mallShizhuangList) {
			if (mallRes.getIndex() != 0) {
				Mall mall = mallRes.toMall();
				mall.setType(EMall.shizhuang);
				malls.put(mallRes.getIndex(), mall);
				EMall.shizhuang.getMalls().add(mall);
			}
		}

		// ======= 伙伴 =======
		List<MallRes> mallStoneList = JsonImporter.fileImporter(MALL_STONE, new TypeReference<List<MallRes>>() {
		});
		for (MallRes mallRes : mallStoneList) {
			if (mallRes.getIndex() != 0) {
				Mall mall = mallRes.toMall();
				mall.setType(EMall.stone);
				malls.put(mallRes.getIndex(), mall);
				EMall.stone.getMalls().add(mall);
			}
		}

		// ======= 其他 =======
		List<MallRes> mallOtherList = JsonImporter.fileImporter(MALL_OTHER, new TypeReference<List<MallRes>>() {
		});
		for (MallRes mallRes : mallOtherList) {
			if (mallRes.getIndex() != 0) {
				Mall mall = mallRes.toMall();
				mall.setType(EMall.other);
				malls.put(mallRes.getIndex(), mall);
				EMall.other.getMalls().add(mall);
			}
		}

		// ======= 银两 =======
		List<MallRes> mallCoinList = JsonImporter.fileImporter(MALL_COIN, new TypeReference<List<MallRes>>() {
		});
		for (MallRes mallRes : mallCoinList) {
			if (mallRes.getIndex() != 0) {
				Mall mall = mallRes.toMall();
				mall.setType(EMall.coin);
				malls.put(mallRes.getIndex(), mall);
				EMall.coin.getMalls().add(mall);
			}
		}

		// ======= vip专属 =======
		List<MallRes> mallVipList = JsonImporter.fileImporter(MALL_VIP, new TypeReference<List<MallRes>>() {
		});
		for (MallRes mallRes : mallVipList) {
			if (mallRes.getIndex() != 0) {
				Mall mall = mallRes.toMall();
				mall.setType(EMall.vip);
				malls.put(mallRes.getIndex(), mall);
				EMall.vip.getMalls().add(mall);
			}
		}
		List<LessItemRes> lessList = JsonImporter.fileImporter(MALL_LESSITEM, new TypeReference<List<LessItemRes>>() {
		});
		for (LessItemRes lessItemRes : lessList) {
			lessItem.put(lessItemRes.getId(), lessItemRes);
		}
		long end = System.currentTimeMillis();
		System.out.println("商城脚本加载耗时：" + (end - start));
	}

	/**
	 * 根据索引获取商城物品
	 * 
	 * @param index
	 *            索引
	 * @return 商城物品
	 */
	public Mall getMallByIndex(int index) {
		return malls.get(index);
	}

	/**
	 * @return the lessItem
	 */
	public Map<Integer, LessItemRes> getLessItem() {
		return lessItem;
	}

	/**
	 * @param lessItem
	 *            the lessItem to set
	 */
	public void setLessItem(Map<Integer, LessItemRes> lessItem) {
		this.lessItem = lessItem;
	}

	/**
	 * 缺少物品计算
	 * 
	 * @param bind
	 *            1 绑定不绑定都行 , 2 不绑定的 ,3 绑定的
	 * */

	public void countLessItem(Role role, int itemId2, int number2, int bind) {
		LessItemRes less = MallJson.instance().getLessItem().get(itemId2);
		int itemId = less.getIdTo();
		// 缺少多少个
		int num = 0;
		if (bind == 1) {
			num = number2 - role.getBag().getTheItemNum(itemId2);
		}
		if (bind == 0) {
			num = number2 - role.getBag().getTheItemNumNoBind(itemId2);
		}
		// 缺少多少个指向
		num = num * less.getTimes();
		SendMessage.refreshNoitem(role, itemId, num, less.getPrice());
	}

}
