package iyunu.NewTLOL.timer;

import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.model.gang.FightApplyInfo;
import iyunu.NewTLOL.processor.gangFight.GangFightOutProcessorCenter;
import iyunu.NewTLOL.processor.gangFight.GangFightRoundProcessorCenter;

/**
 * 帮派战循环赛
 * 
 * @author SunHonglei
 * 
 */
public class TimeJobGangFightRound {

	/**
	 * 报名
	 */
	public void apply() {

		GangFightManager.save();
		GangFightManager.resetApply();

		for (FightApplyInfo fightApplyInfo : GangFightManager.gangFightOutMap) {
			GangFightManager.apply(fightApplyInfo);
		}
		System.out.println("帮派战报名");
	}

	public void excute() {
		GangFightRoundProcessorCenter gangFightRoundProcessorCenter = new GangFightRoundProcessorCenter();
		gangFightRoundProcessorCenter.start();

		BulletinManager.instance().addBulletinRock("帮派战循环赛已经开始！请报名的帮派立即前往征战！", 1);
	}

	public void out() {
		GangFightOutProcessorCenter fightOutProcessorCenter = new GangFightOutProcessorCenter();
		fightOutProcessorCenter.start();

		BulletinManager.instance().addBulletinRock("帮派战淘汰赛已经开始！请进入8强的帮派立即前往征战！", 1);
	}
}
