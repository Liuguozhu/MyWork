package iyunu.NewTLOL.model.bag;

import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.award.AwardServer;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

public class BagNew {

	public static final int MAX_CELLS = 100;
	public static final int DEFAULT_CELLS = 40;

	private HashBiMap<Integer, Integer> map = HashBiMap.create(); // <背包索引，物品编号>
	private HashBasedTable<Integer, Integer, CellNew> cells = HashBasedTable.create(); // <背包索引，物品编号，格子信息>

	private int size; // 格子数量
	private int available; // 空闲格子数量
	private Role role;

	public static Comparator<CellNew> comparator = new Comparator<CellNew>() {
		@Override
		public int compare(CellNew o1, CellNew o2) {
			if (o1.getItem().getPriority() < o2.getItem().getPriority()) {
				return -1;
			} else if (o1.getItem().getPriority() == o2.getItem().getPriority()) {
				return 0;
			} else {
				return 1;
			}
		}
	};

	/**
	 * 背包构造方法
	 * 
	 * @param size
	 *            背包格子数量
	 */
	public BagNew(int size) {
		if (size > MAX_CELLS) {
			size = MAX_CELLS;
		}
		this.size = size;
		this.available = size;
		for (int i = 0; i < size; i++) {
			CellNew cell = new CellNew(i);
			cells.put(i, 0, cell);
		}
	}

	/**
	 * 扩展背包
	 * 
	 * @param expandNum
	 *            扩展量
	 * @return 扩展成功
	 */
	public boolean expand(int expandNum) {
		if (size + expandNum > MAX_CELLS) {
			return false;
		}
		this.size += expandNum; // 扩展背包格子数量
		for (int i = cells.size(); i < size; i++) {
			CellNew cell = new CellNew(i);
			cells.put(i, 0, cell);
		}
		this.available += expandNum; // 扩展空闲格子数量
		return true;
	}

	/**
	 * 添加角色
	 * 
	 * @param role
	 *            角色对象
	 */
	public void addRole(Role role) {
		this.role = role;
	}

	/**
	 * 获取空闲格子数量
	 * 
	 * @return 数量
	 */
	public int freeCell() {
		return available;
	}

