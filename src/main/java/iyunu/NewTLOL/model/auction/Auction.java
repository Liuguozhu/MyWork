package iyunu.NewTLOL.model.auction;

import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.json.JsonTool;

public class Auction {

	protected long id; // 拍卖编号
	protected String title; //
	protected long ownerId; // 拍卖者编号
	protected String ownerName; // 拍卖者名称
	protected long timeout; // 到期时间
	protected int buyoutprice; // 一口价
	protected int tid;
	protected int uid;
	protected String srv; // 服务器名称
	protected EAuctionType type; // 跟踪类别
	protected int page;

	private int num; // 数量
	private Item item; // 物品
	private String itemStr;
	private long partnerId;
	private Partner partner;
	private int money;

	/**
	 * 编码成字符串
	 * 
	 * @return 字符串 //类型#item
	 */
	// @Override
	// public String toString() {
	// if (this.item != null) {
	// String str = this.item.getType().ordinal() + "#";
	// SerializeWriter out = new SerializeWriter();
	// JSONSerializer serializer = new JSONSerializer(out);
	// serializer.write(this.item);
	// str += serializer.toString();
	// return str;
	// } else {
	// return "";
	// }
	// }

	/**
	 * 解码成Item对象
	 * 
	 * @param str
	 *            字符串
	 * @return 格子对象
	 */
	public static Item decode(String str) {
		if ("".equals(str)) {
			return null;
		} else {
			String[] strs = str.split("#");
			
			EItem eItem = EItem.values()[Translate.stringToInt(strs[0])];
			Item item = JsonTool.decode(strs[1], eItem.getObject());
			return item;
		}
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	 * @return the ownerId
	 */
	public long getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId
	 *            the ownerId to set
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the ownerName
	 */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * @param ownerName
	 *            the ownerName to set
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * @return the timeout
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the buyoutprice
	 */
	public int getBuyoutprice() {
		return buyoutprice;
	}

	/**
	 * @param buyoutprice
	 *            the buyoutprice to set
	 */
	public void setBuyoutprice(int buyoutprice) {
		this.buyoutprice = buyoutprice;
	}

	/**
	 * @return the tid
	 */
	public int getTid() {
		return tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(int tid) {
		this.tid = tid;
	}

	/**
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the type
	 */
	public EAuctionType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EAuctionType type) {
		this.type = type;
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
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return the itemStr
	 */
	public String getItemStr() {
		return itemStr;
	}

	/**
	 * @param itemStr
	 *            the itemStr to set
	 */
	public void setItemStr(String itemStr) {
		this.itemStr = itemStr;
	}

	/**
	 * @return the partnerId
	 */
	public long getPartnerId() {
		return partnerId;
	}

	/**
	 * @param partnerId
	 *            the partnerId to set
	 */
	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
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

}
