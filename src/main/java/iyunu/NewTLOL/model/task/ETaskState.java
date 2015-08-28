package iyunu.NewTLOL.model.task;

public enum ETaskState {

	none, // 不可接取
	/** 可接取 **/
	not, // 可接取
	during, // 进行中
	finish, // 已完成
	failed, // 失败
	;

	public static ETaskState getETaskStateByIndex(int index) {
		switch (index) {
		case 0:
			return none;
		case 1:
			return not;
		case 2:
			return during;
		case 3:
			return finish;
		case 4:
			return failed;
		default:
			return none;
		}
	}

}
