package iyunu.NewTLOL.model.gang;

import java.util.HashMap;

/**
 * 帮派职位
 */
public enum GangJobTitle {
	/** 帮主 */
	Leader("帮主", GangRight.values()),
	/** 护法 */
	ViceLeader("副帮主", GangRight.values()),
	/** 长老 */
	Presbyter("长老", GangRight.values()),
	/** 帮众 */
	Member("帮众"),
	/** 无帮派 */
	NULL("无帮派");

	/**
	 * 职位描述
	 */
	private final String des;

	/**
	 * 拥有的权限
	 */
	private final GangRight[] rights;

	/** 编号-职位映射 */
	private static HashMap<Byte, GangJobTitle> mapping = new HashMap<Byte, GangJobTitle>();

	/**
	 * @param jobTitleVaule
	 *            职位编号
	 * @param des
	 *            描述
	 * @param rights
	 *            拥有的权限
	 */
	private GangJobTitle(final String des, final GangRight... rights) {
		this.des = des;
		this.rights = rights;
	}

	/**
	 * 根据职位编号获取职位
	 * 
	 * @param jobTitleVaule
	 *            职位编号
	 * @return GangJobTitle 职位
	 */
	public static GangJobTitle getInstance(final byte jobTitleVaule) {
		if (mapping.isEmpty())
			for (GangJobTitle instance : GangJobTitle.values())
				mapping.put((byte) instance.ordinal(), instance);

		return mapping.get(jobTitleVaule);
	}

	/**
	 * @param right
	 *            帮派权限
	 * @return boolean 是否拥有改项权限
	 */
	public boolean hasRight(final GangRight right) {
		if (rights == null)
			return false;

		for (GangRight r : rights)
			if (r == right)
				return true;

		return false;
	}

	/**
	 * @return String 职位描述
	 */
	public String getDes() {
		return des;
	}

	/**
	 * @return String 职位描述出现在角色头顶时的附加字符串
	 */
	public String getDesAtTitle() {
		if (this == Member || this == NULL)
			return "";
		else
			return " " + des;
	}
}
