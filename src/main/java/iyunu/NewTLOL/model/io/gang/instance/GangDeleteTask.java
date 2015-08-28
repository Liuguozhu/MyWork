package iyunu.NewTLOL.model.io.gang.instance;

import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.io.gang.GangIOTask;

public class GangDeleteTask extends GangIOTask {

	public GangDeleteTask(Gang gang) {
		super(gang);
	}

	@Override
	public void excute() {
		try {
			gangService.deleteGang(gang.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void callBack() {
	}

}
