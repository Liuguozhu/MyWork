package iyunu.NewTLOL.model.shenbing;

public enum EAddType {

	addMattack("内功攻击"), //
	addPattack("外功攻击"), //
	addMdefence("内功防御"), //
	addPdefence("外功防御"), //
	addHp("生命值"), //
	addMp("内力值"), //
	addSpeed("速度"), //
	addHit("命中"), //
	addDodge("闪避"), //
	addCrit("暴击"), //
	addParry("格挡"), //
	;

	private String desc;

	EAddType(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

}
