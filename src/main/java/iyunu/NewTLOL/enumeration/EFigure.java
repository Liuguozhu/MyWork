package iyunu.NewTLOL.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum EFigure {

	all(1, 2, 3, 4, 5, 6), // 所有形象
	knife(5, 6), // 拿刀的
	sword(1, 2), // 拿剑的
	staff(3, 4), // 拿杖的
	knife_male(6), // 刀男
	sword_male(2), // 剑男
	staff_male(4), // 杖男
	knife_female(5), // 刀女
	sword_female(1), // 剑女
	staff_female(3), // 杖女
	;
	private List<Integer> num = new ArrayList<Integer>();

	EFigure(int... nums) {
		for (int i : nums) {
			num.add(i);
		}
	}

	/**
	 * 检查角色编号是否符合对应角色条件
	 * 
	 * @param figure
	 *            角色编号
	 * @return 满足条件
	 */
	public boolean check(long figure) {
		int index = (int) figure;
		return num.contains(index);
	}

}
