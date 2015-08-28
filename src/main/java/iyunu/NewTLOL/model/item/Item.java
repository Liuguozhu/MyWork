package iyunu.NewTLOL.model.item;

import iyunu.NewTLOL.enumeration.EColor;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.ShenbingJson;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.instance.Drawing;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.shenbing.res.ShenbingUpStarRes;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.json.JsonTool;

import java.util.ArrayList;
import java.util.Map;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public abstract class Item {

	/** 编号 **/
	protected int id;
	/** 唯一编号 **/
	protected String uid;
	/** 名称 **/
	protected String name;
	/** 颜色 **/
	protected EColor color = EColor.white;
	/** 描述 **/
	protected String desc;
	/** 类型 **/
	protected EItem type;
	/** 类型名称 **/
	protected String typeName;
	/** 等级 **/
	protected int level;
	/** 最大叠加数量 **/
	protected int max;
	/** 使用方式(0使用，1消耗) **/
	protected int way;
	/** 是否可以战斗使用(0均可使用，1非战斗使用，2战斗使用) **/
	protected int apply;
	/** 丢弃返还金钱 **/
	protected int returnGold;
	/** 背包显示优先级 **/
	protected int priority;
	/** 是否可以出售 **/
	protected int isSell;
	/** 数值 **/
	protected int value;
	/** 图标 **/
	protected String icon;
	// protected int sign;
	protected int mark;
	/** 是否可以交易（0.可以，1.不可以） **/
	protected int isDeal; // 存数据库
	/** 是否公告通知 **/
	protected int isNotice;
	protected int showLevel;
	/** 时间 **/
	protected int time;
	/** 到期时间 **/
	protected long timeOut = 0; // 存数据库
	/** 收集任务编号 **/
	protected ArrayList<Integer> taskIds = new ArrayList<Integer>(); // <任务编号>

	public String encode() {
		String str = this.getType().ordinal() + "!" + this.getIsDeal() + "!";
		switch (this.getType()) {
		case equip:
			SerializeWriter out = new SerializeWriter();
			JSONSerializer serializer = new JSONSerializer(out);
			serializer.write(this);
			str += serializer.toString();
			break;
		case drawing:
			Drawing drawing = (Drawing) this;
			str += drawing.getId() + "!" + drawing.getMapId() + "!" + drawing.getX() + "!" + drawing.getY();
			break;
		default:
			str += this.getId();
			break;
		}
		return str;
	}

	public static Item decode(String str) {
		String[] strs = str.split("!");
		EItem eItem = EItem.values()[Translate.stringToInt(strs[0])];
		Item item = null;
		switch (eItem) {
		case equip: // 装备
			Equip equip = (Equip) JsonTool.decode(strs[2], eItem.getObject());
			Equip newEquip = (Equip) ItemJson.instance().getItem(equip.getId(), equip.getUid());
			newEquip.setSteps(equip.getSteps());
			newEquip.setStar(equip.getStar());
			newEquip.setLuck(equip.getLuck());
//			newEquip.setLuckLimit(equip.getLuckLimit());
//			newEquip.setPropertyPercent(equip.getPropertyPercent());
			
			ShenbingUpStarRes newShenbingUpStarRes = ShenbingJson.instance().getShenbingUpStarResMap().get(equip.getStar());
			newEquip.setLuckLimit(newShenbingUpStarRes.getLuckLimit());
			newEquip.setPropertyPercent(newShenbingUpStarRes.getPropertyPercent());
			
			newEquip.setTimeOut(equip.getTimeOut());
			for (AddProperty addProperty : equip.getAddPropertyList()) {
				newEquip.getAddPropertyList().add(addProperty);
			}
			equip.getAddPropertyList().clear();

			item = newEquip;

			if (equip.getTimeOut() > 0 && equip.getTimeOut() < System.currentTimeMillis()) {
				item = null;
			}
			break;
		case drawing: // 藏宝图
			item = ItemJson.instance().getItem(Translate.stringToInt(strs[2]));
			if (item != null) {
				Drawing drawing = (Drawing) item;
				drawing.setMapId(Translate.stringToInt(strs[3]));
				drawing.setX(Translate.stringToInt(strs[4]));
				drawing.setY(Translate.stringToInt(strs[5]));
			}
			break;
		default: // 其他物品
			item = ItemJson.instance().getItem(Translate.stringToInt(strs[2]));
			break;
		}
		if (item != null) {
			item.setIsDeal(Translate.stringToInt(strs[1]));
		}
		return item;
	}

	/**
	 * 复制
	 * 
	 * @return 自身复制对象
	 */
	public abstract Item copy();

	/**
	 * 使用物品（若不能使用返回失败）
	 * 
	 * @param role
	 *            使用者
	 * @return 使用成功
	 */
	public abstract boolean use(Role role, int num, Map<Integer, Cell> cellsMap);

	public abstract boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo);

	public abstract void change();

	/**
	 * 设置时效
	 * 
	 * @return
	 */
	public Item timeOut() {
		if (time > 0 && timeOut == 0) {
			timeOut = System.currentTimeMillis() + time * Time.MINUTE_MILLISECOND;
		}

		return this;
	}

	/**
	 * 绑定
	 */
	public Item bind() {
		isDeal = 1;
		return this;
	}

	/**
	 * 解绑
	 */
	public Item outBind() {
		isDeal = 0;
		return this;
	}

	@SuppressWarnings("unchecked")
	public static void init(Item newItem, Item oldItem) {
		newItem.setId(oldItem.getId());
		newItem.setUid(oldItem.getUid());
		newItem.setName(oldItem.getName());
		newItem.setType(oldItem.getType());
		newItem.setTypeName(oldItem.getTypeName());
		newItem.setDesc(oldItem.getDesc());
		newItem.setColor(oldItem.getColor());
		newItem.setLevel(oldItem.getLevel());
		newItem.setMax(oldItem.getMax());
		newItem.setWay(oldItem.getWay());
		newItem.setApply(oldItem.getApply());
		newItem.setValue(oldItem.getValue());
		newItem.setReturnGold(oldItem.getReturnGold());
		newItem.setPriority(oldItem.getPriority());
		newItem.setIsSell(oldItem.getIsSell());
		newItem.setIcon(oldItem.getIcon());
		newItem.setMark(oldItem.getMark());
		newItem.setIsDeal(oldItem.getIsDeal());
		newItem.setIsNotice(oldItem.getIsNotice());
		newItem.setShowLevel(oldItem.getShowLevel());
		newItem.setTime(oldItem.getTime());
		newItem.setTimeOut(oldItem.getTimeOut());
		newItem.setTaskIds((ArrayList<Integer>) oldItem.getTaskIds().clone());
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the color
	 */
	public EColor getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(EColor color) {
		this.color = color;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the type
	 */
	public EItem getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EItem type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
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
	 * @return the way
	 */
	public int getWay() {
		return way;
	}

	/**
	 * @param way
	 *            the way to set
	 */
	public void setWay(int way) {
		this.way = way;
	}

	/**
	 * @return the apply
	 */
	public int getApply() {
		return apply;
	}

	/**
	 * @param apply
	 *            the apply to set
	 */
	public void setApply(int apply) {
		this.apply = apply;
	}

	public int getReturnGold() {
		return returnGold;
	}

	public void setReturnGold(int returnGold) {
		this.returnGold = returnGold;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getIsSell() {
		return isSell;
	}

	public void setIsSell(int isSell) {
		this.isSell = isSell;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the sign
	 */
	// public int getSign() {
	// return sign;
	// }
	//
	// /**
	// * @param sign
	// * the sign to set
	// */
	// public void setSign(int sign) {
	// this.sign = sign;
	// }

	/**
	 * @return the mark
	 */
	public int getMark() {
		return mark;
	}

	/**
	 * @param mark
	 *            the mark to set
	 */
	public void setMark(int mark) {
		this.mark = mark;
	}

	/**
	 * @return the isDeal
	 */
	public int getIsDeal() {
		return isDeal;
	}

	/**
	 * @param isDeal
	 *            the isDeal to set
	 */
	public void setIsDeal(int isDeal) {
		this.isDeal = isDeal;
	}

	/**
	 * @return the isNotice
	 */
	public int getIsNotice() {
		return isNotice;
	}

	/**
	 * @param isNotice
	 *            the isNotice to set
	 */
	public void setIsNotice(int isNotice) {
		this.isNotice = isNotice;
	}

	/**
	 * @return the showLevel
	 */
	public int getShowLevel() {
		return showLevel;
	}

	/**
	 * @param showLevel
	 *            the showLevel to set
	 */
	public void setShowLevel(int showLevel) {
		this.showLevel = showLevel;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return the timeOut
	 */
	public long getTimeOut() {
		return timeOut;
	}

	/**
	 * @param timeOut
	 *            the timeOut to set
	 */
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * @return the taskIds
	 */
	public ArrayList<Integer> getTaskIds() {
		return taskIds;
	}

	/**
	 * @param taskIds
	 *            the taskIds to set
	 */
	public void setTaskIds(ArrayList<Integer> taskIds) {
		this.taskIds = taskIds;
	}

}
