package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.compensate.Compensate;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.service.iface.compensate.CompensateService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class CompensateManager {

	/**
	 * 私有构造方法
	 */
	private CompensateManager() {

	}

	private static CompensateManager instance = new CompensateManager();
	private Compensate compensate = null;

	public static CompensateManager intance() {
		return instance;
	}

	public void init() {
		compensate = null;
		CompensateService compensateService = Spring.instance().getBean("compensateService", CompensateService.class);
		compensate = compensateService.query();
		if (compensate != null) {
			compensate.init();
		}
	}

	/**
	 * 玩家补偿
	 * 
	 * @param role
	 *            角色对象
	 */
	public void compensate(Role role) {
		if (compensate != null && compensate.getVersion() > role.getCompensate() && role.getLevel() >= compensate.getLevel() && role.getDate().before(compensate.getStart())) {
			role.setCompensate(compensate.getVersion());
			long now = System.currentTimeMillis();

			if (compensate.getStartTime() < now && now < compensate.getEndTime()) {
				Map<Integer, Integer> itemIds = (Map<Integer, Integer>) compensate.getItemIds();
				Map<Item, Integer> map = new HashMap<Item, Integer>();
				Iterator<Entry<Integer, Integer>> it = itemIds.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Integer, Integer> entry = it.next();
					Item item = ItemJson.instance().getItem(entry.getKey());
					if (item != null) {
						map.put(item, entry.getValue());
					}
				}

				MailServer.send(role.getId(), compensate.getTitle(), compensate.getContent(), map, compensate.getGold(), compensate.getCoin(), compensate.getMoney(), compensate.getExp(), null);
			}
		}
	}

	/**
	 * @return the compensate
	 */
	public Compensate getCompensate() {
		return compensate;
	}

}
