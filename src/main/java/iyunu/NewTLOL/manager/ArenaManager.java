package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.arena.ArenaModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ArenaManager {
	private static ArenaManager instance = new ArenaManager();
	/** 名次MAP */
	public Map<Integer, Long> roleRankMap = new ConcurrentHashMap<>();
	/** 竞技场总MAP */
	public Map<Long, ArenaModel> arenaMap = new ConcurrentHashMap<>();

	public static ArenaManager instance() {
		return instance;
	}

	private ArenaManager() {
	}

	/**
	 * @return the roleRankMap
	 */
	public Map<Integer, Long> getRoleRankMap() {
		return roleRankMap;
	}

	/**
	 * @param roleRankMap
	 *            the roleRankMap to set
	 */
	public void setRoleRankMap(Map<Integer, Long> roleRankMap) {
		this.roleRankMap = roleRankMap;
	}

	/**
	 * @return the arenaMap
	 */
	public Map<Long, ArenaModel> getArenaMap() {
		return arenaMap;
	}

	/**
	 * @param arenaMap
	 *            the arenaMap to set
	 */
	public void setArenaMap(Map<Long, ArenaModel> arenaMap) {
		this.arenaMap = arenaMap;
	}

}