package iyunu.NewTLOL.processor;

import iyunu.NewTLOL.event.rank.RankEvent;
import iyunu.NewTLOL.json.ActivityPayJson;
import iyunu.NewTLOL.manager.ActivityPayManager;
import iyunu.NewTLOL.model.payActivity.instance.PayActivityInfo;
import iyunu.NewTLOL.model.payActivity.instance.PayActivityList;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.util.Time;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ActivityNewProcessorCenter {

	private ScheduledThreadPoolExecutor activityNexExec; // 每日零点检查
	private Runnable runnable;
	private long start;

	/**
	 * 构造方法
	 * 
	 * @param payActivityList
	 */
	public ActivityNewProcessorCenter(final PayActivityList payActivityList) {
		start = (payActivityList.getFinish() - System.currentTimeMillis()) / Time.MILLISECOND;

		runnable = new Runnable() {

			@Override
			public void run() {
				switch (payActivityList.getType()) {
				case 3:

					for (PayActivityInfo payActivityInfo : ActivityPayJson.instance().get冲击狂人().values()) {
						RoleCard roleCard = RankEvent.ExpEvent.getHandler().getTopCards().get(0);
						ActivityPayManager.saveActivityNewState(payActivityList.getType(), payActivityInfo.getIndex(), roleCard.getId());
					}

					break;
				case 4:

					for (PayActivityInfo payActivityInfo : ActivityPayJson.instance().get最佳搭档().values()) {
						for (Long id : ActivityPayManager.activityNewRoles.get(payActivityList.getType()).get(payActivityInfo.getIndex())) {
							ActivityPayManager.saveActivityNewState(payActivityList.getType(), payActivityInfo.getIndex(), id);
						}
					}

					break;
				case 5:

					for (PayActivityInfo payActivityInfo : ActivityPayJson.instance().get装备铸造().values()) {
						for (Long id : ActivityPayManager.activityNewRoles.get(payActivityList.getType()).get(payActivityInfo.getIndex())) {
							ActivityPayManager.saveActivityNewState(payActivityList.getType(), payActivityInfo.getIndex(), id);
						}
					}

					break;
				case 6:

					ActivityPayManager.saveRoleIdByLevelRanking();
					for (PayActivityInfo payActivityInfo : ActivityPayJson.instance().get江湖等级榜().values()) {
						for (Long id : ActivityPayManager.activityNewRoles.get(payActivityList.getType()).get(payActivityInfo.getIndex())) {
							ActivityPayManager.saveActivityNewState(payActivityList.getType(), payActivityInfo.getIndex(), id);
						}
					}

					break;
				case 7:

					ActivityPayManager.saveRoleIdByDongTianRanking();
					for (PayActivityInfo payActivityInfo : ActivityPayJson.instance().get斗破洞天().values()) {
						for (Long id : ActivityPayManager.activityNewRoles.get(payActivityList.getType()).get(payActivityInfo.getIndex())) {
							ActivityPayManager.saveActivityNewState(payActivityList.getType(), payActivityInfo.getIndex(), id);
						}
					}

					break;
				case 8:

					for (PayActivityInfo payActivityInfo : ActivityPayJson.instance().get绝世神兵().values()) {
						for (Long id : ActivityPayManager.activityNewRoles.get(payActivityList.getType()).get(payActivityInfo.getIndex())) {
							ActivityPayManager.saveActivityNewState(payActivityList.getType(), payActivityInfo.getIndex(), id);
						}
					}

					break;
				case 9:

					ActivityPayManager.saveRoleIdByPowerRanking();
					for (PayActivityInfo payActivityInfo : ActivityPayJson.instance().get最强战力().values()) {
						for (Long id : ActivityPayManager.activityNewRoles.get(payActivityList.getType()).get(payActivityInfo.getIndex())) {
							ActivityPayManager.saveActivityNewState(payActivityList.getType(), payActivityInfo.getIndex(), id);
						}
					}

					break;
				case 10:

					for (PayActivityInfo payActivityInfo : ActivityPayJson.instance().get副本达人().values()) {
						for (Long id : ActivityPayManager.activityNewRoles.get(payActivityList.getType()).get(payActivityInfo.getIndex())) {
							ActivityPayManager.saveActivityNewState(payActivityList.getType(), payActivityInfo.getIndex(), id);
						}
					}

					break;
				case 11:

					for (PayActivityInfo payActivityInfo : ActivityPayJson.instance().get每日挑战().values()) {
						for (Long id : ActivityPayManager.activityNewRoles.get(payActivityList.getType()).get(payActivityInfo.getIndex())) {
							ActivityPayManager.saveActivityNewState(payActivityList.getType(), payActivityInfo.getIndex(), id);
						}
					}

					break;
				case 12:

					for (PayActivityInfo payActivityInfo : ActivityPayJson.instance().get铸造大匠().values()) {
						for (Long id : ActivityPayManager.activityNewRoles.get(payActivityList.getType()).get(payActivityInfo.getIndex())) {
							ActivityPayManager.saveActivityNewState(payActivityList.getType(), payActivityInfo.getIndex(), id);
						}
					}

					break;

				default:
					break;
				}

				stop();
			}
		};

	}

	public void init() {
		activityNexExec = new ScheduledThreadPoolExecutor(1);
		activityNexExec.scheduleAtFixedRate(runnable, start, Time.DAY_SECOND, TimeUnit.SECONDS);
	}

	public void stop() {
		if (activityNexExec != null) {
			activityNexExec.shutdown();
		}
	}

}
