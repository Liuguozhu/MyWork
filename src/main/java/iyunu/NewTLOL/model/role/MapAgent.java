package iyunu.NewTLOL.model.role;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.gang.GangJobTitle;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class MapAgent {

	private Role role;
	/** 角色id及状态， 0.新增，1.移动，2.删除 */
	private ConcurrentHashMap<Long, Integer> nearby = new ConcurrentHashMap<Long, Integer>(); // 周围玩家
	private long lastUpdate = 0; // 上次更新时间
	private final static long UPDATE_PERIOD = 800; // 更新周期

	public MapAgent(Role role) {
		this.role = role;
	}

	public MapAgent() {

	}

	/**
	 * 刷新周围玩家
	 */
	public final void flushNearby() {
		long timeMillis = System.currentTimeMillis();

		if (lastUpdate <= 0) {
			lastUpdate = timeMillis;
			return;
		}

		if (timeMillis - lastUpdate < UPDATE_PERIOD)
			return;

		sendMessage();
	}

	/**
	 * 发送消息
	 */
	public final void sendMessage() {
		refreshNearby(); // 更新附近角色
		lastUpdate = System.currentTimeMillis();

		// if (nearby.isEmpty())
		// return;

		LlpMessage allLlpMessage = null;
		LlpMessage siteLlpMessage = null;
		LlpMessage removeLlpMessage = null;
		try {
			if (role != null && role.getChannel() != null) {
				allLlpMessage = LlpJava.instance().getMessage("s_refreshNearbyAll");
				siteLlpMessage = LlpJava.instance().getMessage("s_refreshNearbySite");
				removeLlpMessage = LlpJava.instance().getMessage("s_refreshNearbyRemove");

				boolean isAll = false;
				boolean isSite = false;
				boolean isRemove = false;

				Iterator<Entry<Long, Integer>> it = nearby.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Long, Integer> entry = it.next();

					if (entry.getKey() == role.getId()) {
						it.remove();
						continue;
					}

					if (entry.getValue() == 0) {
						LlpMessage message = allLlpMessage.write("aroundAllInfoList");
						Role role = ServerManager.instance().getOnlinePlayer(entry.getKey());
						if (role != null) {
							message.write("roleId", role.getId());
							message.write("x", role.getMapInfo().getX());
							message.write("y", role.getMapInfo().getY());
							message.write("name", role.getNick());
							message.write("figure", role.getFigure());
							if (role.getGang() != null) {
								message.write("gangName", role.getGang().getName());
								message.write("gangJob", role.getGang().getJobTitle(role).getDes());
							} else {
								message.write("gangName", "");
								message.write("gangJob", GangJobTitle.NULL.getDes());
							}

							message.write("level", role.getLevel());
							message.write("vocation", role.getVocation().getName());
							int isLeader = 0;
							if (role.getTeam() != null && role.getTeam().getLeader().getId() == role.getId()) {
								isLeader = 1;
							}
							message.write("isLeader", isLeader);
							message.write("arriveX", role.getMapInfo().getArriveX());
							message.write("arriveY", role.getMapInfo().getArriveY());
							message.write("vip", role.getVip().getLevel().ordinal());
							message.write("isBattle", Util.falseOrTrue(role.isBattle()));
							message.write("blood", role.getBlood());

							Equip equip = role.getEquipments().get(EEquip.shenbing);
							if (equip == null) {
								message.write("shenbingId", 0);
							} else {
								message.write("shenbingId", equip.getId());
							}

							Equip shizhuang = role.getEquipments().get(EEquip.shizhuang);
							if (shizhuang == null) {
								message.write("shizhuangId", 0);
							} else {
								message.write("shizhuangId", 1);
							}

							isAll = true;
						} else {
							removeLlpMessage.write("roleIds", entry.getKey());
							it.remove();
							isRemove = true;
						}
					} else if (entry.getValue() == 1) {
						Role role = ServerManager.instance().getOnlinePlayer(entry.getKey());
						if (role != null) {
							LlpMessage message = siteLlpMessage.write("aroundSiteInfoList");
							message.write("roleId", role.getId());
							message.write("x", role.getMapInfo().getX());
							message.write("y", role.getMapInfo().getY());
							message.write("arriveX", role.getMapInfo().getArriveX());
							message.write("arriveY", role.getMapInfo().getArriveY());
							isSite = true;
						} else {
							removeLlpMessage.write("roleIds", entry.getKey());
							it.remove();
							isRemove = true;
						}
					} else {
						removeLlpMessage.write("roleIds", entry.getKey());
						it.remove();
						isRemove = true;
					}
				}

				if (isAll) {
					role.getChannel().write(allLlpMessage);
				}
				if (isSite) {
					role.getChannel().write(siteLlpMessage);
				}
				if (isRemove) {
					role.getChannel().write(removeLlpMessage);
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送周围玩家信息");
			e.printStackTrace();
		} finally {
			if (allLlpMessage != null) {
				allLlpMessage.destory();
			}
			if (siteLlpMessage != null) {
				siteLlpMessage.destory();
			}
			if (removeLlpMessage != null) {
				removeLlpMessage.destory();
			}
		}
	}

	/**
	 * 更新周围玩家列表
	 */
	public final void refreshNearby() {
		Set<Long> nearby = role.getMapInfo().getBaseMap().allInMap();
		// 0.新增，1.移动，2.删除
		for (long roleId : this.nearby.keySet()) {
			this.nearby.put(roleId, 2);
		}

		for (long rid : nearby) {
			if (this.nearby.containsKey(rid)) {
				this.nearby.put(rid, 1);
			} else {
				this.nearby.put(rid, 0);
			}
		}
	}

	public void clear() {
		nearby.clear();
	}

	public boolean isNearBy(long roleId) {
		return nearby.containsKey(roleId);
	}
}
