package iyunu.NewTLOL.processor.gangFight;

import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.model.gang.FightApplyInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Util;

/**
 * 帮派战战斗
 * 
 * @author SunHonglei
 * 
 */
public class OutFightProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 1000; // 无任务时，周期性探测是否有新任务到达
	private FightApplyInfo fightApplyInfo1;
	private FightApplyInfo fightApplyInfo2;
	private long 战斗开始时间;
	private long 结束时间;
	private int match; // 第几场（4.8进4，3.半决赛，2.决赛）

	public OutFightProcessorCenter() {

	}

	public OutFightProcessorCenter(FightApplyInfo fightApplyInfo1, FightApplyInfo fightApplyInfo2, long 战斗开始时间, long 结束时间, int match) {
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

		int winner = 1; // 0.都输，1.1赢，2.2赢

		while (process) {

			long startTime = System.currentTimeMillis(), spent = 0;

			if (startTime >= 战斗开始时间) {

				// 轮空
				if (fightApplyInfo1 == null || fightApplyInfo1.isNull()) {
					winner = 2; // 2赢
					shutdown(); // 关闭线程
					break;
				}
				if (fightApplyInfo2 == null || fightApplyInfo2.isNull()) {
					winner = 1; // 1赢
					shutdown(); // 关闭线程
					break;
				}

				int size1 = fightApplyInfo1.getRoleNum();
				int size2 = fightApplyInfo2.getRoleNum();
				if (size1 <= 0 && size2 <= 0) {
					if (Util.probable()) {
						winner = 1; // 1赢
					} else {
						winner = 2; // 2赢
					}

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
						} else {

							if (Util.probable()) {
								winner = 1; // 1赢
							} else {
								winner = 2; // 2赢
							}

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

		if (winner == 1) { // ======================================1赢======================================
			awardWinner(fightApplyInfo1);
			awardLoser(fightApplyInfo2);
		} else { // ======================================2赢======================================
			awardWinner(fightApplyInfo2);
			awardLoser(fightApplyInfo1);
		}

		fightApplyInfo1.clearRoleIds();
		fightApplyInfo2.clearRoleIds();
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
			int multiple = 12;
			int gangExp = 900;

			if (ServerManager.instance().isOnline(roleId)) { // 玩家在线

				Role role = ServerManager.instance().getOnlinePlayer(roleId);

				if (match == 2) {
					gangExp = 1800;
					multiple = 24;

					if (role.getLevel() < 36) {
						exp = (int) ((role.getLevel() * 1000 + 10000) * 8 / 3f);
						gold = role.getLevel() * 1600 + 16000;
					} else {
						exp = (int) ((role.getLevel() * 15000 - 450000) * 8 / 3f);
						gold = role.getLevel() * 24000 - 720000;
					}
				} else {
					if (role.getLevel() < 36) {
						exp = (int) ((role.getLevel() * 1000 + 10000) * 4 / 3f);
						gold = role.getLevel() * 800 + 8000;
					} else {
						exp = (int) ((role.getLevel() * 15000 - 450000) * 4 / 3f);
						gold = role.getLevel() * 12000 - 360000;
					}
				}

				// 发送通知
				GangMessage.refreshGangFightAward(role, "淘汰赛", 0, winnerSize, winnerSize * multiple, exp, gold, gangExp, tribute, coin);

			} else {
				RoleCard roleCard = RoleManager.getRoleCardByRoleId(roleId);

				if (match == 2) {
					gangExp = 1800;
					multiple = 24;

					if (roleCard.getLevel() < 36) {
						exp = (int) ((roleCard.getLevel() * 1000 + 10000) * 8 / 3f);
						gold = roleCard.getLevel() * 1600 + 16000;
					} else {
						exp = (int) ((roleCard.getLevel() * 15000 - 450000) * 8 / 3f);
						gold = roleCard.getLevel() * 24000 - 720000;
					}
				} else {
					if (roleCard.getLevel() < 36) {
						exp = (int) ((roleCard.getLevel() * 1000 + 10000) * 4 / 3f);
						gold = roleCard.getLevel() * 800 + 8000;
					} else {
						exp = (int) ((roleCard.getLevel() * 15000 - 450000) * 4 / 3f);
						gold = roleCard.getLevel() * 12000 - 360000;
					}
				}
			}

			// 发送邮件
			MailServer.send(roleId, "帮派战奖励", "在刚刚结束的帮派战中您的奋勇杀敌得到我们的一致赞赏，现特发以下奖励以资鼓励，希望您能再接再厉，再创辉煌", null, gold, coin, 0, exp, tribute, null);

		}

		switch (fightApplyInfo.getIndex()) {
		case 1:
		case 2:
			fightApplyInfo.setIndex(9);
			GangFightManager.淘汰赛对阵表.put(9, fightApplyInfo);
			break;
		case 3:
		case 4:
			fightApplyInfo.setIndex(10);
			GangFightManager.淘汰赛对阵表.put(10, fightApplyInfo);
			break;
		case 5:
		case 6:
			fightApplyInfo.setIndex(11);
			GangFightManager.淘汰赛对阵表.put(11, fightApplyInfo);
			break;
		case 7:
		case 8:
			fightApplyInfo.setIndex(12);
			GangFightManager.淘汰赛对阵表.put(12, fightApplyInfo);
			break;
		case 9:
		case 11:
			fightApplyInfo.setIndex(13);
			GangFightManager.淘汰赛对阵表.put(13, fightApplyInfo);
			break;
		case 10:
		case 12:
			fightApplyInfo.setIndex(14);
			GangFightManager.淘汰赛对阵表.put(14, fightApplyInfo);
			break;
		case 13:
		case 14:
//			fightApplyInfo.addWin();
//			if (fightApplyInfo.getWinNumber() >= 2) {
				fightApplyInfo.setIndex(15);
				GangFightManager.淘汰赛对阵表.put(15, fightApplyInfo);

				BulletinManager.instance().addBulletinRock("帮派战决赛圆满结束，恭喜" + StringControl.red(fightApplyInfo.getGangName()) + "帮派加冕成功，成为本周" + StringControl.red("冠军帮！"), 1);
				GangManager.instance().changeChampion(fightApplyInfo.getGangId()); // 转变冠军榜
				GangFightManager.CHAMPION = fightApplyInfo.getGangName();
//			}
			break;
		default:
			break;
		}
	}

	public void awardLoser(FightApplyInfo fightApplyInfo) {
		int loserSize = fightApplyInfo.roleIds().size();
		for (Long roleId : fightApplyInfo.roleIds()) { // 失败方

			int exp = 0;
			int gold = 0;
			int coin = 10000;
			int tribute = 75;
			int multiple = 12;
			int gangExp = 600;

			if (ServerManager.instance().isOnline(roleId)) { // 玩家在线
				Role role = ServerManager.instance().getOnlinePlayer(roleId);

				if (match == 2) {
					gangExp = 1800;
					multiple = 24;

					if (role.getLevel() < 36) {
						exp = (int) ((role.getLevel() * 1000 + 10000) * 8 / 3f);
						gold = role.getLevel() * 1600 + 16000;
					} else {
						exp = (int) ((role.getLevel() * 15000 - 450000) * 8 / 3f);
						gold = role.getLevel() * 24000 - 720000;
					}
				} else {
					if (role.getLevel() < 36) {
						exp = (int) ((role.getLevel() * 1000 + 10000) * 4 / 3f);
						gold = role.getLevel() * 800 + 8000;
					} else {
						exp = (int) ((role.getLevel() * 15000 - 450000) * 4 / 3f);
						gold = role.getLevel() * 12000 - 360000;
					}
				}

				// 发送通知
				GangMessage.refreshGangFightAward(role, "淘汰赛", 1, loserSize, loserSize * multiple, exp, gold, gangExp, tribute, coin);

			} else { // 玩家不在线

				RoleCard roleCard = RoleManager.getRoleCardByRoleId(roleId);

				if (match == 2) {
					gangExp = 1800;
					multiple = 24;

					if (roleCard.getLevel() < 36) {
						exp = (int) ((roleCard.getLevel() * 1000 + 10000) * 8 / 3f);
						gold = roleCard.getLevel() * 1600 + 16000;
					} else {
						exp = (int) ((roleCard.getLevel() * 15000 - 450000) * 8 / 3f);
						gold = roleCard.getLevel() * 24000 - 720000;
					}
				} else {
					if (roleCard.getLevel() < 36) {
						exp = (int) ((roleCard.getLevel() * 1000 + 10000) * 4 / 3f);
						gold = roleCard.getLevel() * 800 + 8000;
					} else {
						exp = (int) ((roleCard.getLevel() * 15000 - 450000) * 4 / 3f);
						gold = roleCard.getLevel() * 12000 - 360000;
					}
				}
			}

			// 发送邮件
			MailServer.send(roleId, "帮派战奖励", "在刚刚结束的帮派战中您的奋勇杀敌得到我们的一致赞赏，现特发以下奖励以资鼓励，希望您能再接再厉，再创辉煌", null, gold, coin, 0, exp, tribute, null);
		}
	}
}
