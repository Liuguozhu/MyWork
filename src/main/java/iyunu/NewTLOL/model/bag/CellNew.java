package iyunu.NewTLOL.model.bag;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.item.AddProperty;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Drawing;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.json.JsonTool;

import com.alibaba.fastjson.JSON;

public class CellNew {

	/** 背包索引 **/
	private int index;
	private int num;
	private int max;
	private Item item;

	public CellNew(int index) {
		this.index = index;
	}

	/**
	 * 清除格子
	 */
	public void clean() {
		this.item = null;
		this.num = 0;
		this.max = 0;
	}

	/**
	 * 编码成字符串
	 * 
	 * @return 字符串 // index#num#max#类型#item
	 */
	public String encode() {
		if (item != null) {
			return index + "#" + num + "#" + item.encode();
		} else {
			return index + "#" + num + "#null";
		}
	}

	/**
	 * 解码成格子对象
	 * 
	 * @param str
	 *            字符串
	 * @return 格子对象
	 */
	public static CellNew oldDecode(String str) {
		if ("".equals(str)) {
			return null;
		} else {
			String[] strs = str.split("#");
			EItem eItem = EItem.values()[Translate.stringToInt(strs[3])];
			Item item = null;
			switch (eItem) {
			case equip:
				Equip equip = (Equip) JsonTool.decode(strs[4], eItem.getObject());
				Equip newEquip = (Equip) ItemJson.instance().getItem(equip.getId(), equip.getUid());
				newEquip.setSteps(equip.getSteps());
				newEquip.setStar(equip.getStar());
				newEquip.setLuck(equip.getLuck());
				newEquip.setLuckLimit(equip.getLuckLimit());
				newEquip.setPropertyPercent(equip.getPropertyPercent());
				newEquip.setTimeOut(equip.getTimeOut());
				// newEquip.setIsDeal(equip.getIsDeal());
				for (AddProperty addProperty : equip.getAddPropertyList()) {
					newEquip.getAddPropertyList().add(addProperty);
				}
				equip.getAddPropertyList().clear();

				if (strs.length >= 6) {
					newEquip.setIsDeal(Translate.stringToInt(strs[5]));
				}

				item = newEquip;

				if (equip.getTimeOut() > 0 && equip.getTimeOut() < System.currentTimeMillis()) {
					item = null;
				}
				break;
			case drawing:
				item = ItemJson.instance().getItem(Translate.stringToInt(strs[4]));
				if (item != null) {
					Drawing drawing = (Drawing) item;
					drawing.setMapId(Translate.stringToInt(strs[5]));
					drawing.setX(Translate.stringToInt(strs[6]));
					drawing.setY(Translate.stringToInt(strs[7]));
					if (strs.length >= 9) {
						drawing.setIsDeal(Translate.stringToInt(strs[8]));
					}
				}
				break;
			default:
				item = ItemJson.instance().getItem(Translate.stringToInt(strs[4]));
				if (item != null && strs.length >= 6) {
					item.setIsDeal(Translate.stringToInt(strs[5]));
				}
				break;
			}
			CellNew cell = new CellNew(Translate.stringToInt(strs[0]));
			if (item != null) {
				cell.setNum(Translate.stringToInt(strs[1]));
				cell.setMax(Translate.stringToInt(strs[2]));
				cell.setItem(item);
			}

			return cell;
		}
	}

	public static CellNew decode(String string) {

		String[] strings = string.split("#");
		CellNew cell = new CellNew(Translate.stringToInt(strings[0]));
		cell.setNum(Translate.stringToInt(strings[1]));
		if (cell.getNum() > 0) {
			Item item = Item.decode(strings[2]);
			cell.setItem(item);
			cell.setMax(item.getMax());
		} else {
			cell.setMax(0);
		}
		return cell;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
		if (item != null) {
			this.max = item.getMax();
		} else {
			this.max = 0;
		}
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

}
