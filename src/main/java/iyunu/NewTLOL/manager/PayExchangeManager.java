package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.activity.PayExchange;
import iyunu.NewTLOL.model.activity.PayExchangeTime;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.processor.activity.PayExchangeProcessorCenter;
import iyunu.NewTLOL.redis.RedisCenter;
import iyunu.NewTLOL.redis.RedisPayExchange;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.service.iface.payActivity.PayActivityService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 充值积分 活动
 * 
 * @author fhy
 * 
 */
public class PayExchangeManager {

	private static PayExchangeManager instance = new PayExchangeManager();

	public static boolean FLAG = false;
	public static long STARTTIME = 0;
	public static long ENDTIME = 0;

	// public static int LEAST_MARK = 2000;

	private PayExchangeManager() {
	}

	public static PayExchangeManager instance() {
		return instance;
	}

	public Map<Long, Integer> payExchangeMap = new HashMap<>();
	public List<Long> list = new ArrayList<>();

	/**
	 * 加载
	 */
	public void load() {
		PayActivityService payActivityService = Spring.instance().getBean("payActivityService", PayActivityService.class);
		PayExchangeTime p = payActivityService.queryPayExchangeTime();

		if (p == null || p.getStartTime() == null) {
			STARTTIME = ENDTIME = System.currentTimeMillis() - 1000;
		} else {
			STARTTIME = p.getStartTime().getTime();
			ENDTIME = p.getEndTime().getTime();
		}
		PayExchangeProcessorCenter pp = new PayExchangeProcessorCenter();
		pp.start();
		long time = System.currentTimeMillis();
		if (time > STARTTIME && time < ENDTIME) {
			RedisPayExchange redisPayExchange = Spring.instance().getBean("redisPayExchange", RedisPayExchange.class);
			payExchangeMap = redisPayExchange.getPayExchange();
			if (payExchangeMap != null) {
				Iterator<Entry<Long, Integer>> it = payExchangeMap.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Long, Integer> entry = it.next();
					// if (entry.getValue() > LEAST_MARK) {
					list.add(entry.getKey());
					// }
				}
				sort();
			} else {
				payExchangeMap = new HashMap<Long, Integer>();
			}
		}
	}

	/**
	 * 对充值的人进行排行
	 */
	public void sort() {
		Collections.sort(list, new Comparator<Long>() {
			@Override
			public int compare(Long o1, Long o2) {
				if (payExchangeMap.get(o1) < payExchangeMap.get(o2)) {
					return 1;
				} else if (payExchangeMap.get(o1) > payExchangeMap.get(o2)) {
					return -1;
				} else {
					return 0;
				}
			}
		});
	}

	/**
	 * 加积分
	 * 
	 * @param roleId
	 *            角色ID
	 * @param mark
	 *            积分
	 */
	public void add(long roleId, int mark) {
		if (roleId <= 0 || mark <= 0) {
			return;
		}
		if (payExchangeMap.containsKey(roleId)) {
			payExchangeMap.put(roleId, payExchangeMap.get(roleId) + mark);
		} else {
			payExchangeMap.put(roleId, mark);
		}
		// if (payExchangeMap.get(roleId) > LEAST_MARK) {
		if (!list.contains(roleId)) {
			list.add(roleId);
		}
		sort();
		// }
	}

	/**
	 * 发送奖励
	 */
	public void sendAward() {
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				if (payExchangeMap.get(list.get(i)) >= 8000) {
					awardInfo(list.get(i), 1);
				} else {
					MailServer.send(list.get(i), "奇珍异宝", "在奇珍异宝活动中，您未满足第一名的条件，积分不足8000。", null, 0, 0, 0, 0, null);
				}
			} else if (i == 1) {
				awardInfo(list.get(i), 2);
			} else if (i == 2) {
				awardInfo(list.get(i), 3);
			} else if (i > 2 && i < 10) {
				awardInfo(list.get(i), 4);
			} else if (i > 9 && i < 20) {
				awardInfo(list.get(i), 5);
			}
		}
		payExchangeMap.clear();
		list.clear();
		FLAG = false;
		// 关闭积分榜图标
		for (Long roleId : ServerManager.instance().getIdChannelPair().keySet()) {
			Role role = ServerManager.instance().getOnlinePlayer(roleId);
			SendMessage.refreshPayExchange(role, 0);
		}
		// 删除积分榜保存
		RedisPayExchange redisPayExchange = Spring.instance().getBean("redisPayExchange", RedisPayExchange.class);
		redisPayExchange.delPayExchange();
	}

	public void awardInfo(long roleId, int mingCi) {
		try {

			Map<Item, Integer> itemIds = new HashMap<Item, Integer>();
			PayExchange m = ActivityJson.instance().getPayExchange().get(mingCi);
			Partner p = null;
			Partner newPartner = null;
			p = PartnerJson.instance().getNewPartner(m.getPartnerIndex());
			if (p != null) {
				RedisCenter redisCenter = Spring.instance().getBean("redisCenter", RedisCenter.class);
				long partnerId = redisCenter.getPartnerId();

				newPartner = p.copy();
				PartnerServer.countPotential(newPartner); // 计算资质
				newPartner.setId(partnerId);
				newPartner.setHp(newPartner.getHpMax());
			}
			List<MonsterDropItem> md = m.getItemId();
			for (MonsterDropItem monsterDropItem : md) {
				Item item = ItemJson.instance().getItem(monsterDropItem.getItemId());
				item.setIsDeal(monsterDropItem.getIsBind());
				itemIds.put(item, monsterDropItem.getNum());
			}
			MailServer.send(roleId, "奇珍异宝", "请查收。", itemIds, 0, 0, 0, 0, newPartner);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the payExchangeMap
	 */
	public Map<Long, Integer> getPayExchangeMap() {
		return payExchangeMap;
	}

	/**
	 * @param payExchangeMap
	 *            the payExchangeMap to set
	 */
	public void setPayExchangeMap(Map<Long, Integer> payExchangeMap) {
		this.payExchangeMap = payExchangeMap;
	}

	/**
	 * @return the list
	 */
	public List<Long> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<Long> list) {
		this.list = list;
	}

}
