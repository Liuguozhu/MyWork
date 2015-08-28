package iyunu.NewTLOL.model.io.gang.instance;

import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.io.gang.GangIOTask;

public class GangInsertTask extends GangIOTask {

	public GangInsertTask(Gang gang) {
		super(gang);
	}

	@Override
	public void excute() {
		try {
			// 把member转成HashSet<Long>存入数据库
			gang.setMembersString(GangManager.instance().memberToSet(gang.getMembers()));

			gangService.insertGang(gang);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void callBack() {
	}

}
