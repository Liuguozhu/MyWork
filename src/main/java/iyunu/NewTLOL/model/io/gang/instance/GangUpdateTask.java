package iyunu.NewTLOL.model.io.gang.instance;

import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.io.gang.GangIOTask;

public class GangUpdateTask extends GangIOTask {

	public GangUpdateTask(Gang gang) {
		super(gang);
	}

	@Override
	public void excute() {
		try {
			// 把member转成HashSet<Long>存入数据库
			gang.setMembersString(GangManager.instance().memberToSet(gang.getMembers()));
			gangService.updateGang(gang);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void callBack() {
	}

}
