package iyunu.NewTLOL.timer;

import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.processor.activity.HuntskillProcessorCenter;
import iyunu.NewTLOL.util.StringControl;


/**
 * 猎技
 * 
 * @author SunHonglei
 * 
 */
public class TimeJobHuntskill {

	public void excute() {
		
		HuntskillProcessorCenter huntskillProcessorCenter = new HuntskillProcessorCenter();
		huntskillProcessorCenter.start();
		
		String content = StringControl.yel("帮派寻宝活动已开启，请回到本帮派参加");
		BulletinManager.instance().addBulletinRock(content, 2);
	}
}
