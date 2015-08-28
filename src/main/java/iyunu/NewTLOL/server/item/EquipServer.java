package iyunu.NewTLOL.server.item;

import iyunu.NewTLOL.model.item.AddProperty;
import iyunu.NewTLOL.model.item.instance.Equip;

public class EquipServer {

	public static int countPower(Equip equip) {

		int equipHpMax = (int) ((equip.getPropertyPercent() / 10000f + 1) * equip.getHpMax());
		int equipMpMax = (int) ((equip.getPropertyPercent() / 10000f + 1) * equip.getMpMax());
		int equipPattack = (int) ((equip.getPropertyPercent() / 10000f + 1) * equip.getPattack());
		int equipPdefence = (int) ((equip.getPropertyPercent() / 10000f + 1) * equip.getPdefence());
		int equipMattack = (int) ((equip.getPropertyPercent() / 10000f + 1) * equip.getMattack());
		int equipMdefence = (int) ((equip.getPropertyPercent() / 10000f + 1) * equip.getMdefence());
		int equipSpeed = (int) ((equip.getPropertyPercent() / 10000f + 1) * equip.getSpeed());

		// 基础
		int base = 0;
		base += equipHpMax / 5; // 生命值上限
		base += equipMpMax / 2; // 内力值上限
		base += equipPattack / 2; // 外功攻击
		base += equipPdefence / 3; // 外功防御
		base += equipMattack / 2; // 内功攻击
		base += equipMdefence / 3; // 内功防御
		base += equipSpeed; // 速度

		int addProperty = 0;
		for (AddProperty property : equip.getAddPropertyList()) {
			switch (property.getType()) {
			case addMattack:
				addProperty += property.getValue() / 2;
				break;
			case addPattack:
				addProperty += property.getValue() / 2;
				break;
			case addMdefence:
				addProperty += property.getValue() / 3;
				break;
			case addPdefence:
				addProperty += property.getValue() / 3;
				break;
			case addHp:
				addProperty += property.getValue() / 5;
				break;
			case addMp:
				addProperty += property.getValue() / 2;
				break;
			case addSpeed:
				addProperty += property.getValue();
				break;
			case addHit:
				addProperty += property.getValue();
				break;
			case addDodge:
				addProperty += property.getValue() / 2;
				break;
			case addCrit:
				addProperty += property.getValue() / 3;
				break;
			case addParry:
				addProperty += property.getValue() / 3;
				break;
			default:
				break;
			}
		}

		return (base + addProperty);

	}
}
