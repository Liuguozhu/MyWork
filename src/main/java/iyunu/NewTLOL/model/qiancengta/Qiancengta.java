package iyunu.NewTLOL.model.qiancengta;

/**
 * @function 角色千层塔信息
 * @author LuoSR
 * @date 2014年8月15日
 */
public class Qiancengta {
	private long id; // 角色编号
	private String name; // 角色名称
	private int historyFloorId; // 历史千层塔层数

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHistoryFloorId() {
		return historyFloorId;
	}

	public void setHistoryFloorId(int historyFloorId) {
		this.historyFloorId = historyFloorId;
	}

}
