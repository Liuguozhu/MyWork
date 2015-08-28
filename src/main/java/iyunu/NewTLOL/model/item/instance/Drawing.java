package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.manager.BattleManager;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.MonsterManager;
import iyunu.NewTLOL.message.FightMessage;
import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.model.activity.DrawingSite;
import iyunu.NewTLOL.model.activity.instance.DrawingAward;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.EMonsterOnMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.model.monster.DropMonster;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.monster.instance.MonsterGroup;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.processor.BattleProcessorCenter;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.battle.BattleServer;
import iyunu.NewTLOL.server.map.MapServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 藏宝图
 * 
 * @author SunHonglei
 * 
 */
public class Drawing extends Item {

	private int mapId;
	private int x;
	private int y;

	@Override
	public Drawing copy() {
		Drawing item = new Drawing();
		Item.init(item, this);

		DrawingSite drawingSite = ActivityJson.instance().randomSite();
		item.setMapId(drawingSite.getMapId());
		item.setX(drawingSite.getX());
		item.setY(drawingSite.getY());

		return item;
	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {

		if (MapServer.isArrive(role, this.getMapId(), this.getX(), this.getY())) {

			DrawingAward drawingAward = ActivityJson.instance().getDrawingAwards(value);

			switch (drawingAward.awardType()) {
			case 1:
				if (drawingAward.getReward().equals(EOpen.all)) {

					if (role.getBag().isFull(drawingAward.getItems().size())) {
						return false;
					}

					for (MonsterDropItem dropItem : drawingAward.getItems()) {
						if (Util.probable(dropItem.getProbability())) {
							Item item = ItemJson.instance().getItem(dropItem.getItemId());
							if (item != null && num > 0) {
								item.setIsDeal(dropItem.getIsBind());
								BagServer.add(role, item, num * dropItem.getNum(), cellsMap, EItemGet.openTreasure);
							}
						}
					}

				} else if (drawingAward.getReward().equals(EOpen.one)) {

					if (role.getBag().isFull(num)) {
						return false;
					}

					for (int i = 0; i < num; i++) {
						int probable = 0;
						int finalRate = Util.getRandom(10000);
						for (MonsterDropItem dropItem : drawingAward.getItems()) {
							probable += dropItem.getProbability();
							if (probable > finalRate) {
								Item item = ItemJson.instance().getItem(dropItem.getItemId());
								if (item != null) {
									item.setIsDeal(dropItem.getIsBind());
									BagServer.add(role, item, dropItem.getNum(), cellsMap, EItemGet.openTreasure);
									break;
								}
							}
						}
					}
				}

				break;
			case 2:
				int sum = 0;
				int finalRate = Util.getRandom(10000);

				Iterator<Entry<Integer, Integer>> it = drawingAward.getFight().entrySet().iterator();
				while (it.hasNext()) {
					Entry<Integer, Integer> entry = it.next();
					sum += entry.getValue();
					if (finalRate < sum) {
						MonsterGroup monsterGroup = MonsterJson.instance().getMonsterGroup(entry.getKey());
						BattleInfo battleInfo = BattleServer.preBattle(role, monsterGroup, 0, role.getMapInfo().getBaseMap(), false);

						BattleManager.instance().addBattleProcessor(new BattleProcessorCenter(battleInfo));

						if (role.getTeam() == null) { // 单人遇怪
							role.setBattle(true);
							role.setBattleId(battleInfo.getBattleId()); // 设置战斗编号
							FightMessage.sendEncounterResult(battleInfo, role);
						} else { // 组队遇怪
							for (Role member : role.getTeam().getMember()) {
								member.setBattle(true);
								member.setBattleId(battleInfo.getBattleId()); // 设置战斗编号
							}
							FightMessage.sendEncounterResult(battleInfo);
						}
						break;
					}
				}

				break;
			case 3:
				int dropSum = 0;
				int dropFinalRate = Util.getRandom(10000);
				for (DropMonster dropMonster : drawingAward.getMonsters()) {
					dropSum += dropMonster.getProbability();
					if (dropFinalRate < dropSum) {
						BaseMap baseMap = MapManager.instance().getMapById(dropMonster.getMapId());
						List<MonsterOnMap> monsterOnMaps = new ArrayList<MonsterOnMap>();
						int monsterSum = dropMonster.getNum();
						MonsterGroup monsterGroup = MonsterJson.instance().getMonsterGroup(dropMonster.getMonsterGroupId());
						for (int i = 0; i < monsterSum; i++) {
							DrawingSite drawingSite = ActivityJson.instance().randomSite(baseMap.getId());
							if (drawingSite != null) {
								MonsterOnMap monsterOnMap = new MonsterOnMap(baseMap.getId(), drawingSite.getX(), drawingSite.getY(), dropMonster.getMonsterGroupId(), EMonsterOnMap.drawing);
								MonsterManager.instance().addMonsterOnMap(monsterOnMap, Time.MILLISECOND * 60 * 10); // 添加至怪物定时器
								monsterOnMaps.add(monsterOnMap);
								baseMap.getMonsters().put(monsterOnMap.getUid(), monsterOnMap);
							}
						}
						MapMessage.sendMonsterOnMap(baseMap, monsterOnMaps);
						String content = StringControl.grn(role.getNick()) + "在挖宝时，不慎释放了" + StringControl.pur(monsterGroup.getName()) + "至" + StringControl.red(baseMap.getName()) + "，请各位勇士前往击杀！";
						BulletinManager.instance().addBulletinRock(content, 1, baseMap.getId());
						break;
					}
				}
				break;
			default:
				break;
			}

		} else {
			MapMessage.sendTargetSite(role, mapId, x, y);
			return false;
		}

		return true;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}

	/**
	 * @return the mapId
	 */
	public int getMapId() {
		return mapId;
	}

	/**
	 * @param mapId
	 *            the mapId to set
	 */
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}
}