package iyunu.NewTLOL.model.qiancengta.res;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.qiancengta.instance.QiancengtaInfo;
import iyunu.NewTLOL.util.Translate;

public class QiancengtaInfoRes {
	private int id; // 层数编号
	private int groupId; // 怪物组合
	private String des; // 掉落说明
	private int exp; // 掉落经验
	private int gold; // 掉落金钱
	private String photo; // 头像
	private String items; // 掉落物品

	public QiancengtaInfo toQiancengtaInfo() {
		QiancengtaInfo qiancengtaInfo = new QiancengtaInfo();
		qiancengtaInfo.setId(id);
		qiancengtaInfo.setGroupId(groupId);
		qiancengtaInfo.setDes(des);
		qiancengtaInfo.setExp(exp);
		qiancengtaInfo.setGold(gold);
		qiancengtaInfo.setPhoto(photo);
		String[] str = items.split(";");

		for (String string : str) {
			String[] itemStr = string.split(":");
			qiancengtaInfo.getItems().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]), Translate.stringToInt(itemStr[3])));
		}

		return qiancengtaInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

}
