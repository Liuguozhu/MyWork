package iyunu.NewTLOL.model.mail;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.util.json.JsonTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class Mail {

	private long mailId; // 编号
	private String title; // 标题
	private String content; // 内容
	private int read;// 0未读，1已读
	private long receiveId; // 接收者编号
	private String sendName;// 发件人名字
	private int gold;// 邮件里的金币
	private int coin;// 银两
	private Map<Integer, Integer> items = new HashMap<Integer, Integer>(4); // 第一个Integer为物品ID，第二个Integer为物品数量
	private Map<Item, Integer> newItems = new HashMap<Item, Integer>(4); // 第一个Integer为物品ID，第二个Integer为物品数量
	private Date time; // 发送时间
	private String srv; // 服务器
	private int money; // 元宝
	private Partner partner; // 伙伴
	private int sortId;// 排序ID，给客户端排序用

	private int exp; // 经验
	private int tribute; // 帮贡

	public Mail() {

	}

	/**
	 * 创建新邮件
	 * 
	 * @param mailId
	 *            邮件编号
	 * @param title
	 *            邮件标题
	 * @param content
	 *            邮件内容
	 * @param senderId
	 *            发件人编号
	 * @param receiveId
	 *            收件人编号
	 * @param sendName
	 *            发件人名称
	 */
	public Mail(long mailId, String title, String content, long receiveId, String sendName) {
		this.mailId = mailId;
		this.title = title;
		this.content = content;
		this.read = 0;
		this.receiveId = receiveId;
		this.sendName = sendName;
		this.time = new Date();
		this.srv = ServerManager.instance().getServer();
	}

	public void change() {
		if (newItems == null || newItems.isEmpty()) {
			if (items != null && !items.isEmpty()) {
				Iterator<Entry<Integer, Integer>> it = items.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Integer, Integer> entry = it.next();
					Item item = ItemJson.instance().getItem(entry.getKey());
					if (item != null) {
						newItems.put(item, entry.getValue());
					}
				}
			}
		}
		items.clear();
	}

	public void clear() {
		gold = 0;
		coin = 0;
		money = 0;
		exp = 0;
		items.clear();
		newItems.clear();
	}

	/**
	 * Mail toString 方法
	 * 
	 * @return Mail 自定义的string
	 */
	@Override
	public String toString() {
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.write(this);
		return serializer.toString();
	}

	/**
	 * 解析Mail字符串
	 * 
	 * @param mailStr
	 *            序列化字符串
	 * @return Mail战斗对象
	 */
	public static Mail parseMail(String mailStr) {
		StringBuilder sb = new StringBuilder();
		sb.append(mailStr);
		Mail mail = JsonTool.decode(sb.toString(), Mail.class);
		return mail;
	}

	/**
	 * @return the mailId
	 */
	public long getMailId() {
		return mailId;
	}

	/**
	 * @param mailId
	 *            the mailId to set
	 */
	public void setMailId(long mailId) {
		this.mailId = mailId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the read
	 */
	public int getRead() {
		return read;
	}

	/**
	 * @param read
	 *            the read to set
	 */
	public void setRead(int read) {
		this.read = read;
	}

	/**
	 * @return the receiveId
	 */
	public long getReceiveId() {
		return receiveId;
	}

	/**
	 * @param receiveId
	 *            the receiveId to set
	 */
	public void setReceiveId(long receiveId) {
		this.receiveId = receiveId;
	}

	/**
	 * @return the gold
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * @param gold
	 *            the gold to set
	 */
	public void setGold(int gold) {
		this.gold = gold;
	}

	/**
	 * @return the sendName
	 */
	public String getSendName() {
		return sendName;
	}

	/**
	 * @param sendName
	 *            the sendName to set
	 */
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	/**
	 * @return the newItems
	 */
	public Map<Item, Integer> getNewItems() {
		return newItems;
	}

	/**
	 * @param newItems
	 *            the newItems to set
	 */
	public void setNewItems(Map<Item, Integer> newItems) {
		this.newItems = newItems;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the srv
	 */
	public String getSrv() {
		return srv;
	}

	/**
	 * @param srv
	 *            the srv to set
	 */
	public void setSrv(String srv) {
		this.srv = srv;
	}

	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * @return the partner
	 */
	public Partner getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the partner to set
	 */
	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	/**
	 * @return the sortId
	 */
	public int getSortId() {
		return sortId;
	}

	/**
	 * @param sortId
	 *            the sortId to set
	 */
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	/**
	 * @return the items
	 */
	public Map<Integer, Integer> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(Map<Integer, Integer> items) {
		this.items = items;
	}

	/**
	 * @return the coin
	 */
	public int getCoin() {
		return coin;
	}

	/**
	 * @param coin
	 *            the coin to set
	 */
	public void setCoin(int coin) {
		this.coin = coin;
	}

	/**
	 * @return the exp
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * @param exp
	 *            the exp to set
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}

	/**
	 * @return the tribute
	 */
	public int getTribute() {
		return tribute;
	}

	/**
	 * @param tribute
	 *            the tribute to set
	 */
	public void setTribute(int tribute) {
		this.tribute = tribute;
	}

}
