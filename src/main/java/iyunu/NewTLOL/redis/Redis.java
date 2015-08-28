package iyunu.NewTLOL.redis;

public abstract class Redis {

	protected RedisLogon redisLogon;
	protected RedisCenter redisCenter;

	/**
	 * @return the redisLogon
	 */
	public RedisLogon getRedisLogon() {
		return redisLogon;
	}

	/**
	 * @param redisLogon
	 *            the redisLogon to set
	 */
	public void setRedisLogon(RedisLogon redisLogon) {
		this.redisLogon = redisLogon;
	}

	/**
	 * @return the redisCenter
	 */
	public RedisCenter getRedisCenter() {
		return redisCenter;
	}

	/**
	 * @param redisCenter
	 *            the redisCenter to set
	 */
	public void setRedisCenter(RedisCenter redisCenter) {
		this.redisCenter = redisCenter;
	}

}
