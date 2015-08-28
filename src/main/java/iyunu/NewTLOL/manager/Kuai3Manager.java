package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.json.Kuai3Json;
import iyunu.NewTLOL.model.kuai3.Kuai3Enum;
import iyunu.NewTLOL.model.kuai3.Kuai3Model;
import iyunu.NewTLOL.model.kuai3.Kuai3Word;
import iyunu.NewTLOL.model.kuai3.Record;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Kuai3Manager {

	public ScheduledThreadPoolExecutor lottery = new ScheduledThreadPoolExecutor(1);
	public static final int OFTEN = 60 * 5;// 每5分钟开一次奖
	public static final int SINGLECOIN = 1000;// 每单注 银两
	public int resultNow;
	public int turns = 0;
	public List<Record> record = new ArrayList<Record>();

	/**
	 * 私有构造方法
	 */
	private Kuai3Manager() {
	}

	private static Kuai3Manager instance = new Kuai3Manager();
	/** 下注者下注记录 */
	private List<Kuai3Model> kuai3Roles = new ArrayList<Kuai3Model>();

	private List<Kuai3Word> awards = new ArrayList<Kuai3Word>();

	private long timeout = 0;// 下次开奖时间

	private Map<Long, Long> awardPlus = new HashMap<>();

	/**
	 * @return the kuai3Roles
	 */
	public List<Kuai3Model> getKuai3Roles() {
		return kuai3Roles;
	}

	/**
	 * 获取拍卖行管理实例
	 * 
	 * @return 拍卖行管理实例
	 */
	public static Kuai3Manager instance() {
		return instance;
	}

	public void start() {

		lottery.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				if (ServerManager.isRunning()) {
					sendAward();
				}
			}

		}, 0, OFTEN, TimeUnit.SECONDS);
		LogManager.info("【快3】加载完成");
	}

	/**
	 * 开奖结果计算
	 * 
	 * @return int[] 开奖结果
	 */
	public int[] getResults() {

		int[] result = new int[3];
		result[0] = Util.getRandom(6) + 1;
		result[1] = Util.getRandom(6) + 1;
		result[2] = Util.getRandom(6) + 1;
		Arrays.sort(result);
		resultNow = result[0] * 100 + result[1] * 10 + result[2];

		LogManager.kuai3(turns, resultNow); // 开奖结果日志
		turns++;
		if (record.size() < 10) {
			Record recordele = new Record();
			recordele.setResult(resultNow);
			recordele.setTurn(turns);
			record.add(recordele);
		} else {
			record.remove(0);
			Record recordele = new Record();
			recordele.setResult(resultNow);
			recordele.setTurn(turns);
			record.add(recordele);
		}
		return result;
	}

	/**
	 * 中奖计算
	 * 
	 * @param k
	 *            玩家单注彩票
	 */
	public void win(Kuai3Model k, int plus) {
		try {
			Kuai3Word kuai3Word = new Kuai3Word();
			kuai3Word.setCoin(SINGLECOIN * k.getTimes() * Kuai3Json.instance().getAward(k.getType(), plus != 0 ? plus : 0));
			kuai3Word.setRoleId(k.getRoleId());
			kuai3Word.setWord(k.getType().name() + "，所选号码：" + (Kuai3Enum.三连号通选.equals(k.getType()) ? "三连号通选" : k.getNum()) + "，开奖号码" + resultNow + "，倍数：" + (k.getTimes() > 1 ? k.getTimes() : 1)
					+ "，计：" + kuai3Word.getCoin() + "银两");
			kuai3Word.setWin(1);
			awards.add(kuai3Word);
			// 加入中奖的和值里
			if (awardPlus.containsKey(k.getRoleId())) {
				awardPlus.put(k.getRoleId(), awardPlus.get(k.getRoleId()) + kuai3Word.getCoin());
			} else {
				awardPlus.put(k.getRoleId(), (long) kuai3Word.getCoin());
			}
			LogManager.kuai3Result(turns, resultNow, 1, k.getRoleId(), k.getType().ordinal(), k.getNum(), k.getTimes() > 1 ? k.getTimes() : 1, kuai3Word.getCoin()); // 中奖日志
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 未中奖计算
	 * 
	 * @param k
	 *            玩家单注彩票
	 */
	public void lose(Kuai3Model k) {
		try {

			Kuai3Word kuai3Word = new Kuai3Word();
			kuai3Word.setCoin(0);
			kuai3Word.setRoleId(k.getRoleId());
			kuai3Word.setWord(k.getType().name() + "，所选号码：" + (Kuai3Enum.三连号通选.equals(k.getType()) ? "三连号通选" : k.getNum()) + "，开奖号码" + resultNow + "，倍数：" + (k.getTimes() > 1 ? k.getTimes() : 1)
					+ "，未中奖，祝你游戏愉快，下次一定中！");
			kuai3Word.setWin(0);
			LogManager.kuai3Result(turns, resultNow, 0, k.getRoleId(), k.getType().ordinal(), k.getNum(), k.getTimes() > 1 ? k.getTimes() : 1, 0); // 中奖日志
			awards.add(kuai3Word);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算得奖者，并发送奖励
	 */
	public void sendAward() {
		try {

			int[] result = getResults();

			for (Kuai3Model k : kuai3Roles) {

				if (Kuai3Enum.三不同号.equals(k.getType())) {
					if (k.getNum() == resultNow) {
						win(k, 0);
					} else {
						lose(k);
					}
				} else if (Kuai3Enum.三连号通选.equals(k.getType())) {
					if (result[2] - result[1] == 1 && result[1] - result[0] == 1) {
						win(k, 0);
					} else {
						lose(k);
					}
				} else if (Kuai3Enum.二同号复选.equals(k.getType())) {

					if (k.getNum() == (result[0] * 10 + result[1]) || k.getNum() == (result[1] * 10 + result[2])) {
						win(k, 0);
					} else {
						lose(k);
					}

				} else if (Kuai3Enum.三同号单选.equals(k.getType())) {
					if (k.getNum() == resultNow) {
						win(k, 0);

					} else {
						lose(k);
					}
				} else {
					int plus = result[0] + result[1] + result[2];
					if (k.getNum() == plus) {
						win(k, plus);
					} else {
						lose(k);
					}
				}
			}

			// 此处邮件发奖
			for (Kuai3Word k : awards) {
				MailServer.send(k.getRoleId(), (k.getWin() == 1 ? "猜猜看结果(赢)" : "猜猜看结果(输)"), k.getWord(), null, 0, k.getCoin(), 0, 0, null);
			}
			awards.clear();
			kuai3Roles.clear();
			// 世界公告本次结果
			// ChatMessage.sendChat(new Chat(EChat.system, 0, "系统", "猜猜看第" +
			// getTurns() + "期结果：" + getResultNow()));

			BulletinManager.instance().addBulletinChat("猜猜看第" + getTurns() + "期结果：" + StringControl.grn(getResultNow()) + getQianSan());

			awardPlus.clear();
			// 设定本次开奖时间
			setTimeout(System.currentTimeMillis() + OFTEN * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 猜猜看前几名的提示
	 * 
	 * @return
	 */
	public String getQianSan() {
		List<Long> listMap = new ArrayList<Long>(awardPlus.keySet());
		if (listMap.size() > 0) {
			Collections.sort(listMap, new Comparator<Long>() {
				@Override
				public int compare(Long o1, Long o2) {
					if (awardPlus.get(o1) > awardPlus.get(o2)) {
						return 1;
					} else if (awardPlus.get(o1) < awardPlus.get(o2)) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			StringBuffer sb = new StringBuffer();

			sb.append("获得前");
			if (listMap.size() < 3) {
				sb.append(listMap.size());
			} else {
				sb.append(3);
			}
			sb.append("名的是：");
			Iterator<Long> it = listMap.iterator();
			int num = 0;
			while (it.hasNext() && num < 3) {
				long roleId = it.next();
				num++;
				sb.append(StringControl.grn("".equals(RoleManager.getNameByRoleId(roleId)) ? "无" : RoleManager.getNameByRoleId(roleId)) + "，");
			}
			sb.append("恭喜" + (num == 1 ? "他" : "他们") + "博得头彩，可喜可贺。");
			listMap.clear();
			return "，" + sb.toString();
		} else {
			return "";
		}
	}

	/**
	 * @return the turns
	 */
	public int getTurns() {
		return turns;
	}

	/**
	 * @return the result
	 */
	public int getResultNow() {
		return resultNow;
	}

	/**
	 * @param resultNow
	 *            the resultNow to set
	 */
	public void setResultNow(int resultNow) {
		this.resultNow = resultNow;
	}

	/**
	 * @return the awards
	 */
	public List<Kuai3Word> getAwards() {
		return awards;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return (int) ((timeout - System.currentTimeMillis()) / 1000);
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the record
	 */
	public List<Record> getRecord() {
		return record;
	}

}
