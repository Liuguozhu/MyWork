package iyunu.NewTLOL.model.bag;

import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.award.AwardServer;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class BagStone {

	public static int DEFAULT_CELLS = 100;
	private Role role;
	private Cell[] cells; // 格子
	private int available; // 空闲格子数量

	/**
	 * 构造方法
	 * 
	 * @param size
	 */
	public BagStone(int size) {
		cells = new Cell[100];
		for (int i = 0; i < 100; i++) {
			Cell cell = new Cell();
			cell.setIndex(i);
			cells[i] = cell;
		}
		this.available = BagStone.DEFAULT_CELLS;
	}

	/**
	 * 构造防范
	 */
	public BagStone() {

	}

	/**
	 * @function 整理背包
	 * @author LuoSR
	 * @date 2013年12月25日
	 */
	public void sortBag() {

		List<Cell> list = new ArrayList<Cell>();
		for (int i = 0; i < cells.length; i++) {
			for (int j = i + 1; j < cells.length; j++) {
				if (cells[i].getItem() != null && cells[j].getItem() != null && cells[i].getItem().getId() == cells[j].getItem().getId() && cells[i].getItem().getIsDeal() == cells[j].getItem().getIsDeal()) {

					int cellLeft = cells[i].getMax() - cells[i].getNum(); // i格子可存放物品的剩余数量
					if (cellLeft > 0) { // i格子可存放物品的剩余数量>0，即还可以存放物品
						if (cells[j].getNum() <= cellLeft) { // j格子的物品数量≤i格子可存放物品的剩余数量，即物品全部添加完成
							cells[i].setNum(cells[i].getNum() + cells[j].getNum());
							this.clean(cells[j].getIndex());
							continue;
						} else {
							cells[i].setNum(cells[i].getMax());
							cells[j].setNum(cells[j].getNum() - cellLeft);
						}
					}
				}
			}
			if (cells[i].getItem() != null) {
				list.add(cells[i]);
			}
		}

		// 排序
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Collections.sort(list, Bag.comparator);

		// 重新放入排序好的物品
		Cell[] newCells = new Cell[cells.length];
		int size = list.size();
		for (int i = 0; i < newCells.length; i++) {

			if (i < size) {
				newCells[i] = list.get(i);
				newCells[i].setIndex(i);
			} else {
				Cell cell = new Cell();
				cell.setIndex(i);
				newCells[i] = cell;
			}

		}
		cells = newCells;

	}

	/**
	 * 清除格子
	 * 
	 * @param index
	 *            背包格子索引
	 */
	public void clean(int index) {
		cells[index].clean();
		available++; // 空闲格子数量增加
	}

	public boolean isFull() {
		return isFull(1);
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
	public boolean add(Item item, int num, Map<Integer, Cell> map, EItemGet eItemGet) {

		if (item == null || num <= 0 || map == null) { // 非法参数
			LogManager.exception("角色编号【" + role.getId() + "】角色名称【" + role.getNick() + "】【宝石背包】添加物品异常item=" + item + ",num=" + num);
			return false;
		}

		int max = item.getMax(); // 物品最大叠加数量
		int needNum = (num - 1) / max + 1; // 所需要的格子数量
		if (isFull(needNum)) { // 背包空余格子数不足
			if (item.getType().equals(EItem.taskItem)) { // 背包空间不足，不获得任务物品
				return false;
			} else {
				MailServer.send(role.getId(), "系统邮件", "宝石背包已满，通过邮件发放", item, num, 0, 0, 0, 0, null, "系统");
				return true;
			}
		}

		int left = num;

		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			Item cellItem = cell.getItem();
			if (cellItem != null && cellItem.getId() == item.getId() && cellItem.getIsDeal() == item.getIsDeal()) {
				int leftCapacity = cellItem.getMax() - cell.getNum();
				if (leftCapacity > 0) {
					if (left <= leftCapacity) {
						cell.setNum(cell.getNum() + left);
						map.put(cell.getIndex() + Bag.MAX_CELLS, cell);

						// ======收集任务======
						TaskManager.addCollectionTaskItem(role, item, num);

						AwardServer.addItem(role, item, num); // 发送通知
						LogManager.itemGet(role, item, num, eItemGet);
						return true;
					} else {
						cell.setNum(cell.getMax());
						left = left - leftCapacity;
						map.put(cell.getIndex() + Bag.MAX_CELLS, cell);
					}
				}
			}
		}

		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			Item cellItem = cell.getItem();
			if (cellItem == null) {

				available--;

				if (item.getMax() >= left) {
					save(cell, item, left, map);

					// ======收集任务======
					TaskManager.addCollectionTaskItem(role, item, num);

					AwardServer.addItem(role, item, num); // 发送通知
					LogManager.itemGet(role, item, num, eItemGet);
					return true;
				} else {
					left = left - item.getMax();
					save(cell, item, max, map);
				}
			}
		}

		AwardServer.addItem(role, item, num); // 发送通知
		LogManager.itemGet(role, item, num, eItemGet);
		return true;
	}

	/**
	 * 判断物品一共有多少个
	 * 
	 * @param itemId
	 * @return
	 */
	public int getTheItemNum(int itemId) {
		int num = 0;
		for (int i = 0; i < cells.length; i++) {
			if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId) {
				num = cells[i].getNum() + num;
			}
		}
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
		for (int i = 0; i < cells.length; i++) {
			if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 0) {
				num = cells[i].getNum() + num;
			}
		}
		return num;
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
		int sum = 0;

		if (isBind == 0) { // 不绑定

			for (int i = 0; i < cells.length; i++) {
				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 0) {
					sum += cells[i].getNum();
					map.put(i, cells[i].getNum());
					if (sum >= num) {
						map.put(i, cells[i].getNum() - sum + num);
						return map;
					}
				}
			}
			map.clear();
		} else { // 绑定
			for (int i = 0; i < cells.length; i++) {
				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 1) {
					sum += cells[i].getNum();
					map.put(i, cells[i].getNum());
					if (sum >= num) {
						map.put(i, cells[i].getNum() - sum + num);
						return map;
					}
				}
			}

			for (int i = 0; i < cells.length; i++) {
				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 0) {
					sum += cells[i].getNum();
					map.put(i, cells[i].getNum());
					if (sum >= num) {
						map.put(i, cells[i].getNum() - sum + num);
						return map;
					}
				}
			}
			map.clear();
		}

		return map;
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
		if (index < 0 || index > BagStone.DEFAULT_CELLS) {
			return false;
		}
		Cell cell = cells[index];
		Item item = cell.getItem();
		int quantity = cell.getNum();
		if (quantity < num) {
			return false;
		} else if (quantity == num) {
			cell.clean();
			available++; // 空闲格子数量增加
		} else {
			cell.setNum(quantity - num);
		}

		// ======收集任务======
		TaskManager.deleteCollectionTaskItem(role, item, num);

		LogManager.item(role, item, num, itemCost); // 物品使用日志
		cellsMap.put(index, cell);

		return true;
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
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int haveNum = 0;
		for (Cell cell : cells) {
			if (cell.getItem() != null && cell.getItem().getId() == itemId) {
				int needNum = cell.getNum();
				if (haveNum + needNum == num) {
					map.put(cell.getIndex(), needNum);
					success = true;
					break;
				} else if (haveNum + needNum > num) {
					map.put(cell.getIndex(), num - haveNum);
					success = true;
					break;
				} else {
					map.put(cell.getIndex(), needNum);
				}
				haveNum += needNum;
			}
		}

		if (success) {
			Set<Entry<Integer, Integer>> set = map.entrySet();
			for (Iterator<Entry<Integer, Integer>> it = set.iterator(); it.hasNext();) {
				Entry<Integer, Integer> entry = it.next();
				removeByIndex(entry.getKey(), entry.getValue(), cellMap, itemCost);
			}
		}

		return success;
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
	public void save(Cell cell, Item item, int num, Map<Integer, Cell> map) {
		cell.setItem(item);
		cell.setNum(num);
		map.put(cell.getIndex() + Bag.MAX_CELLS, cell); // 存储信息
	}

	public String toString() {
		String str = "";
		for (Cell cell : cells) {
			String cellStr = cell.encode();
			str += cellStr + "&";
		}
		return str;
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
		if (isBind == 0) { // 不绑定
			for (int i = 0; i < cells.length; i++) {
				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 0) {
					map.put(i, cells[i].getNum());
				}
			}
		} else { // 绑定
			for (int i = 0; i < cells.length; i++) {
				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 1) {
					map.put(i, cells[i].getNum());
				}
			}
			for (int i = 0; i < cells.length; i++) {
				if (cells[i].getItem() != null && cells[i].getItem().getId() == itemId && cells[i].getItem().getIsDeal() == 0) {
					map.put(i, cells[i].getNum());
				}
			}
		}
		return map;
	}

	/**
	 * 背包解码
	 * 
	 * @param str
	 *            背包字符串
	 * @return 背包对象
	 */
	public static BagStone decode(String str) {
		if (str == null || "".equals(str)) {
			return new BagStone(BagStone.DEFAULT_CELLS); // 背包解码错误，则返回一个空背包
		}

		BagStone bag = null;
		try {
			bag = new BagStone(BagStone.DEFAULT_CELLS);
			String[] strs = str.split("&");
			for (int i = 0; i < strs.length; i++) {
				Cell stoneCell = Cell.decode(strs[i]);
				bag.getCells()[i] = stoneCell;
				if (stoneCell.getItem() != null) {
					bag.setAvailable(bag.getAvailable() - 1);
				}
			}

		} catch (Exception e) {
			LogManager.error("宝石背包解码错误");
			bag = new BagStone(BagStone.DEFAULT_CELLS); // 背包解码错误，则返回一个空背包
		}
		return bag;
	}

	public void addRole(Role role) {
		this.role = role;
	}

	public Role role() {
		return this.role;
	}

	/**
	 * @return the cells
	 */
	public Cell[] getCells() {
		return cells;
	}

	/**
	 * @param cells
	 *            the cells to set
	 */
	public void setCells(Cell[] cells) {
		this.cells = cells;
	}

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

}
