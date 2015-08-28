package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.json.MiningJson;
import iyunu.NewTLOL.model.mining.Mining;
import iyunu.NewTLOL.model.mining.MiningPage;
import iyunu.NewTLOL.model.mining.MiningRole;
import iyunu.NewTLOL.model.mining.res.MiningRes;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.redis.RedisMining;
import iyunu.NewTLOL.service.iface.role.RoleServiceIfce;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Translate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class MiningManger {

	private static MiningManger instance = new MiningManger();

	private TreeMap<Integer, Mining> miningMap = new TreeMap<>();
	public static long MINING_MAX_TIME = 4 * 60 * 60 * 1000;// 最长占领时间 ，4小时
	public static long MINING_EVERY_TIME = 5 * 60 * 1000;// 结算间隔时间 ，5分钟
	public static int MINING_PAGE_MAX = 15;// 每页显示多少个矿
	private ConcurrentHashMap<Long, List<String>> tipMap = new ConcurrentHashMap<>();

	private MiningManger() {

	}

	public static MiningManger instance() {
		return instance;
	}

	/**
	 * 开服时加载
	 */
	public void load() {
		miningInit();
		RedisMining redisMining = Spring.instance().getBean("redisMining", RedisMining.class);
		TreeMap<Integer, MiningRole> miningRoleMap = redisMining.getMiningMap();
		if (miningRoleMap != null) {
			Iterator<Entry<Integer, MiningRole>> it = miningRoleMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Integer, MiningRole> entry = it.next();
				if (entry.getValue().getFlag() == 1) {
					Mining mining = new Mining();
					MiningRole miningR = entry.getValue();
					mining.setFlag(miningR.getFlag());
					mining.setIndex(miningR.getIndex());
					RoleServiceIfce roleService = Spring.instance().getBean("roleService", RoleServiceIfce.class);
					Role role = roleService.queryRole(miningR.getRoleId());
					mining.setRole(role);
					mining.setStartTime(miningR.getStartTime());
					Map<Integer, MiningRes> resMap = MiningJson.instance().getMining();
					mining.setType(resMap.get(entry.getKey()).getType());
					mining.setValue(resMap.get(entry.getKey()).getValue());
					miningMap.put(entry.getKey(), mining);
				}
			}
		}

		// 加载矿区被抢提示
		tipMap = redisMining.getMiningRobMap();
		if (tipMap == null) {
			tipMap = new ConcurrentHashMap<>();
		}
	}

	/**
	 * 初始化
	 */
	public void miningInit() {
		miningMap.clear();
		Map<Integer, MiningRes> resMap = MiningJson.instance().getMining();
		Iterator<Map.Entry<Integer, MiningRes>> it = resMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, MiningRes> entry = it.next();
			Mining mining = new Mining();
			mining.setIndex(entry.getKey());
			mining.setValue(entry.getValue().getValue());
			mining.setType(entry.getValue().getType());
			miningMap.put(entry.getKey(), mining);
		}
	}

	/**
	 * 根据页数返回矿的信息
	 * 
	 * @param page
	 *            页数
	 */
	public MiningPage getPageMining(int page) {
		MiningPage miningPage = new MiningPage();
		try {
			if (miningMap.size() > 0) {
				int start = (page - 1) * MINING_PAGE_MAX + 1;
				int end = start + MINING_PAGE_MAX > miningMap.size() ? miningMap.size() + 1 : start + MINING_PAGE_MAX;
				miningPage.setTotalPage((miningMap.size() - 1) / MINING_PAGE_MAX + 1);
				miningPage.setTotalPage(miningPage.getTotalPage() == 0 ? 1 : miningPage.getTotalPage());// 总页数
				for (int i = start; i < end; i++) {
					miningPage.getMiningList().add(miningMap.get(i));
				}
				miningPage.setPage(page);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return miningPage;

	}

	/**
	 * 判断页数是否不对
	 * 
	 * @param page
	 * @return
	 */
	public boolean ifPage(int page) {
		if (miningMap.size() > 0) {
			if ((miningMap.size() - 1) / MINING_PAGE_MAX + 1 >= page && page > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断 页数和 选择的矿区是否正确
	 * 
	 * @param page
	 * @param index
	 * @return
	 */
	public boolean ifPageIndex(int page, int index) {
		if (miningMap.size() > 0) {
			if ((miningMap.size() - 1) / MINING_PAGE_MAX + 1 >= page || page > 0) {
				int start = (page - 1) * MINING_PAGE_MAX + 1;
				int end = start + MINING_PAGE_MAX > miningMap.size() ? miningMap.size() + 1 : start + MINING_PAGE_MAX;
				if (index <= end && index >= start) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 是不是已经有一个矿
	 * 
	 * @param roleId
	 * @return true 是有一个矿了， false 没有矿
	 */
	public boolean ifHadAMining(long roleId) {
		Iterator<Mining> it = miningMap.values().iterator();
		while (it.hasNext()) {
			Mining mining = it.next();
			if (mining.getRole() != null) {
				if (mining.getRole().getId() == roleId) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 矿区收益计算
	 * 
	 * @param startTime
	 *            开始时间
	 * @param value
	 *            每5分钟的收益
	 * @return
	 */
	public int countRec(long startTime, int value) {
		if (startTime == 0) {
			return 0;
		}
		long between = System.currentTimeMillis() - startTime;
		if (between < 0) {
			between = 0;
		}
		if (between / Time.HOUR_MILLISECOND > 4) {
			between = Time.HOUR_MILLISECOND * 4;
		}
		int rec = Translate.longToInt(between / MINING_EVERY_TIME * value);
		return rec;
	}

	/**
	 * 矿区收益进度计算
	 * 
	 * @param startTime
	 *            开始时间
	 * @return
	 */
	public int countRate(long startTime) {
		if (startTime == 0) {
			return 0;
		}
		long between = System.currentTimeMillis() - startTime;
		if (between < 0) {
			between = 0;
		}
		if (between / Time.HOUR_MILLISECOND > 4) {
			between = Time.HOUR_MILLISECOND * 4;
		}
		int rec = Translate.doubeToInt(between * 1.0 / MINING_MAX_TIME * 10000);
		return rec;
	}

	/**
	 * 剩余时间
	 * 
	 * @param startTime
	 * @return 秒数
	 */
	public long getRestTime(long startTime) {
		long between = System.currentTimeMillis() - startTime;
		if (between < 0) {
			between = 0;
			return 0;
		}
		between = Time.HOUR_MILLISECOND * 4 - between;
		if (between / Time.HOUR_MILLISECOND > 4) {
			return 0;
		}
		return between / 1000;
	}

	/**
	 * @return the miningMap
	 */
	public TreeMap<Integer, Mining> getMiningMap() {
		return miningMap;
	}

	/**
	 * @param miningMap
	 *            the miningMap to set
	 */
	public void setMiningMap(TreeMap<Integer, Mining> miningMap) {
		this.miningMap = miningMap;
	}

	/**
	 * @return the tipMap
	 */
	public ConcurrentHashMap<Long, List<String>> getTipMap() {
		return tipMap;
	}

	/**
	 * @param tipMap
	 *            the tipMap to set
	 */
	public void setTipMap(ConcurrentHashMap<Long, List<String>> tipMap) {
		this.tipMap = tipMap;
	}

}
