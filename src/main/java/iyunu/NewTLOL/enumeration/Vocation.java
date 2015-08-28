package iyunu.NewTLOL.enumeration;

/**
 * 门派
 * 
 * @author SunHonglei
 * 
 */
public enum Vocation {

	/** 无职业 **/
	none("无"),
	/** 鼎寺（少林） **/
	sl("鼎寺"),
	/** 青轩（姑苏慕容） **/
	gsmr("青轩"),
	/** 天旗（大理） **/
	dl("天旗"),
	/** 鬼谷（灵鹫宫） **/
	ljg("鬼谷"), //
	wg("外功怪"), //
	ng("内功怪"), //
	;

	private String name;

	Vocation(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}