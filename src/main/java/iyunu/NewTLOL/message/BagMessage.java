package iyunu.NewTLOL.message;

import iyunu.NewTLOL.model.bag.Bag;
import iyunu.NewTLOL.model.bag.BagStone;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.AddProperty;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Drawing;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class BagMessage {

	/**
	 * ' 发送装备
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendEquip(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshEquip");
				EEquip[] equips = EEquip.values();
				for (EEquip eEquip : equips) {
					Equip equip = role.getEquipments().get(eEquip);
					if (equip != null) {
						LlpMessage message = llpMessage.write("equipInfoList");
						message.write("itemId", equip.getId());
						message.write("icon", equip.getIcon());
						message.write("part", equip.getPart().ordinal());
						message.write("isDeal", equip.getIsDeal());
						message.write("star", equip.getStar());

						for (AddProperty addProperty : equip.getAddPropertyList()) {
							LlpMessage msg = message.write("addPropertyInfoList");
							msg.write("type", addProperty.getType().getDesc());
							msg.write("value", addProperty.getValue());
							msg.write("maxValue", addProperty.getMaxValue());
						}

						message.write("luck", equip.getLuck() > equip.getLuckLimit() ? equip.getLuckLimit() : equip.getLuck());
						if (equip.getTimeOut() > 0) {
							message.write("timeOut", "有效期至：" + Time.getTimeToStr(equip.getTimeOut()));
						} else {
							message.write("timeOut", "");
						}
					}
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.info("异常报告：发送装备信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 封装装备信息
	 * @author LuoSR
	 * @param message
	 *            消息
	 * @param equip
	 *            装备对象
	 * @date 2013年12月27日
	 */
	public static void packageEquip(LlpMessage message, Equip equip) {
		if (equip != null) {
			message.write("itemId", equip.getId());
			message.write("part", equip.getPart().ordinal());
			message.write("icon", equip.getIcon());
			message.write("isDeal", equip.getIsDeal());
			message.write("star", equip.getStar());
			for (AddProperty addProperty : equip.getAddPropertyList()) {
				LlpMessage msg = message.write("addPropertyInfoList");
				msg.write("type", addProperty.getType().getDesc());
				msg.write("value", addProperty.getValue());
				msg.write("maxValue", addProperty.getMaxValue());
			}

			if (equip.getTimeOut() > 0) {
				message.write("timeOut", "有效期至：" + Time.getTimeToStr(equip.getTimeOut()));
			} else {
				message.write("timeOut", "");
			}
		}
	}

	/**
	 * 封装格子信息
	 * 
	 * @param llpMessage
	 *            协议对象
	 * @param cell
	 *            格子对象
	 */
	public static void packedCell(LlpMessage llpMessage, Cell cell, Role role, boolean isMark) {
		LlpMessage message = llpMessage.write("cellInfoList");
		int num = cell.getNum();
		message.write("empty", num == 0 ? 0 : 1);
		message.write("index", cell.getIndex());
		message.write("num", num);

		if (num != 0) {
			Item item = cell.getItem();
			message.write("type", item.getType().ordinal());
			message.write("itemId", item.getId());
			message.write("icon", item.getIcon());
			message.write("isDeal", item.getIsDeal());
			if (isMark) { // 是否标记通知
				if (item.getType().equals(EItem.equip)) {
					Equip equip = (Equip) item;
					Equip nowEquip = role.getEquipments().get(equip.getPart());
					if (nowEquip == null) {
						if (equip.getFigure().check(role.getFigure())) {
							message.write("mark", 1);
						} else {
							message.write("mark", 0);
						}
					} else {
						if (nowEquip.totalValue() < equip.totalValue()) {
							if (equip.getFigure().equals(nowEquip.getFigure())) {
								message.write("mark", 1);
							} else {
								message.write("mark", 0);
							}
						} else {
							message.write("mark", 0);
						}
					}

					for (AddProperty addProperty : equip.getAddPropertyList()) {
						LlpMessage msg = message.write("addPropertyInfoList");
						msg.write("type", addProperty.getType().getDesc());
						msg.write("value", addProperty.getValue());
						msg.write("maxValue", addProperty.getMaxValue());
					}
					message.write("star", equip.getStar());
				} else {
					message.write("mark", item.getMark());
				}
			} else {

				if (item.getType().equals(EItem.equip)) {
					Equip equip = (Equip) item;
					message.write("mark", 0);
					for (AddProperty addProperty : equip.getAddPropertyList()) {
						LlpMessage msg = message.write("addPropertyInfoList");
						msg.write("type", addProperty.getType().getDesc());
						msg.write("value", addProperty.getValue());
						msg.write("maxValue", addProperty.getMaxValue());
					}
					message.write("star", equip.getStar());
				} else {
					message.write("mark", 0);
				}

			}

			if (item.getType().equals(EItem.drawing)) {
				Drawing drawing = (Drawing) item;
				LlpMessage msg = message.write("drawingMsg");
				msg.write("mapId", drawing.getMapId());
				msg.write("x", drawing.getX());
				msg.write("y", drawing.getY());
			}

			if (item.getTimeOut() > 0) {
				message.write("timeOut", "有效期至：" + Time.getTimeToStr(item.getTimeOut()));
			} else {
				message.write("timeOut", "");
			}
		}
	}

	/**
	 * 发送背包信息
	 * 
	 * @param role
	 *            角色信息
	 */
	public static void logonSendBag(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshBag");
				llpMessage.write("sum", role.getBag().getSize());
				for (Cell cell : role.getBag().getCells()) {
					packedCell(llpMessage, cell, role, true);
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.info("异常报告：发送全部背包信息");
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 发送背包信息
	 * 
	 * @param role
	 *            角色信息
	 * @param cells
	 *            有改动的背包格子集合
	 */
	public static void sendBag(Role role, Map<Integer, Cell> cells) {
		if (cells.size() > 0) {
			LlpMessage llpMessageCell = null;
			LlpMessage llpMessageStone = null;
			try {
				if (role != null && role.getChannel() != null && role.isLogon()) {
					llpMessageCell = LlpJava.instance().getMessage("s_refreshBag");
					llpMessageCell.write("sum", role.getBag().getSize());

					llpMessageStone = LlpJava.instance().getMessage("s_refreshStoneBag");
					llpMessageStone.write("sum", BagStone.DEFAULT_CELLS);

					Iterator<Entry<Integer, Cell>> it = cells.entrySet().iterator();
					while (it.hasNext()) {
						Entry<Integer, Cell> entry = it.next();
						if (entry.getKey() < Bag.MAX_CELLS) {
							packedCell(llpMessageCell, entry.getValue(), role, true);
						} else {
							packedStoneCell(llpMessageStone, entry.getValue(), role);
						}
					}

					role.getChannel().write(llpMessageCell);
					role.getChannel().write(llpMessageStone);
				}
			} catch (Exception e) {
				LogManager.info("异常报告：发送部分背包信息");
				e.printStackTrace();
			} finally {
				if (llpMessageCell != null) {
					llpMessageCell.destory();
				}
				if (llpMessageStone != null) {
					llpMessageStone.destory();
				}
			}
		}
	}

	/**
	 * 发送背包信息
	 * 
	 * @param role
	 *            角色信息
	 * @param cells
	 *            有改动的背包格子集合
	 */
	public static void sendBag(Role role, Map<Integer, Cell> cells, boolean isMark) {
		if (cells.size() > 0) {
			LlpMessage llpMessage = null;
			try {
				if (role != null && role.getChannel() != null && role.isLogon()) {
					llpMessage = LlpJava.instance().getMessage("s_refreshBag");
					llpMessage.write("sum", role.getBag().getSize());
					for (Cell cell : cells.values()) {
						packedCell(llpMessage, cell, role, isMark);
					}
					role.getChannel().write(llpMessage);
				}
			} catch (Exception e) {
				LogManager.info("异常报告：发送部分背包信息");
				e.printStackTrace();
			} finally {
				if (llpMessage != null) {
					llpMessage.destory();
				}
			}
		}
	}

	/**
	 * 发送仓库信息
	 * 
	 * @param role
	 *            角色信息
	 */
	public static void sendWarehouse(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshWarehouse");
				llpMessage.write("sum", role.getWarehouse().getSize());
				for (Cell cell : role.getWarehouse().getCells()) {
					packedCell(llpMessage, cell, role, true);
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.info("异常报告：发送仓库1信息");
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 发送仓库信息
	 * 
	 * @param role
	 *            角色信息
	 * @param cells
	 *            有改动的仓库格子集合
	 */
	public static void sendWarehouse(Role role, Map<Integer, Cell> cells) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshWarehouse");
				llpMessage.write("sum", role.getWarehouse().getSize());
				for (Cell cell : cells.values()) {
					packedCell(llpMessage, cell, role, true);
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送仓库2信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 封装格子信息
	 * 
	 * @param llpMessage
	 *            协议对象
	 * @param cell
	 *            格子对象
	 */
	public static void packedStoneCell(LlpMessage llpMessage, Cell cell, Role role) {
		LlpMessage message = llpMessage.write("cellStoneList");
		int num = cell.getNum();
		message.write("empty", num == 0 ? 0 : 1);
		message.write("index", cell.getIndex());
		message.write("num", num);
		if (num != 0) {
			Item item = cell.getItem();
			message.write("itemId", item.getId());
			message.write("icon", item.getIcon());
			message.write("isDeal", item.getIsDeal());
		}
	}

	/**
	 * 发送宝石背包信息
	 * 
	 * @param role
	 *            角色信息
	 */
	public static void sendStoneBag(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshStoneBag");
				llpMessage.write("sum", 100);
				for (Cell cell : role.getBagStone().getCells()) {
					packedStoneCell(llpMessage, cell, role);
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.info("异常报告：发送宝石背包信息");
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 发送背包信息
	 * 
	 * @param role
	 *            角色信息
	 * @param cells
	 *            有改动的背包格子集合
	 */
	public static void sendBagStone(Role role, Map<Integer, Cell> cells) {
		if (cells.size() > 0) {
			LlpMessage llpMessageStone = null;
			try {
				if (role != null && role.getChannel() != null && role.isLogon()) {

					llpMessageStone = LlpJava.instance().getMessage("s_refreshStoneBag");
					llpMessageStone.write("sum", BagStone.DEFAULT_CELLS);
					for (Cell cell : cells.values()) {
						packedStoneCell(llpMessageStone, cell, role);
					}
					role.getChannel().write(llpMessageStone);
				}
			} catch (Exception e) {
				LogManager.info("异常报告：发送部分宝石背包信息");
				e.printStackTrace();
			} finally {
				if (llpMessageStone != null) {
					llpMessageStone.destory();
				}
			}
		}
	}
}
