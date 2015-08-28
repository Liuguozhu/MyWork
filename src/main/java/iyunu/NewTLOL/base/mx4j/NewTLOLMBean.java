package iyunu.NewTLOL.base.mx4j;

public interface NewTLOLMBean {

	/**
	 * 执行命令
	 * 
	 * @param command
	 */
	void execute(String command);

	/**
	 * 关闭服务器
	 */
	void stopService(String pwd);

	/**
	 * 重新加载JSON资源
	 */
	void reloadJson();

	/**
	 * 重新加载非法文字
	 */
	void reloadIlllegalWord();

	void sendMail(long roleId);

	/**
	 * @function 发送充值信息
	 * @author LuoSR
	 * @param roleId
	 *            角色编号
	 * @param rmb
	 *            充值金额
	 * @param orderId
	 *            订单号
	 * @param state
	 *            充值状态
	 * @date 2014年4月2日
	 */
	void sendPayInfo(long roleId, int rmb, String orderId, String state);

	/**
	 * 重新加载公告
	 */
	void reloadBulletin();

	/**
	 * 重新加载登录公告
	 */
	void reloadLogonBulletin();

	/**
	 * 地图上添加物品
	 * 
	 * @param mapId
	 * @param x
	 * @param y
	 * @param itemId
	 */
	void addItemOnMap(int mapId, int x, int y, int itemId);

	/**
	 * 地图上添加怪物
	 * 
	 * @param mapId
	 * @param x
	 * @param y
	 * @param monsterGroup
	 */
	void addMonsterOnMap(int mapId, int x, int y, int monsterGroup);

	/**
	 * 日志控制器
	 * 
	 * @param open
	 *            开启
	 */
	void logManager(boolean open);

	/**
	 * 取得在线人数
	 * 
	 * @param s
	 *            无用的字段
	 * @return
	 */
	String getOnlineNumber(String s);

	/**
	 * 发送邮件
	 * 
	 * @param ids
	 *            玩家ID集合
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param items
	 *            物品ID集合
	 * @param gold
	 *            金币
	 * @param money
	 *            元宝
	 */
	void sendMail(String ids, String title, String content, String items, int gold, int coin, int money, int exp);

	/**
	 * 世界喊话
	 * 
	 * @param content
	 *            喊话内容
	 */
	void sendWordTalk(String content);

	/**
	 * 发送滚屏公告
	 * 
	 * @param content
	 *            公告内容
	 * @param num
	 *            播放次数
	 */
	void sendRockBulletin(String content, int num);

	void reloadConf(int password);

	void reloadCompensate(int password);

	void reloadCdKey(int password);

	/**
	 * 全部下架拍卖行物品
	 */
	void allDownAuction(int pwd);

	/**
	 * 重置充值活动
	 * 
	 * @param pwd
	 *            密码
	 */
	void reloadPayActivity(int pwd);

	/**
	 * @param title
	 * @param content
	 * @param items
	 *            itemId,num,bind ; itemId,num,bind ;
	 * @param gold
	 * @param coin
	 * @param money
	 * @param exp
	 */
	void allOnlinesendMail(String title, String content, String items, int gold, int coin, int money, int exp);

	/**
	 * 加载积分榜时间
	 */
	String reloadJiFengBangTime();
	/**
	 * 加载日常任务
	 */
	void reloadDaily(int pwd);
}