	/**
	 * 背包是否已满
	 * 
	 * @return 背包已满
	 */
	public boolean isFull() {
		if (available > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断背包是否有指定数量空格
	 * 
	 * @param num
	 *            需要格子数量
	 * @return 背包空间不足
	 */
	public boolean isFull(int num) {
		if (available < num) {
			return true;
		}
		return false;
	}

	/**
	 * 保存物品信息
	 * 
	 * @param cell
	 *            格子
	 * @param item
	 *            物品
	 * @param num
	 *            数量
	 * @param map
	 *            存储记录
	 */
	public void save(CellNew cell, Item item, int num, Map<Integer, CellNew> map) {
		cell.setItem(item);
		cell.setNum(num);
		map.put(cell.getIndex(), cell); // 存储信息
	}

	public int test(int left, int max, CellNew cell, Item item, Map<Integer, CellNew> map) {
		if (left <= max) {
			this.save(cell, item, left, map); // 保存物品
			return 0;
		} else {
			this.save(cell, item, max, map);
			return left - max;
		}
	}

	public void testHas(Map<Integer, Integer> hasFree, Item item, int num, Map<Integer, CellNew> map, EItemGet eItemGet) {
		int left = num;
		Iterator<Entry<Integer, Integer>> it = hasFree.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Integer> entry = it.next();

			CellNew cell = cells.get(entry.getKey(), item.getId());
			int max = cell.getMax() - cell.getNum();

			left = this.test(left, max, cell, item, map);
			if (left > 0) {
				continue;
			}

			TaskManager.addCollectionTaskItem(role, item, num); // ======收集任务======

			AwardServer.addItem(role, item, num); // 发送通知
			LogManager.itemGet(role, item, num, eItemGet); // 日志记录

			if (this.isNotice(item, eItemGet)) { // 发送公告通知
				String content = "恭喜玩家" + StringControl.grn(role.getNick()) + "通过" + eItemGet.getName() + "获得了" + StringControl.color(item.getColor(), item.getName()) + " x " + num;
				BulletinManager.instance().addBulletinRock(content, 1);
			}
			break;
		}
	}

	public void testNone(Item item, int num, Map<Integer, CellNew> map, EItemGet eItemGet) {

		if (item.getType().equals(EItem.equip)) { // 判断时限
			item.timeOut();
		}

		int left = num;
		Map<Integer, CellNew> emptyMap = cells.column(0);
		for (int i = 0; i < size; i++) {
			if (emptyMap.containsKey(i)) {
				CellNew cell = emptyMap.get(i);

				available--; // 背包空闲格子数减1

				left = this.test(left, item.getMax(), cell, item, map);
				if (left > 0) {
					continue;
				}

				TaskManager.addCollectionTaskItem(role, item, num); // ======收集任务======

				AwardServer.addItem(role, item, num); // 发送通知
				LogManager.itemGet(role, item, num, eItemGet); // 日志记录

				if (this.isNotice(item, eItemGet)) { // 发送公告通知
					String content = "恭喜玩家" + StringControl.grn(role.getNick()) + "通过" + eItemGet.getName() + "获得了" + StringControl.color(item.getColor(), item.getName()) + " x " + num;
					BulletinManager.instance().addBulletinRock(content, 1);
				}
				break;
			}
		}

	}

	/**
	 * 添加物品
	 * 
	 * @param item
	 *            物品对象
	 * @param num
	 *            数量
	 * @param map
	 *            存储记录
	 * @return 添加成功（失败原因是背包已满）
	 */
	public boolean add(Item item, int num, Map<Integer, CellNew> map, EItemGet eItemGet) {

		if (item == null || num <= 0 || map == null) { // 非法参数
			LogManager.exception("角色编号【" + role.getId() + "】角色名称【" + role.getNick() + "】背包添加物品异常item=" + item + ",num=" + num);
			return false;
		}

		if (item.getMax() > 1 && cells.containsColumn(item.getId())) { // 判断是否已有此物品

			int sum = 0;
			Map<Integer, CellNew> hasMap = cells.column(item.getId());
			Map<Integer, Integer> hasFree = new HashMap<>(); // <背包索引，数量>
			for (CellNew cellNew : hasMap.values()) {
				int freeNum = cellNew.getMax() - cellNew.getNum();
				hasFree.put(cellNew.getIndex(), freeNum);
				sum += freeNum;
				if (sum >= num) {
					break;
				}
			}

			if (sum >= num) { // 判断已有物品的空位是否能存下
				this.testHas(hasFree, item, num, hasMap, eItemGet);
				return true;
			} else { // 空位不够存

				int need = num - sum;

				int max = item.getMax(); // 物品最大叠加数量
				int needNum = (need - 1) / max + 1; // 所需要的格子数量

				if (isFull(needNum)) { // 背包空余格子数不足
					if (!item.getType().equals(EItem.taskItem)) { // 背包空间不足，不获得任务物品
						MailServer.send(role.getId(), "系统邮件", "背包已满，通过邮件发放", item, num, 0, 0, 0, 0, null, "系统");
					}
				}

				this.testHas(hasFree, item, sum, hasMap, eItemGet);
				this.testNone(item, need, map, eItemGet);

				return true;
			}

		} else { // ====================背包中不包含新增物品==========================

			int max = item.getMax(); // 物品最大叠加数量
			int needNum = (num - 1) / max + 1; // 所需要的格子数量

			if (isFull(needNum)) { // 背包空余格子数不足
				if (item.getType().equals(EItem.taskItem)) { // 背包空间不足，不获得任务物品
					return false;
				} else {
					MailServer.send(role.getId(), "系统邮件", "背包已满，通过邮件发放", item, num, 0, 0, 0, 0, null, "系统");
					return true;
				}
			}

			this.testNone(item, num, map, eItemGet);
			return true;
		}

	}

	/**
	 * 是否发送通知
	 * 
	 * @param item
	 *            物品对象
	 * @param eItemGet
	 *            获得类型
	 * @return 发送通知
	 */
	public static boolean isNotice(Item item, EItemGet eItemGet) {
		if (item.getIsNotice() == 1 && eItemGet.isNotice()) {
			return true;
		}
		return false;
	}

	/**
	 * 删除指定格子内物品
	 * 
	 * @param index
	 *            背包格子索引
	 * @param num
	 *            数量
	 * @return 删除成功
	 */
	public boolean removeByIndex(int index, int num, Map<Integer, Cell> cellsMap, EItemCost itemCost) {
//		if (index < 0 || index > BagNew.MAX_CELLS) {
//			return false;
//		}
//		Cell cell = cells[index];
//		Item item = cell.getItem();
//		int quantity = cell.getNum();
//		if (quantity < num) {
//			return false;
//		} else if (quantity == num) {
//			cell.clean();
//			available++; // 空闲格子数量增加
//		} else {
//			cell.setNum(quantity - num);
//		}
//
//		// ======收集任务======
//		TaskManager.deleteCollectionTaskItem(role, item, num);
//
//		LogManager.item(role, item, num, itemCost); // 物品使用日志
//		cellsMap.put(index, cell);
//
		return true;
	}

	/**
	 * 清除格子
	 * 
	 * @param index
	 *            背包格子索引
	 */
	public void clean(int index) {
//		cells[index].clean();
//		available++; // 空闲格子数量增加
	}

	/**
	 * 根据物品编号清除格子
	 * 
	 * @param itemId
	 *            物品编号
	 * @param cellsMap
	 */
	public void cleanById(int itemId, Map<Integer, Cell> cellsMap) {
//		for (Cell cell : cells) {
//			if (cell.getItem() != null && cell.getItem().getId() == itemId) {
//				LogManager.item(role, cell.getItem(), cell.getNum(), EItemCost.task); // 物品使用日志
//				cell.clean();
//				available++; // 空闲格子数量增加
//				cellsMap.put(cell.getIndex(), cell);
//			}
//		}
	}

	/**
	 * 删除指定物品
	 * 
	 * @param itemId
	 *            物品编号
	 * @param num
	 *            数量
	 * @return 删除成功
	 */
	public boolean removeById(int itemId, int num, Map<Integer, Cell> cellMap, EItemCost itemCost) {
		boolean success = false;
//		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//		int haveNum = 0;
//		for (Cell cell : cells) {
//			if (cell.getItem() != null && cell.getItem().getId() == itemId) {
//				int needNum = cell.getNum();
//				if (haveNum + needNum == num) {
//					map.put(cell.getIndex(), needNum);
//					success = true;
//					break;
//				} else if (haveNum + needNum > num) {
//					map.put(cell.getIndex(), num - haveNum);
//					success = true;
//					break;
//				} else {
//					map.put(cell.getIndex(), needNum);
//				}
//				haveNum += needNum;
//			}
//		}
//
//		if (success) {
//			Set<Entry<Integer, Integer>> set = map.entrySet();
//			for (Iterator<Entry<Integer, Integer>> it = set.iterator(); it.hasNext();) {
//				Entry<Integer, Integer> entry = it.next();
//				removeByIndex(entry.getKey(), entry.getValue(), cellMap, itemCost);
//			}
//		}
//
		return success;
	}

	public String allItemString() {
		String str = "" + size;
//		for (Cell cell : cells) {
//			if (cell.getItem() != null) {
//				str += "【" + cell.getIndex() + "-" + cell.getNum() + "-" + cell.getItem().getName() + "】";
//			} else {
//				str += "【" + cell.getIndex() + "-0-null" + "】";
//			}
//		}
		return str;
	}

	public String toString() {
		String str = "" + size;
//		for (Cell cell : cells) {
//			String cellStr = cell.encode();
//			str += "&" + cellStr;
//		}
//
		return str;
	}

	/**
	 * 背包解码
	 * 
	 * @param str
	 *            背包字符串
	 * @return 背包对象
	 */
	public static BagNew oldDecode(String str) {
		BagNew bag = null;
//		try {
//			String[] strs = str.split("&");
//			int size = Translate.stringToInt(strs[0]);
//			size = size >= BagNew.DEFAULT_CELLS ? size : BagNew.DEFAULT_CELLS;
//			bag = new BagNew(size);
//
//			for (int i = 1; i < strs.length; i++) {
//				Cell cell = Cell.oldDecode(strs[i]);
//				if (cell != null) {
//					bag.getCells()[i - 1] = cell;
//					bag.setAvailable(bag.getAvailable() - 1);
//
//					// ======判断背包中是否有宝石（临时使用）======
//					if (!bag.isHasStone() && cell.getItem().getType().equals(EItem.stone)) {
//						bag.setHasStone(true);
//					}
//					// ======判断背包中是否有宝石（临时使用）======
//				}
//			}
//		} catch (Exception e) {
//			LogManager.error("背包解码错误");
//			bag = new BagNew(BagNew.DEFAULT_CELLS); // 背包解码错误，则返回一个空背包
//			bag.setWarn(true);
//		}
		return bag;
	}

	/**
	 * 背包解码
	 * 
	 * @param str
	 *            背包字符串
	 * @return 背包对象
	 */
	public static BagNew decode(String str) {
		BagNew bag = null;
//
//		if (str == null || "".equals(str)) {
//			return new BagNew(BagNew.DEFAULT_CELLS); // 背包解码错误，则返回一个空背包
//		}
//
//		try {
//			String[] strs = str.split("&");
//			int size = Translate.stringToInt(strs[0]);
//			size = size >= BagNew.DEFAULT_CELLS ? size : BagNew.DEFAULT_CELLS;
//			bag = new BagNew(size);
//
//			for (int i = 1; i < strs.length; i++) {
//				Cell cell = Cell.decode(strs[i]);
//				bag.getCells()[i - 1] = cell;
//				if (cell.getItem() != null) {
//
//					bag.setAvailable(bag.getAvailable() - 1);
//
//					// ======判断背包中是否有宝石（临时使用）======
//					if (!bag.isHasStone() && cell.getItem().getType().equals(EItem.stone)) {
//						bag.setHasStone(true);
//					}
//					// ======判断背包中是否有宝石（临时使用）======
//				}
//			}
//		} catch (Exception e) {
//			LogManager.error("背包解码错误");
//			bag = new BagNew(BagNew.DEFAULT_CELLS); // 背包解码错误，则返回一个空背包
//			bag.setWarn(true);
//		}
		return bag;

	}

	/**
	 * @function 整理背包
	 * @author LuoSR
	 * @date 2013年12月25日
	 */
	public void sortBag() {

//		List<Cell> list = new ArrayList<Cell>();
//		for (int i = 0; i < cells.length; i++) {
//			for (int j = i + 1; j < cells.length; j++) {
//				if (cells[i].getItem() != null && cells[j].getItem() != null && cells[i].getItem().getId() == cells[j].getItem().getId() && cells[i].getItem().getIsDeal() == cells[j].getItem().getIsDeal()) {
//
//					int cellLeft = cells[i].getMax() - cells[i].getNum(); // i格子可存放物品的剩余数量
//					if (cellLeft > 0) { // i格子可存放物品的剩余数量>0，即还可以存放物品
//						if (cells[j].getNum() <= cellLeft) { // j格子的物品数量≤i格子可存放物品的剩余数量，即物品全部添加完成
//							cells[i].setNum(cells[i].getNum() + cells[j].getNum());
//							this.clean(cells[j].getIndex());
//							continue;
//						} else {
//							cells[i].setNum(cells[i].getMax());
//							cells[j].setNum(cells[j].getNum() - cellLeft);
//						}
//					}
//				}
//			}
//			if (cells[i].getItem() != null) {
//				list.add(cells[i]);
//			}
//		}
//
//		// 排序
//		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
//		Collections.sort(list, comparator);
//
//		// 重新放入排序好的物品
//		Cell[] newCells = new Cell[cells.length];
//		int size = list.size();
//		for (int i = 0; i < newCells.length; i++) {
//
//			if (i < size) {
//				newCells[i] = list.get(i);
//				newCells[i].setIndex(i);
//			} else {
//				Cell cell = new Cell();
//				cell.setIndex(i);
//				newCells[i] = cell;
//			}
//
//		}
//		cells = newCells;

	}

	/**
	 * 根据物品类型获取物品所在背包索引
	 * 
	 * @param type
	 *            物品类型
	 * @param isBind
	 *            是否绑定（0.不绑定，1.绑定）
	 * @return 物品所在背包索引
	 */
	public int isInBagByType(EItem type, int isBind) {
//		if (isBind == 0) { // 不绑定
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getType().equals(type) && cells[i].getItem().getIsDeal() == 0) {
//					return i;
//				}
//			}
//		} else { // 绑定
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getType().equals(type) && cells[i].getItem().getIsDeal() == 1) {
//					return i;
//				}
//			}
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getType().equals(type) && cells[i].getItem().getIsDeal() == 0) {
//					return i;
//				}
//			}
//		}
		return -1;
	}

	public int isInBagIndexById(int itemId, int isBind) {
		int result = -1;
//		if (isBind == 0) { // 不绑定
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 0) {
//					result = i;
//					break;
//				}
//			}
//		} else { // 绑定
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 1) {
//					result = i;
//					break;
//				}
//			}
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 0) {
//					result = i;
//					break;
//				}
//			}
//		}
		return result;
	}

	/**
	 * 根据物品编号获取物品所在背包索引及数量
	 * 
	 * @param itemId
	 *            物品编号
	 * @param isBind
	 *            是否绑定（0.不绑定，1.绑定）
	 * @return 物品所在背包索引及数量
	 */
	public Map<Integer, Integer> isInBagMapById(int itemId, int isBind) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//		if (isBind == 0) { // 不绑定
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 0) {
//					map.put(i, cells[i].getNum());
//				}
//			}
//		} else { // 绑定
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 1) {
//					map.put(i, cells[i].getNum());
//				}
//			}
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 0) {
//					map.put(i, cells[i].getNum());
//				}
//			}
//		}
		return map;
	}

