package iyunu.NewTLOL.processor.gangFight;

import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.model.gang.FightApplyInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

/**
 * 帮派战战斗
 * 
 * @author SunHonglei
 * 
 */
public class RoundFightProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 1000; // 无任务时，周期性探测是否有新任务到达
	private FightApplyInfo fightApplyInfo1;
	private FightApplyInfo fightApplyInfo2;
	private long 战斗开始时间;
	private long 结束时间;

	public RoundFightProcessorCenter() {

	}

	public RoundFightProcessorCenter(FightApplyInfo fightApplyInfo1, FightApplyInfo fightApplyInfo2, long 战斗开始时间, long 结束时间) {
		this.fightApplyInfo1 = fightApplyInfo1;
		this.fightApplyInfo2 = fightApplyInfo2;
		this.战斗开始时间 = 战斗开始时间;
		this.结束时间 = 结束时间;
	}

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	public void run() {

		int winner = 0; // 0.都输，1.1赢，2.2赢

		while (process) {

			long startTime = System.currentTimeMillis(), spent = 0;

			if (startTime >= 战斗开始时间) {

				// 轮空
				if (fightApplyInfo1.isNull()) { // 理论不应该出现
					winner = 2; // 2赢
					shutdown(); // 关闭线程
					break;
				}
				if (fightApplyInfo2.isNull()) {
					winner = 1; // 1赢
					shutdown(); // 关闭线程
					break;
				}

				int size1 = fightApplyInfo1.getRoleNum();
				int size2 = fightApplyInfo2.getRoleNum();
				if (size1 <= 0 && size2 <= 0) {
					// 都输
					shutdown(); // 关闭线程
				} else if (size1 > 0 && size2 <= 0) {
					winner = 1; // 1赢
					shutdown(); // 关闭线程
				} else if (size1 <= 0 && size2 > 0) {
					winner = 2; // 2赢
					shutdown(); // 关闭线程
				} else {
					if (startTime >= 结束时间) { // 到时间
						// 根据条件判断输赢
						if (size1 > size2) {
							winner = 1; // 1赢
						} else if (size1 < size2) {
							winner = 2; // 2赢
						}
						shutdown(); // 关闭线程
					}
				}
			}

			spent = System.currentTimeMillis() - startTime;
			if (spent < PERIOD_WAIT) {
				synchronized (this) {
					try {
						wait(PERIOD_WAIT);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (winner == 1) { // =======================1赢==============================================
			fightApplyInfo1.addScore(3);
			long now = System.currentTimeMillis();
			fightApplyInfo1.setTime(now - 战斗开始时间);
			fightApplyInfo2.setTime(Util.matchZero(结束时间 - now));

			awardWinner(fightApplyInfo1);
			awardLoser(fightApplyInfo2);
		} else if (winner == 2) { // =======================2赢==============================================
			fightApplyInfo2.addScore(3);
			long now = System.currentTimeMillis();
			fightApplyInfo2.setTime(now - 战斗开始时间);
			fightApplyInfo1.setTime(Util.matchZero(结束时间 - now));

			awardWinner(fightApplyInfo2);
			awardLoser(fightApplyInfo1);

		} else { // ==============================================都输==============================================
			long now = System.currentTimeMillis();
			fightApplyInfo1.setTime(Util.matchZero(结束时间 - now));
			fightApplyInfo2.setTime(Util.matchZero(结束时间 - now));

			awardLoser(fightApplyInfo1);
			awardLoser(fightApplyInfo2);
		}

		// GangFightManager.roundShowSort();
		fightApplyInfo1.clearRoleIds();
		fightApplyInfo2.clearRoleIds();
		LogManager.info("【帮战循环赛（" + fightApplyInfo1.getGangName() + " VS " + fightApplyInfo2.getGangName() + "）战斗处理器】已关闭");
	}

	public void setFightApplyInfo1(FightApplyInfo fightApplyInfo1) {
		this.fightApplyInfo1 = fightApplyInfo1;
	}

	public void setFightApplyInfo2(FightApplyInfo fightApplyInfo2) {
		this.fightApplyInfo2 = fightApplyInfo2;
	}

	public void awardWinner(FightApplyInfo fightApplyInfo) {
		int winnerSize = fightApplyInfo.roleIds().size();
		for (Long roleId : fightApplyInfo.roleIds()) { // 胜利方

			int exp = 0;
			int gold = 0;
			int coin = 20000;
			int tribute = 150;
			// 发送通知
			if (ServerManager.instance().isOnline(roleId)) { // 玩家在线
				Role role = ServerManager.instance().getOnlinePlayer(roleId);
				if (role.getLevel() < 36) {
					exp = (int) ((role.getLevel() * 1000 + 10000) * 2 / 3f);
					gold = role.getLevel() * 400 + 4000;
				} else {
					exp = (int) ((role.getLevel() * 15000 - 450000) * 2 / 3f);
					gold = role.getLevel() * 6000 - 180000;
				}

				GangMessage.refreshGangFightAward(role, "循环赛", 0, winnerSize, winnerSize * 6, exp, gold, 600, tribute, coin); // 发送通知
			} else { // 玩家不在线
				RoleCard roleCard = RoleManager.getRoleCardByRoleId(roleId);
				if (roleCard.getLevel() < 36) {
					exp = (int) ((roleCard.getLevel() * 1000 + 10000) * 2 / 3f);
					gold = roleCard.getLevel() * 400 + 4000;
				} else {
					exp = (int) ((roleCard.getLevel() * 15000 - 450000) * 2 / 3f);
					gold = roleCard.getLevel() * 6000 - 180000;
				}
			}

			// 发送邮件
			MailServer.send(roleId, "帮派战奖励", "在刚刚结束的帮派战中您的奋勇杀敌得到我们的一致赞赏，现特发以下奖励以资鼓励，希望您能再接再厉，再创辉煌", null, gold, coin, 0, exp, tribute, null);
		}
	}

	public void awardLoser(FightApplyInfo fightApplyInfo) {
		int loserSize = fightApplyInfo.roleIds().size();
		for (Long roleId : fightApplyInfo.roleIds()) { // 失败方

			int exp = 0;
			int gold = 0;
			int coin = 10000;
			int tribute = 75;

			if (ServerManager.instance().isOnline(roleId)) { // 玩家在线
				Role role = ServerManager.instance().getOnlinePlayer(roleId);
				if (role.getLevel() < 36) {
					exp = (int) ((role.getLevel() * 1000 + 10000) * 2 / 3f);
					gold = role.getLevel() * 400 + 4000;
				} else {
					exp = (int) ((role.getLevel() * 15000 - 450000) * 2 / 3f);
					gold = role.getLevel() * 6000 - 180000;
				}
				GangMessage.refreshGangFightAward(role, "循环赛", 1, loserSize, loserSize * 6, exp, gold, 450, tribute, coin);

			} else {
				RoleCard roleCard = RoleManager.getRoleCardByRoleId(roleId);
				if (roleCard.getLevel() < 36) {
					exp = (int) ((roleCard.getLevel() * 1000 + 10000) * 2 / 3f);
					gold = roleCard.getLevel() * 400 + 4000;
				} else {
					exp = (int) ((roleCard.getLevel() * 15000 - 450000) * 2 / 3f);
					gold = roleCard.getLevel() * 6000 - 180000;
				}

			}

			// 发送邮件
			MailServer.send(roleId, "帮派战奖励", "在刚刚结束的帮派战中您的奋勇杀敌得到我们的一致赞赏，现特发以下奖励以资鼓励，希望您能再接再厉，再创辉煌", null, gold, coin, 0, exp, tribute, null);
		}
	}
}
