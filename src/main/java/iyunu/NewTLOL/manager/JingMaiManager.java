package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.role.Property;

public class JingMaiManager {

	public static String[] names = { "生命上限", "内力上限", "外功攻击", "内功攻击", "外功防御", "内功防御", "命中", "闪避", "速度", "暴击", "格挡", "力量", "智力", "体质", "敏捷" };

	public static int getValueByProperty(Property property, int index) {
		switch (index) {
		case 0:
			return property.getHpMax();
		case 1:
			return property.getMpMax();
		case 2:
			return property.getPattack();
		case 3:
			return property.getMattack();
		case 4:
			return property.getPdefence();
		case 5:
			return property.getMdefence();
		case 6:
			return property.getHit();
		case 7:
			return property.getDodge();
		case 8:
			return property.getSpeed();
		case 9:
			return property.getCrit();
		case 10:
			return property.getParry();
		case 11:
			return property.getStrength();
		case 12:
			return property.getIntellect();
		case 13:
			return property.getPhysique();
		case 14:
			return property.getAgility();
		default:
			return 0;
		}
	}
}
