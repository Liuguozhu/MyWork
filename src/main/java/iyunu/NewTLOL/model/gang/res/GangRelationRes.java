package iyunu.NewTLOL.model.gang.res;

public class GangRelationRes {

	private int operater; // 操作人职位ID
	private int beOperater; // 被操作人职位ID
	private String rights; // 操作权限集合

	public GangRelationRes toGangRelationRes() {
		GangRelationRes gangRelationRes = new GangRelationRes();
		gangRelationRes.setOperater(operater);
		gangRelationRes.setBeOperater(beOperater);
		gangRelationRes.setRights(rights);

		return gangRelationRes;
	}

	/**
	 * @return the operater
	 */
	public int getOperater() {
		return operater;
	}

	/**
	 * @param operater
	 *            the operater to set
	 */
	public void setOperater(int operater) {
		this.operater = operater;
	}

	/**
	 * @return the beOperater
	 */
	public int getBeOperater() {
		return beOperater;
	}

	/**
	 * @param beOperater
	 *            the beOperater to set
	 */
	public void setBeOperater(int beOperater) {
		this.beOperater = beOperater;
	}

	/**
	 * @return the rights
	 */
	public String getRights() {
		return rights;
	}

	/**
	 * @param rights
	 *            the rights to set
	 */
	public void setRights(String rights) {
		this.rights = rights;
	}

}
