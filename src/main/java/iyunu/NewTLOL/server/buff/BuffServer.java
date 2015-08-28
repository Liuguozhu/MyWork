package iyunu.NewTLOL.server.buff;

import iyunu.NewTLOL.json.RoleJson;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.Time;

import java.util.Iterator;
import java.util.Map.Entry;

public class BuffServer {

	/**
	 * 添加BUFF
	 * 
	 * @param role
	 *            角色对象
	 * @param buffId
	 *            BUFF编号
	 */
	public static void addBuff(Role role, int buffId) {
		addBuff(role, buffId, 1);
	}

	/**
	 * 添加BUFF
	 * 
	 * @param role
	 *            角色对象
	 * @param buffId
	 *            BUFF编号
	 * @param num
	 *            数量
	 */
	public static void addBuff(Role role, int buffId, int num) {
		BuffRole newBuff = RoleJson.instance().getbuffRoleResById(buffId);
		if (newBuff != null) {

			switch (newBuff.getType()) {
			case hp:
			case mp:
			case php:
				if (role.getBuffs().containsKey(newBuff.getType())) {
					BuffRole oldBuff = role.getBuffs().get(newBuff.getType());
					oldBuff.setValue(oldBuff.getValue() + newBuff.getValue() * num);
				} else {
					newBuff.setValue(newBuff.getValue() * num);
					role.getBuffs().put(newBuff.getType(), newBuff);
				}

				break;
			case doubleExp:
			case doubleGold:

				if (role.getBuffs().containsKey(newBuff.getType())) {
					BuffRole oldBuff = role.getBuffs().get(newBuff.getType());
					oldBuff.setFinishTime(oldBuff.getFinishTime() + newBuff.getTime() * Time.MILLISECOND * num);
				} else {
					newBuff.setFinishTime(System.currentTimeMillis() + newBuff.getTime() * Time.MILLISECOND * num);
					role.getBuffs().put(newBuff.getType(), newBuff);
				}

				break;
			case monster:
				if (role.getBuffs().containsKey(newBuff.getType())) {
					BuffRole oldBuff = role.getBuffs().get(newBuff.getType());
					if (oldBuff.getLevel() < newBuff.getLevel()) {
						newBuff.setFinishTime(System.currentTimeMillis() + newBuff.getTime() * Time.MILLISECOND * num);
						role.getBuffs().put(newBuff.getType(), newBuff);
					} else if (oldBuff.getLevel() == newBuff.getLevel()) {
						oldBuff.setFinishTime(oldBuff.getFinishTime() + newBuff.getTime() * Time.MILLISECOND * num);
					}
				} else {
					newBuff.setFinishTime(System.currentTimeMillis() + newBuff.getTime() * Time.MILLISECOND * num);
					role.getBuffs().put(newBuff.getType(), newBuff);
				}
				break;
			default:

				if (role.getBuffs().containsKey(newBuff.getType())) {
					BuffRole oldBuff = role.getBuffs().get(newBuff.getType());
					if (oldBuff.getLevel() <= newBuff.getLevel()) {
						role.getBuffs().put(newBuff.getType(), newBuff);
					}
				} else {
					role.getBuffs().put(newBuff.getType(), newBuff);
				}

				break;
			}
			// ======刷新BUFF协议======
			SendMessage.sendNewBuff(role);
		}
	}

	/**
	 * 移除BUFF
	 * 
	 * @param role
	 *            角色对象
	 * @param type
	 *            buff类型
	 */
	public static void removeBuff(Role role, EBuffType type) {
		role.getBuffs().remove(type);
		// ======刷新BUFF协议======
		SendMessage.sendNewBuff(role);
	}

	/**
	 * 检查BUFF
	 * 
	 * @param role
	 *            角色对象
	 */
	public static boolean checkBuff(Role role) {
		boolean result = false;
		Iterator<Entry<EBuffType, BuffRole>> it = role.getBuffs().entrySet().iterator();
		while (it.hasNext()) {
			Entry<EBuffType, BuffRole> entry = it.next();
			if (entry.getValue().getFinishTime() != -1) {
				if (entry.getValue().getFinishTime() < System.currentTimeMillis()) {
					it.remove();
					result = true;
				}
			} else {
				if (entry.getValue().getValue() <= 0) {
					it.remove();
					result = true;
				}
			}
		}
		if (result) {
			// ======刷新BUFF协议======
			SendMessage.sendNewBuff(role);
		}
		return result;
	}

}
