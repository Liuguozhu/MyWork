package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.json.BloodJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.blood.Blood;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.blood.BloodServer;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.util.StringControl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class BloodManager {
	private static Random RANDOM = new Random();
	private static BloodManager instance = new BloodManager();
	/** 血战总MAP */
	public Map<Long, Blood> bloodMap = new ConcurrentHashMap<>();
	/** 红方roleID的集合 */
	public List<Long> hong = new ArrayList<>();
	/** 蓝方roleID的集合 */
	public List<Long> lan = new ArrayList<>();
	/** 退出血战的集合， 不再让他们再次参战 ，为了方便判断玩家blood的标识，把临时退出的放在这里，停止后清除 */
	public List<Long> quitList = new ArrayList<>();
	/** 首杀 */
	public long shouSha = 0;
	/** 首杀守护神 */
	public long shouShaMonster = 0;
	/** 连杀 */
	public long hKill = 0;
	/** 红蓝列表显示的最大数目 */
	public static final int MAX_BLOOD = 15;
	/** 杀人加分 */
	public static final int MARK = 10;
	/** 蓝方总战力 */
	public static long lanPower = 0;
	/** 红方总战力 */
	public static long hongPower = 0;

	public static BloodManager instance() {
		return instance;
	}

	private BloodManager() {
	}

	public void sort(List<Long> dui) {
		Collections.sort(dui, new Comparator<Long>() {
			@Override
			public int compare(Long o1, Long o2) {
				Blood blood1 = bloodMap.get(o1);
				Blood blood2 = bloodMap.get(o2);
				if (blood1.getMark() > blood2.getMark()) {
					return -1;
				} else if (blood1.getMark() < blood2.getMark()) {
					return 1;
				} else {
					return 0;
				}
			}
		});
	}

	/**
	 * 获得一个随机阵营
	 * 
	 * @return 阵营
	 */
	public void randomCamp(Role role) {
		if (role != null) {
			if (lanPower < hongPower) {
				Blood blood = new Blood();
				blood.setId(role.getId());
				blood.setLevel(role.getLevel());
				blood.setNick(role.getNick());
				blood.setVocation(role.getVocation());
				blood.setBlood(2);
				role.setBlood(2);
				lan.add(role.getId());
				bloodMap.put(role.getId(), blood);
				lanPower = lanPower + role.getPower();
			} else if (lanPower > hongPower) {
				Blood blood = new Blood();
				blood.setId(role.getId());
				blood.setLevel(role.getLevel());
				blood.setNick(role.getNick());
				blood.setVocation(role.getVocation());
				blood.setBlood(1);
				role.setBlood(1);
				hong.add(role.getId());
				bloodMap.put(role.getId(), blood);
				hongPower = hongPower + role.getPower();
			} else {
				Blood blood = new Blood();
				blood.setId(role.getId());
				blood.setLevel(role.getLevel());
				blood.setNick(role.getNick());
				blood.setVocation(role.getVocation());

				if (Math.random() > 0.5) {
					blood.setBlood(1);
					role.setBlood(1);
					hong.add(role.getId());
					hongPower = hongPower + role.getPower();
				} else {
					blood.setBlood(2);
					role.setBlood(2);
					lan.add(role.getId());
					lanPower = lanPower + role.getPower();
				}
				bloodMap.put(role.getId(), blood);
			}
			SendMessage.sendBlood(role);
		}
	}

	/**
	 * @param blood1
	 *            第一个人的血战状态
	 * @param blood2
	 *            第二个人的血战状态
	 * @return 状态不一样 ，返回true
	 */
	public boolean checkBlood(int blood1, int blood2) {
		if (blood1 == 1) {
			if (blood2 == 2) {
				return true;
			} else {
				return false;
			}
		}
		if (blood1 == 2) {
			if (blood2 == 1) {
				return true;
			} else {
				return false;
			}
		}
		if (blood1 == 0) {
			if (blood2 == 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 杀人杀怪加积分，并判断首杀事件,连杀事件
	 * 
	 * @param type
	 *            1 杀人 2杀怪 3被杀
	 * @param id
	 * @param blood
	 * @param mark
	 */
	public synchronized void addElement(int type, Role role, int mark) {
		if (bloodMap.containsKey(role.getId())) {
			Blood b = bloodMap.get(role.getId());
			b.setMark(b.getMark() + mark);
			if (type == 1) {
				mark = mark / MARK;
				// 首杀人
				if (shouSha == 0) {
					shouSha = role.getId();
					ChatMessage.sendChat(new Chat(EChat.system, 0, "血战", StringControl.yel("玩家【" + StringControl.grn(b.getNick()) + "】" + "第一滴血")));
				}
				// 连杀数
				b.setMultiKill(b.getMultiKill() + mark);
				// 最高连杀
				if (b.getMultiKill() > b.getHkill()) {
					b.setHkill(b.getMultiKill());
				}
				// 杀人数
				b.setKillNum(b.getKillNum() + mark);
				// 连杀超越
				if (hKill != 0) {
					if (b.getHkill() > bloodMap.get(hKill).getHkill()) {
						hKill = b.getId();
					}
				} else {
					// 连杀第一次赋值
					hKill = b.getId();
				}

				ChatMessage.sendChat(new Chat(EChat.system, 0, "血战", StringControl.yel("玩家【" + StringControl.grn(b.getNick()) + "】" + "在 " + role.getMapInfo().getBaseMap().getName() + " "
						+ StringControl.grn(b.getMultiKill()) + "连杀，" + BloodJson.instance().getKillWord().get(RANDOM.nextInt(BloodJson.instance().getKillWord().size())).getWord())));

			} else if (type == 2) {
				// 首杀怪
				if (shouShaMonster == 0) {
					shouShaMonster = role.getId();
					ChatMessage.sendChat(new Chat(EChat.system, 0, "血战", StringControl.yel("玩家【" + StringControl.grn(b.getNick()) + "】" + "血刃先锋，战胜守护怪")));
				}

			} else {
				// 被杀清空连杀
				b.setMultiKill(0);
				// 添加被杀
				b.setDead(b.getDead() + 1);
			}
		}
	}

	/**
	 * 血战发奖
	 */
	public void sendWard() {

		long hongMark = BloodServer.getHongMark();
		long lanMark = BloodServer.getLanMark();
		List<Long> hongRest = new ArrayList<>();
		for (Long rid : hong) {
			Blood blood = bloodMap.get(rid);
			if (blood.getMark() > 4) {
				hongRest.add(rid);
			}
		}
		List<Long> lanRest = new ArrayList<>();
		for (Long rid : lan) {
			Blood blood = bloodMap.get(rid);
			if (blood.getMark() > 4) {
				lanRest.add(rid);
			}
		}

		if (lanMark == hongMark) {
			String content = StringControl.yel("血战圆满结束， 双方平局");
			BulletinManager.instance().addBulletinRock(content, 1);
		}
		sort(lanRest);
		sort(hongRest);
		// 蓝色发奖励
		for (int i = 0; i < lanRest.size(); i++) {
			Map<Item, Integer> itemIds = new HashMap<Item, Integer>();
			Item itemBlue = ItemJson.instance().getItem(21043);
			Item itemGreen = ItemJson.instance().getItem(21042);

			if (i == 0) {
				if (lanMark > hongMark) {
					itemIds.put(itemBlue, 5);
					MailServer.send(lanRest.get(i), "血战奖励", "再刚刚结束的血战中，您勇冠三军，获得了以下奖励，请您查收，希望您再接再厉，再创辉煌~！", itemIds, 0, 0, 0, 0, null);
					String content = StringControl.yel("血战圆满结束，" + StringControl.grn(bloodMap.get(lanRest.get(i)).getNick()) + "是本次血战胜方积分榜第一名");
					BulletinManager.instance().addBulletinRock(content, 1);
				} else {
					itemIds.put(itemBlue, 4);
					MailServer.send(lanRest.get(i), "血战基础奖励", "参与血战活动并取得了本阵营的第一名，恭喜您。", itemIds, 0, 0, 0, 0, null);
				}

			} else if (i == 1) {
				if (lanMark > hongMark) {
					itemIds.put(itemBlue, 4);
					MailServer.send(lanRest.get(i), "血战奖励", "再刚刚结束的血战中，您勇冠三军，获得了以下奖励，请您查收，希望您再接再厉，再创辉煌~！", itemIds, 0, 0, 0, 0, null);
				} else {
					itemIds.put(itemBlue, 3);
					MailServer.send(lanRest.get(i), "血战基础奖励", "参与血战活动并取得了本阵营的第二名，恭喜您。", itemIds, 0, 0, 0, 0, null);
				}
			} else if (i == 2) {
				if (lanMark > hongMark) {
					itemIds.put(itemBlue, 3);
					MailServer.send(lanRest.get(i), "血战奖励", "再刚刚结束的血战中，您勇冠三军，获得了以下奖励，请您查收，希望您再接再厉，再创辉煌~！", itemIds, 0, 0, 0, 0, null);
				} else {
					itemIds.put(itemBlue, 2);
					MailServer.send(lanRest.get(i), "血战基础奖励", "参与血战活动并取得了本阵营的第三名，恭喜您。", itemIds, 0, 0, 0, 0, null);
				}
			} else if (i <= 9 && i >= 3) {
				if (lanMark > hongMark) {
					itemIds.put(itemBlue, 2);
					MailServer.send(lanRest.get(i), "血战奖励", "再刚刚结束的血战中，您勇冠三军，获得了以下奖励，请您查收，希望您再接再厉，再创辉煌~！", itemIds, 0, 0, 0, 0, null);
				} else {
					itemIds.put(itemBlue, 1);
					MailServer.send(lanRest.get(i), "血战基础奖励", "参与血战活动并取得了本阵营的前十名，恭喜您。", itemIds, 0, 0, 0, 0, null);
				}
			} else {
				if (lanMark > hongMark) {
					itemIds.put(itemBlue, 1);
					itemIds.put(itemGreen, 1);
					MailServer.send(lanRest.get(i), "血战奖励", "再刚刚结束的血战中，您勇冠三军，获得了以下奖励，请您查收，希望您再接再厉，再创辉煌~！", itemIds, 0, 0, 0, 0, null);
				} else {
					itemIds.put(itemGreen, 1);
					MailServer.send(lanRest.get(i), "血战基础奖励", "参与血战活动并获得了好成绩，恭喜您。", itemIds, 0, 0, 0, 0, null);
				}
			}
		}
		// 红色发奖励
		for (int i = 0; i < hongRest.size(); i++) {
			Map<Item, Integer> itemIds = new HashMap<Item, Integer>();
			Item itemBlue = ItemJson.instance().getItem(21043);
			Item itemGreen = ItemJson.instance().getItem(21042);
			if (i == 0) {
				if (hongMark > lanMark) {
					itemIds.put(itemBlue, 5);
					MailServer.send(hongRest.get(i), "血战奖励", "再刚刚结束的血战中，您勇冠三军，获得了以下奖励，请您查收，希望您再接再厉，再创辉煌~！", itemIds, 0, 0, 0, 0, null);
					String content = StringControl.yel("血战圆满结束，" + StringControl.grn(bloodMap.get(hongRest.get(i)).getNick()) + "是本次血战胜方积分榜第一名");
					BulletinManager.instance().addBulletinRock(content, 1);
				} else {
					itemIds.put(itemBlue, 4);
					MailServer.send(hongRest.get(i), "血战基础奖励", "参与血战活动并取得了本阵营的第一名，恭喜您。", itemIds, 0, 0, 0, 0, null);
				}
			} else if (i == 1) {
				if (hongMark > lanMark) {
					itemIds.put(itemBlue, 4);
					MailServer.send(hongRest.get(i), "血战奖励", "再刚刚结束的血战中，您勇冠三军，获得了以下奖励，请您查收，希望您再接再厉，再创辉煌~！", itemIds, 0, 0, 0, 0, null);
				} else {
					itemIds.put(itemBlue, 3);
					MailServer.send(hongRest.get(i), "血战基础奖励", "参与血战活动并取得了本阵营的第二名，恭喜您。", itemIds, 0, 0, 0, 0, null);
				}
			} else if (i == 2) {
				if (hongMark > lanMark) {
					itemIds.put(itemBlue, 3);
					MailServer.send(hongRest.get(i), "血战奖励", "再刚刚结束的血战中，您勇冠三军，获得了以下奖励，请您查收，希望您再接再厉，再创辉煌~！", itemIds, 0, 0, 0, 0, null);
				} else {
					itemIds.put(itemBlue, 2);
					MailServer.send(hongRest.get(i), "血战基础奖励", "参与血战活动并取得了本阵营的第三名，恭喜您。", itemIds, 0, 0, 0, 0, null);
				}
			} else if (i <= 9 && i >= 3) {
				if (hongMark > lanMark) {
					itemIds.put(itemBlue, 2);
					MailServer.send(hongRest.get(i), "血战奖励", "再刚刚结束的血战中，您勇冠三军，获得了以下奖励，请您查收，希望您再接再厉，再创辉煌~！", itemIds, 0, 0, 0, 0, null);
				} else {
					itemIds.put(itemBlue, 1);
					MailServer.send(hongRest.get(i), "血战基础奖励", "参与血战活动并取得了本阵营的前十名，恭喜您。", itemIds, 0, 0, 0, 0, null);
				}
			} else {
				if (hongMark > lanMark) {
					itemIds.put(itemBlue, 1);
					itemIds.put(itemGreen, 1);
					MailServer.send(hongRest.get(i), "血战奖励", "再刚刚结束的血战中，您勇冠三军，获得了以下奖励，请您查收，希望您再接再厉，再创辉煌~！", itemIds, 0, 0, 0, 0, null);
				} else {
					itemIds.put(itemGreen, 1);
					MailServer.send(hongRest.get(i), "血战基础奖励", "参与血战活动并获得了好成绩，恭喜您。", itemIds, 0, 0, 0, 0, null);
				}
			}
		}

	}

	/**
	 * @return the bloodMap
	 */
	public Map<Long, Blood> getBloodMap() {
		return bloodMap;
	}

	public void addBloodMap(Role role, int mark) {
		bloodMap.get(role.getId()).setMark(bloodMap.get(role.getId()).getMark() + mark);
	}

	/**
	 * @return the hong
	 */
	public List<Long> getHong() {
		return hong;
	}

	/**
	 * @param hong
	 *            the hong to set
	 */
	public void setHong(List<Long> hong) {
		this.hong = hong;
	}

	/**
	 * @return the lan
	 */
	public List<Long> getLan() {
		return lan;
	}

	/**
	 * @param lan
	 *            the lan to set
	 */
	public void setLan(List<Long> lan) {
		this.lan = lan;
	}

	/**
	 * @return the quitList
	 */
	public List<Long> getQuitList() {
		return quitList;
	}

	/**
	 * @param quitList
	 *            the quitList to set
	 */
	public void setQuitList(List<Long> quitList) {
		this.quitList = quitList;
	}

}