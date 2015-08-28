package iyunu.NewTLOL.timer;

import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.processor.activity.InvasionProcessorCenter;
import iyunu.NewTLOL.util.StringControl;


/**
 * 帮派入侵
 * 
 * @author SunHonglei
 * 
 */
public class TimeJobInvasion {

	public void excute() {
		
		InvasionProcessorCenter invasionProcessorCenter = new InvasionProcessorCenter();
		invasionProcessorCenter.start();
		
		String content = StringControl.yel("帮派入侵活动已开启！");
		BulletinManager.instance().addBulletinRock(content, 2);
	}
}
