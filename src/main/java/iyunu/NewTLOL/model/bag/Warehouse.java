package iyunu.NewTLOL.model.bag;

import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Warehouse {

	public static final int DEFAULT_CELLS = 25;
	public static final int MAX_CELLS = 50;
	private Cell[] cells; // 格子
	private int size; // 格子数量
	private int available; // 空闲格子数量
	private boolean warn = false; // 仓库异常警告
	private boolean hasStone = false;

	/**
	 * 仓库构造方法
	 * 
	 * @param size
	 *            仓库格子数量
	 */
	public Warehouse(int size) {
		this.size = size;
		this.available = size;
		cells = new Cell[size];
		for (int i = 0; i < size; i++) {
			Cell cell = new Cell();
			cell.setIndex(i);
			cells[i] = cell;
		}
	}

	/**
	 * 拓展仓库
	 */
	public boolean expand(int expandNum) {
		if (size >= MAX_CELLS) {
			return false;
		}
		Cell[] newCells = new Cell[size + expandNum];

		for (int i = 0; i < newCells.length; i++) {
			if (size > i) {
				newCells[i] = cells[i];
			} else {
				Cell cell = new Cell();
				cell.setIndex(i);
				newCells[i] = cell;
			}
		}

		cells = newCells;
		size = size + expandNum;
		return true;
	}

	/**
	 * 仓库是否已满
	 * 
	 * @return 仓库已满
	 */
	public boolean isFull() {
		if (available > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断仓库是否有指定数量空格
	 * 
	 * @param num
	 *            指定数量
	 * @return 仓库空间不足
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
	public void save(Cell cell, Item item, int num, Map<Integer, Cell> map) {
		cell.setItem(item);
		cell.setNum(num);
		map.put(cell.getIndex(), cell); // 存储信息
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
	 * @return 添加成功（失败原因是仓库已满）
	 */
	public boolean add(Item item, int num, Map<Integer, Cell> map) {

		if (item == null || num <= 0 || map == null) { // 非法参数
			return false;
		}

		int max = item.getMax(); // 物品最大叠加数量
		int needNum = (num - 1) / max + 1; // 所需要的格子数量
		if (isFull(needNum)) { // 仓库空余格子数不足
			return false;
		}

		int left = num; // 所添加的物品剩余数量

		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			Item cellItem = cell.getItem();
			if (cellItem != null && cellItem.getId() == item.getId() && cellItem.getIsDeal() == item.getIsDeal()) {
				int leftCapacity = cellItem.getMax() - cell.getNum();
				if (leftCapacity > 0) {
					if (left <= leftCapacity) {
						cell.setNum(cell.getNum() + left);
						map.put(cell.getIndex(), cell);
						return true;
					} else {
						cell.setNum(cell.getMax());
						left = left - leftCapacity;
						map.put(cell.getIndex(), cell);
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
					return true;
				} else {
					left = left - item.getMax();
					save(cell, item, max, map);
				}
			}
		}

		return true;
	}

	/**
	 * 删除指定格子内物品
	 * 
	 * @param index
	 *            仓库格子索引
	 * @param num
	 *            数量
	 * @return 删除成功
	 */
	public boolean removeByIndex(int index, int num) {
		Cell cell = cells[index];
		int quantity = cell.getNum();
		if (quantity < num) {
			return false;
		} else if (quantity == num) {
			cell.clean();
			available++; // 空闲格子数量增加
		} else {
			cell.setNum(quantity - num);
		}
		return true;
	}

	public void clean(int index) {
		cells[index].clean();
		available++; // 空闲格子数量增加
	}

	public String toString() {
		String str = "" + size;
		for (Cell cell : cells) {
			String cellStr = cell.encode();
			str += "&" + cellStr;
		}

		return str;
	}

	/**
	 * 仓库编码成字符串
	 * 
	 * @return 仓库字符串
	 */
	public String encode() {
		String str = "" + size;
		for (Cell cell : cells) {
			String cellStr = cell.encode();
			str += "&" + cellStr;
		}

		return str;
	}

	/**
	 * 仓库解码
	 * 
	 * @param str
	 *            仓库字符串
	 * @return 仓库对象
	 */
	public static Warehouse decode(String str) {
		Warehouse warehouse = null;

		if (str == null || "".equals(str)) {
			return new Warehouse(Warehouse.DEFAULT_CELLS); // 仓库解码错误，则返回一个空仓库
		}

		try {
			String[] strs = str.split("&");
			int size = Translate.stringToInt(strs[0]);
			size = size >= Warehouse.DEFAULT_CELLS ? size : Warehouse.DEFAULT_CELLS;
			warehouse = new Warehouse(size);

			for (int i = 1; i < strs.length; i++) {
				Cell cell = Cell.decode(strs[i]);
				warehouse.getCells()[i - 1] = cell;
				if (cell.getItem() != null) {
					warehouse.setAvailable(warehouse.getAvailable() - 1);
					
					// ======判断背包中是否有宝石（临时使用）======
					if (!warehouse.isHasStone() && cell.getItem().getType().equals(EItem.stone)) {
						warehouse.setHasStone(true);
					}
					// ======判断背包中是否有宝石（临时使用）======
				}
			}
		} catch (Exception e) {
			LogManager.error("仓库解码错误");
			warehouse = new Warehouse(Warehouse.DEFAULT_CELLS); // 仓库解码错误，则返回一个空仓库
			warehouse.setWarn(true);
		}
		return warehouse;
	}

	/**
	 * 仓库解码
	 * 
	 * @param str
	 *            仓库字符串
	 * @return 仓库对象
	 */
	public static Warehouse oldDecode(String str) {
		Warehouse warehouse = null;
		try {
			String[] strs = str.split("&");
			int size = Translate.stringToInt(strs[0]);
			warehouse = new Warehouse(size);

			for (int i = 1; i < strs.length; i++) {
				Cell cell = Cell.oldDecode(strs[i]);
				if (cell != null) {
					warehouse.getCells()[i - 1] = cell;
					warehouse.setAvailable(warehouse.getAvailable() - 1);
				}
			}
		} catch (Exception e) {
			LogManager.error("仓库解码错误");
			warehouse = new Warehouse(Warehouse.DEFAULT_CELLS); // 仓库解码错误，则返回一个空仓库
			warehouse.setWarn(true);
		}
		return warehouse;
	}

	/**
	 * @function 整理仓库
	 * @author LuoSR
	 * @date 2013年12月25日
	 */
	public void sortWarehouse() {

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
		Collections.sort(list, new Comparator<Cell>() {
			@Override
			public int compare(Cell o1, Cell o2) {
				if (o1.getItem().getPriority() < o2.getItem().getPriority()) {
					return -1;
				} else {
					return 1;
				}
			}
		});

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
	 * @return the warn
	 */
	public boolean isWarn() {
		return warn;
	}

	/**
	 * @param warn
	 *            the warn to set
	 */
	public void setWarn(boolean warn) {
		this.warn = warn;
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

	/**
	 * @return the hasStone
	 */
	public boolean isHasStone() {
		return hasStone;
	}

	/**
	 * @param hasStone the hasStone to set
	 */
	public void setHasStone(boolean hasStone) {
		this.hasStone = hasStone;
	}

}
