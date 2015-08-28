package iyunu.NewTLOL.model.gang.res;

public class GangOperateMenuRes {

	private int id; // 帮派操作选项ID ，与协议c_gangOperate对应
	private String name; // 操作返回的对应文字

	public GangOperateMenuRes toGangOperateMenuRes() {
		GangOperateMenuRes gangOperateMenuRes = new GangOperateMenuRes();
		gangOperateMenuRes.setId(id);
		gangOperateMenuRes.setName(name);

		return gangOperateMenuRes;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
