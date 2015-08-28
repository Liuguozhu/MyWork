package iyunu.NewTLOL.model.role;

public enum ERoleFightState {

	none, // 无
	left, // 左边
	right, // 右边

	;

	public static boolean same(ERoleFightState fightState1, ERoleFightState fightState2) {
		if (fightState1.equals(fightState2)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean check(ERoleFightState fightState1, ERoleFightState fightState2) {

		if (fightState1.equals(ERoleFightState.left)) {
			if (fightState2.equals(ERoleFightState.right)) {
				return true;
			} else {
				return false;
			}
		} else if (fightState1.equals(ERoleFightState.right)) {
			if (fightState2.equals(ERoleFightState.left)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (fightState2.equals(ERoleFightState.none)) {
				return true;
			} else {
				return false;
			}
		}

	}
}