	/**
	 * 根据物品编号获取物品所在背包索引及数量
	 * 
	 * @param itemId
	 *            物品编号
	 * @param isBind
	 *            是否绑定（0.不绑定，1.绑定）
	 * @param num
	 *            消耗数量
	 * @return 物品所在背包索引及数量
	 */
	public Map<Integer, Integer> isInBagById(int itemId, int isBind, int num) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//		int sum = 0;
//
//		if (isBind == 0) { // 不绑定
//
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 0) {
//					sum += cells[i].getNum();
//					map.put(i, cells[i].getNum());
//					if (sum >= num) {
//						map.put(i, cells[i].getNum() - sum + num);
//						return map;
//					}
//				}
//			}
//			map.clear();
//		} else { // 绑定
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 1) {
//					sum += cells[i].getNum();
//					map.put(i, cells[i].getNum());
//					if (sum >= num) {
//						map.put(i, cells[i].getNum() - sum + num);
//						return map;
//					}
//				}
//			}
//
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 0) {
//					sum += cells[i].getNum();
//					map.put(i, cells[i].getNum());
//					if (sum >= num) {
//						map.put(i, cells[i].getNum() - sum + num);
//						return map;
//					}
//				}
//			}
//			map.clear();
//		}
//
		return map;
	}

	/**
	 * 判断物品一共有多少个
	 * 
	 * @param itemId
	 * @return
	 */
	public int getTheItemNum(int itemId) {
		int num = 0;
//		for (int i = 0; i < cells.length; i++) {
//			if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId) {
//				num = cells[i].getNum() + num;
//			}
//		}
		return num;
	}

	/**
	 * 判断物品一共有多少个非绑定
	 * 
	 * @param itemId
	 * @return
	 */
	public int getTheItemNumNoBind(int itemId) {
		int num = 0;
//		for (int i = 0; i < cells.length; i++) {
//			if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 0) {
//				num = cells[i].getNum() + num;
//			}
//		}
		return num;
	}

	/**
	 * 根据物品类型获取物品所在背包索引及数量
	 * 
	 * @param type
	 *            物品类型
	 * @param isBind
	 *            是否绑定（0.不绑定，1.绑定）
	 * @param num
	 *            消耗数量
	 * @return 物品所在背包索引及数量
	 */
	public Map<Integer, Integer> isInBagByType(EItem type, int isBind, int num) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//		int sum = 0;
