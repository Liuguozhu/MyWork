package iyunu.NewTLOL.dao.impl;

import iyunu.NewTLOL.dao.iface.AuctionDao;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.role.Role;

import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class AuctionDaoImpl extends SqlMapClientDaoSupport implements AuctionDao {
	private StringRedisTemplate redisTemplate;

	/**
	 * @return the redisTemplate
	 */
	public StringRedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Auction> query() {
		return this.getSqlMapClientTemplate().queryForList("AuctionSQL.query", ServerManager.instance().getServer());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Auction> queryMyAuction(Role role) {
		return this.getSqlMapClientTemplate().queryForList("AuctionSQL.queryMyAuction", role);
	}

	@Override
	public void insert(Auction auction) {
		this.getSqlMapClientTemplate().insert("AuctionSQL.insert", auction);
	}

	@Override
	public void delete(Auction auction) {
		this.getSqlMapClientTemplate().delete("AuctionSQL.delete", auction);
	}

	// @Override
	// public int getGold(Role role) {
	// String key = RedisKey.getAuctionGold();
	// Double gold = redisTemplate.opsForZSet().score(key,
	// String.valueOf(role.getId()));
	// if (gold == null) {
	// return 0;
	// }
	// int goldInt = (int) gold.doubleValue();
	//
	// return goldInt;
	// }

	/**
	 * 添加，Zset相当于一个双层MAP， key就是外面的key，String.valueOf(role.getId()是内部的key,gold是值
	 * 
	 */
	// @Override
	// public void addGold(int gold, Role role) {
	// String key = RedisKey.getAuctionGold();
	// redisTemplate.opsForZSet().add(key, String.valueOf(role.getId()), gold);
	// }

	/**
	 * 取出金币就是删除 本条
	 */
	// @Override
	// public void minusGold(Role role) {
	// String key = RedisKey.getAuctionGold();
	// redisTemplate.opsForZSet().remove(key, String.valueOf(role.getId()));
	// }

}