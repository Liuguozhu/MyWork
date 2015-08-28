package iyunu.NewTLOL.model.partner;

public enum ERecruit {

	low(0, 44), //
	mid(45, 64), //
	high(65, 80), //

	;

	private int minLevel;
	private int maxLevel;

	ERecruit(int minLv, int maxLv) {
		this.minLevel = minLv;
		this.maxLevel = maxLv;
	}

	public static ERecruit check(int level) {
		for (ERecruit recruit : ERecruit.values()) {
			if (recruit.maxLevel >= level) {
				return recruit;
			}
		}
		return low;
	}

	/**
	 * @return the minLevel
	 */
	public int getMinLevel() {
		return minLevel;
	}

	/**
	 * @return the maxLevel
	 */
	public int getMaxLevel() {
		return maxLevel;
	}

}