//
//		if (isBind == 0) { // 不绑定
//
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getType().equals(type) && cells[i].getItem().getIsDeal() == 0) {
//					sum += cells[i].getNum();
//					map.put(i, cells[i].getNum());
//					if (sum >= num) {
//						map.put(i, cells[i].getNum() - sum + num);
//						return map;
//					}
//				}
//			}
//			map.clear();
//		} else { // 绑定
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getType().equals(type) && cells[i].getItem().getIsDeal() == 1) {
//					sum += cells[i].getNum();
//					map.put(i, cells[i].getNum());
//					if (sum >= num) {
//						map.put(i, cells[i].getNum() - sum + num);
//						return map;
//					}
//				}
//			}
//
//			for (int i = 0; i < cells.length; i++) {
//				if (cells[i].getItem() != null && cells[i].getItem().getType().equals(type) && cells[i].getItem().getIsDeal() == 0) {
//					sum += cells[i].getNum();
//					map.put(i, cells[i].getNum());
//					if (sum >= num) {
//						map.put(i, cells[i].getNum() - sum + num);
//						return map;
//					}
//				}
//			}
//			map.clear();
//		}
//
		return map;
	}

	/**
	 * @function 通过物品类别查询背包中是否存在
	 * @author LuoSR
	 * @param type
	 *            物品类型
	 * @return 格子索引集合（若不存在返回-1）
	 * @date 2014年3月12日
	 */
	public int isInBagByType(EItem type) {
//		for (int i = 0; i < cells.length; i++) {
//			if (cells[i].getItem() != null && cells[i].getItem().getType().equals(type)) {
//				return i;
//			}
//		}
		return -1;
	}

	/**
	 * @function 通过物品编号查询背包中是否存在
	 * @author LuoSR
	 * @param itemId
	 *            物品ID
	 * @return 格子索引
	 * @date 2014年8月27日
	 */
	public int isInBagById(int itemId) {
//		for (int i = 0; i < cells.length; i++) {
//			if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId) {
//				return i;
//			}
//		}
		return -1;
	}

	/**
	 * @function 通过物品编号查询物品在背包中的索引集合
	 * @author LuoSR
	 * @param itemId
	 *            物品ID
	 * @return 格子索引集合（若不存在返回null）
	 * @date 2014年8月27日
	 */
	public List<Integer> getIndexListById(int itemId) {
		List<Integer> list = new ArrayList<Integer>();
//		for (int i = 0; i < cells.length; i++) {
//			if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId) {
//				list.add(i);
//			}
//		}
		return list;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the cells
	 */
//	public Cell[] getCells() {
//		return cells;
//	}

	/**
	 * @param cells
	 *            the cells to set
	 */
//	public void setCells(Cell[] cells) {
//		this.cells = cells;
//	}

	/**
	 * @return the warn
	 */
//	public boolean isWarn() {
//		return warn;
//	}

	/**
	 * @param warn
	 *            the warn to set
	 */
//	public void setWarn(boolean warn) {
//		this.warn = warn;
//	}

	/**
	 * @return the available
	 */
	public int getAvailable() {
		return available;
	}

	/**
	 * @param available
	 *            the available to set
	 */
	public void setAvailable(int available) {
		this.available = available;
	}

	/**
	 * @return the hasStone
	 */
//	public boolean isHasStone() {
//		return hasStone;
//	}

	/**
	 * @param hasStone
	 *            the hasStone to set
	 */
//	public void setHasStone(boolean hasStone) {
//		this.hasStone = hasStone;
//	}

}
