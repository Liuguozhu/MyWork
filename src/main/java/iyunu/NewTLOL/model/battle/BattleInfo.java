package iyunu.NewTLOL.model.battle;

import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.battle.amin.BufferAminInfo;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.model.monster.instance.MonsterGroup;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BattleInfo {

	private boolean isAuto = false; // 是否自动战斗
	private int battleType = 0; // 类型，0.正常战斗，1.剧情一，2.剧情二，-1.自动战斗
	private int awardType = 0; // 奖励类型（1队长下线没有奖励）
	private int type = 0; // 战斗类型（0.野外遇怪，1.NPC战斗，2.指定唯一编号杀怪，3.试炼，4.PVP,5.千层塔）
	private BaseMap baseMap; // 战斗发生地图
	private MonsterOnMap monsterOnMap;
	private int taskId; // 任务编号
	private long battleId = 0l;
	private int turn = 1;
	private int leftNum = 0;
	private int rightNum = 0;
	private Map<Long, Integer> roleToBattleCharacter = new HashMap<Long, Integer>();
	private Map<Integer, BattleCharacter> left = new HashMap<Integer, BattleCharacter>();
	private Map<Integer, BattleCharacter> right = new HashMap<Integer, BattleCharacter>();
	private Map<Integer, BattleCharacter> all = new HashMap<Integer, BattleCharacter>();
	private List<BattleCharacter> order = new ArrayList<BattleCharacter>();
	private Role leftLeader;
	private Role rightLeader;
	/** 战斗等待时间 **/
	private long waitingTime = 0l;
	/** 提交命令人数计数器 **/
	private int counter = 0;
	/** 战斗人数 **/
	private int number = 0;
	private EBattleReuslt winner = EBattleReuslt.none; // 战斗结果
	/** 战斗动画集合 **/
	private List<BattleAminInfo> battleAminInfos = new ArrayList<BattleAminInfo>();
	/** BUFF动画集合 **/
	private Map<EBattleBuffType, ArrayList<BufferAminInfo>> buffInfos = new HashMap<EBattleBuffType, ArrayList<BufferAminInfo>>(); // <buff类型，中BUFF人集合>
	private Comparator<BattleCharacter> comparator = new Comparator<BattleCharacter>() {
		@Override
		public int compare(BattleCharacter o1, BattleCharacter o2) {
			if (o1.getPropertyBattle().getSpeed() < o2.getPropertyBattle().getSpeed()) {
				return 1;
			} else if (o1.getPropertyBattle().getSpeed() > o2.getPropertyBattle().getSpeed()) {
				return -1;
			} else {
				return 0;
			}
		}
	};

	private int trialsId; // 试炼Id
	private int currentFloorId; // 千层塔ID
	MonsterGroup monsterGroup = new MonsterGroup();// 怪物组合

	/**
	 * 构造方法
	 * 
	 * @param number
	 *            战斗人数
	 */
	public BattleInfo(int number, int type, boolean isAuto) {
		this.number = number;
		this.type = type;
		this.isAuto = isAuto;
		buffInfos.put(EBattleBuffType.b5, new ArrayList<BufferAminInfo>());
		buffInfos.put(EBattleBuffType.b6, new ArrayList<BufferAminInfo>());
		buffInfos.put(EBattleBuffType.b16, new ArrayList<BufferAminInfo>());
	}

	/**
	 * 清空BUFF
	 */
	public void clearBuffAmin() {
		buffInfos.get(EBattleBuffType.b5).clear();
		buffInfos.get(EBattleBuffType.b6).clear();
		buffInfos.get(EBattleBuffType.b16).clear();
	}

	/**
	 * 清除战斗等待时间
	 */
	public void clearTime() {
		waitingTime = 0l;
	}

	/**
	 * 添加buff
	 * 
	 * @param battleBuffType
	 *            buff类型
	 * @param bufferAminInfo
	 *            战斗BUFF对象
	 */
	public void addBuffInfo(EBattleBuffType battleBuffType, BufferAminInfo bufferAminInfo) {
		buffInfos.get(battleBuffType).add(bufferAminInfo);
	}

	/**
	 * 清除战斗动画
	 */
	public void cleanBattleAmin() {
		battleAminInfos.clear();
	}

	/**
	 * 检查时间
	 */
	public void checkTime() {
		if (number <= counter) {
			clearTime();
		}
	}

	/**
	 * 计数器加一
	 */
	public void counterIncrease() {
		if (number > counter) {
			counter++;
			if (number <= counter) {
				clearTime();
			}
		}
	}

	/**
	 * 清除计数器
	 */
	public void counterClear() {
		counter = 0;
	}

	public void decreaseNumber() {
		number = number - 1;
		// System.out.println("battleId=" + battleId);
	}

	public int getNumber() {
		return number;
	}

	/**
	 * 添加战斗动画
	 * 
	 * @param battleAminInfo
	 *            战斗动画
	 */
	public void addBattleAmin(BattleAminInfo battleAminInfo) {
		battleAminInfos.add(battleAminInfo);
	}

	/**
	 * 战斗是否结束
	 * 
	 * @param direction
	 *            方位
	 * @return 战斗结束
	 */
	public boolean isFinish(EDirection direction) {

		if (direction.equals(EDirection.right)) {
			for (BattleCharacter character : right.values()) {
				if (character.getCharacter().getType() != 1 && !character.isDead() && !character.isEscape()) {
					return false;
				}
			}
			winner = EBattleReuslt.left;
			return true;
		} else {
			for (BattleCharacter character : left.values()) {
				if (character.getCharacter().getType() != 1 && !character.isDead() && !character.isEscape()) {
					return false;
				}
			}
			winner = EBattleReuslt.right;
			return true;
		}

	}

	/**
	 * 战斗是否结束
	 * 
	 * @return 战斗结束
	 */
	public boolean isFinish() {
		winner = EBattleReuslt.right;
		for (BattleCharacter character : left.values()) {
			if (!character.isDead() && !character.isEscape()) {
				winner = EBattleReuslt.left;
				break;
			}
		}

		if (winner.equals(EBattleReuslt.right)) {
			return true;
		}

		for (BattleCharacter character : right.values()) {
			if (!character.isDead() && !character.isEscape()) {
				winner = EBattleReuslt.none;
				break;
			}
		}

		if (winner.equals(EBattleReuslt.left)) {
			return true;
		}

		return false;
	}

	/**
	 * 随机获取一个战斗对象
	 * 
	 * @param list
	 *            索引集合
	 * @return 战斗对象
	 */
	private BattleCharacter getRandom(List<Integer> list) {
		if (list.size() <= 0) {
			return null;
		}
		int index = Util.getRandom(list.size());
		BattleCharacter battleCharacter = all.get(list.get(index));
		if (battleCharacter == null || battleCharacter.isDead() || battleCharacter.isEscape()) {
			list.remove(index);
			return getRandom(list);
		}
		return battleCharacter;
	}

	/**
	 * 随机获取一个战斗对象
	 * 
	 * @param list
	 *            索引集合
	 * @param notIndex
	 *            不是指定位置
	 * @return 战斗对象
	 */
	private BattleCharacter getRandom(List<Integer> list, int notIndex) {
		if (list.size() <= 0) {
			return null;
		}
		int index = Util.getRandom(list.size());
		BattleCharacter battleCharacter = all.get(list.get(index));
		if (battleCharacter == null || battleCharacter.isDead() || battleCharacter.isEscape() || battleCharacter.getIndex() == notIndex) {
			list.remove(index);
			return getRandom(list, notIndex);
		}
		return battleCharacter;
	}

	/**
	 * 随机对方获取战斗对象
	 * 
	 * @param direction
	 *            方位
	 * @return 战斗对象
	 */
	public BattleCharacter randomBattleCharacterOpposite(EDirection direction) {
		if (direction.equals(EDirection.right)) {
			List<Integer> list = new ArrayList<>(left.keySet());
			return getRandom(list);
		} else {
			List<Integer> list = new ArrayList<>(right.keySet());
			return getRandom(list);
		}
	}

	/**
	 * 随机对方获取战斗对象
	 * 
	 * @param direction
	 *            方位
	 * @param notIndex
	 *            不是指定位置
	 * @return 战斗对象
	 */
	public BattleCharacter randomBattleCharacterOpposite(EDirection direction, int notIndex) {
		if (direction.equals(EDirection.right)) {
			List<Integer> list = new ArrayList<>(left.keySet());
			return getRandom(list, notIndex);
		} else {
			List<Integer> list = new ArrayList<>(right.keySet());
			return getRandom(list, notIndex);
		}
	}

	/**
	 * 随机己方获取战斗对象
	 * 
	 * @param direction
	 *            方位
	 * @return 战斗对象
	 */
	public BattleCharacter randomBattleCharacter(EDirection direction) {
		if (direction.equals(EDirection.left)) {
			List<Integer> list = new ArrayList<>(left.keySet());
			return getRandom(list);
		} else {
			List<Integer> list = new ArrayList<>(right.keySet());
			return getRandom(list);
		}
	}

	/**
	 * 随机获取多个战斗对象
	 * 
	 * @param list
	 *            索引集合
	 * @param characters
	 *            战斗对象集合
	 * @param num
	 *            数量
	 */
	private void getRandom(List<Integer> list, List<BattleCharacter> characters, int num) {
		if (list.size() <= 0) {
			return;
		}
		int index = Util.getRandom(list.size());
		BattleCharacter battleCharacter = all.get(list.get(index));

		if (characters.contains(battleCharacter)) {
			list.remove(index);
			getRandom(list, characters, num);
		} else {
			if (battleCharacter != null && !battleCharacter.isDead() && !battleCharacter.isEscape()) {
				characters.add(battleCharacter);
			}

			if (characters.size() < num) {
				list.remove(index);
				getRandom(list, characters, num);
			}
		}
	}

	/**
	 * 随机获取多个对方被攻击对象
	 * 
	 * @param direction
	 *            方位
	 * @param num
	 *            数量
	 * @param characters
	 *            存储集合
	 */
	public void randomBattleCharacterOpposite(EDirection direction, int num, List<BattleCharacter> characters) {
		if (direction.equals(EDirection.right)) {
			List<Integer> list = new ArrayList<>(left.keySet());
			getRandom(list, characters, num);
		} else {
			List<Integer> list = new ArrayList<>(right.keySet());
			getRandom(list, characters, num);
		}
	}

	/**
	 * 随机获取多个己方被攻击对象
	 * 
	 * @param direction
	 *            方位
	 * @param num
	 *            数量
	 * @param characters
	 *            存储集合
	 */
	public void randomBattleCharacter(EDirection direction, int num, List<BattleCharacter> characters) {
		if (direction.equals(EDirection.left)) {
			List<Integer> list = new ArrayList<>(left.keySet());
			getRandom(list, characters, num);
		} else {
			List<Integer> list = new ArrayList<>(right.keySet());
			getRandom(list, characters, num);
		}
	}

	/**
	 * 获取一个对方战斗对象
	 * 
	 * @param index
	 *            索引
	 * @param direction
	 *            方位
	 * @return 战斗对象
	 */
	public BattleCharacter getOppositeBattleCharacter(Integer index, EDirection direction) {
		BattleCharacter character = null;

		if (direction.equals(EDirection.right)) {
			character = left.get(index);
		} else {
			character = right.get(index);
		}

		if (character == null || character.isDead() || character.isEscape()) {
			character = randomBattleCharacterOpposite(direction);
		}
		return character;
	}

	/**
	 * 获取多个对方战斗对象
	 * 
	 * @param index
	 *            索引
	 * @param direction
	 *            方位
	 * @param num
	 *            数量
	 * @return 战斗对象
	 */
	public List<BattleCharacter> getOppositeBattleCharacter(Integer index, EDirection direction, int num) {
		List<BattleCharacter> list = new ArrayList<BattleCharacter>();
		BattleCharacter character = null;

		if (direction.equals(EDirection.right)) {
			character = left.get(index);
		} else {
			character = right.get(index);
		}

		if (character != null && !character.isDead() && !character.isEscape()) {
			list.add(character);
			if (list.size() < num) {
				randomBattleCharacterOpposite(direction, num, list);
			}
		} else {
			randomBattleCharacterOpposite(direction, num, list);
		}

		return list;
	}

	/**
	 * 获取一个己方战斗对象
	 * 
	 * @param index
	 *            索引
	 * @param direction
	 *            方位
	 * @param revival
	 *            是否可以复活
	 * @return 战斗对象
	 */
	public BattleCharacter getBattleCharacter(Integer index, EDirection direction, boolean revival) {
		BattleCharacter character = null;

		if (direction.equals(EDirection.left)) {
			character = left.get(index);
		} else {
			character = right.get(index);
		}

		if (character == null || character.isEscape()) {
			character = randomBattleCharacter(direction);
		}

		// 死亡，不可以复活，可以复活但不是伙伴
		if (character.isDead() && (!revival || character.getCharacter() instanceof Partner)) {
			character = randomBattleCharacter(direction);
		}

		return character;
	}

	/**
	 * 获取多个己方战斗对象
	 * 
	 * @param index
	 *            索引
	 * @param direction
	 *            方位
	 * @param num
	 *            数量
	 * @return 战斗对象
	 */
	public List<BattleCharacter> getBattleCharacter(Integer index, EDirection direction, int num) {
		List<BattleCharacter> list = new ArrayList<BattleCharacter>();
		BattleCharacter character = null;

		if (direction.equals(EDirection.left)) {
			character = left.get(index);
		} else {
			character = right.get(index);
		}

		if (character != null && !character.isDead() && !character.isEscape()) {
			list.add(character);
			if (list.size() < num) {
				randomBattleCharacter(direction, num, list);
			}
		} else {
			randomBattleCharacter(direction, num, list);
		}

		return list;
	}

	public void removeBattleCharacter(BattleCharacter character) {
		all.remove(character.getIndex());
		if (character.getDirection().equals(EDirection.left)) {
			left.remove(character.getIndex());
		} else {
			right.remove(character.getIndex());
		}
	}

	/**
	 * 检查战斗队列，删除逃跑的人、伙伴和怪物，以及死亡的伙伴和怪物
	 */
	public void checkOrder() {
		Iterator<BattleCharacter> it = order.iterator();
		while (it.hasNext()) {
			BattleCharacter battleCharacter = it.next();
			if (battleCharacter.isEscape()) {
				it.remove();
				removeBattleCharacter(battleCharacter);
			} else if ((battleCharacter.isDead() && battleCharacter.getCharacter().getType() != 0)) {
				it.remove();
			}
		}
		Collections.sort(order, comparator);
	}

	/**
	 * 添加战斗队列
	 * 
	 * @param character
	 *            对象
	 */
	public void addCharacterInToOrder(BattleCharacter character) {
		order.add(character);
		Collections.sort(order, comparator);
	}

	/**
	 * 添加至战斗队列中指定位置
	 * 
	 * @param character
	 *            战斗对象
	 * @param index
	 *            位置
	 */
	public void putCharacterInToOrder(BattleCharacter character, int index) {
		if (index == -1) {
			order.add(character);
		} else {
			order.set(index, character);
		}
	}

	/**
	 * 获取战斗对象在战斗队列中的位置
	 * 
	 * @param character
	 *            战斗对象
	 * @return 位置
	 */
	public int getCharacterIndexInOrder(BattleCharacter character) {
		return order.indexOf(character);
	}

	private int note = 0;

	/**
	 * 获取下一个战斗对象
	 * 
	 * @return 战斗对象
	 */
	public BattleCharacter getNextICharacter() {
		if (order.size() > note && note >= 0) {
			return order.get(note++);
		} else {
			note = 0;
			return null;
		}
	}

	/**
	 * 战斗左队添加对象
	 * 
	 * @param index
	 *            索引
	 * @param character
	 *            对象
	 */
	public void addCharacterInToLeft(int index, BattleCharacter character) {
		left.put(index, character);
		all.put(index, character);
		if (character.getCharacter().getType() == 0) {
			roleToBattleCharacter.put(character.getCharacter().getId(), index);
		}
		addCharacterInToOrder(character);
	}

	/**
	 * 战斗右队添加对象
	 * 
	 * @param index
	 *            索引
	 * @param character
	 *            对象
	 */
	public void addCharacterInToRight(int index, BattleCharacter character) {
		right.put(index, character);
		all.put(index, character);
		if (character.getCharacter().getType() == 0) {
			roleToBattleCharacter.put(character.getCharacter().getId(), index);
		}
		addCharacterInToOrder(character);
	}

	public void putCharacterInToBattle(int index, BattleCharacter character, int orderIndex) {
		if (character.getDirection().equals(EDirection.left)) {
			left.put(index, character);
		} else {
			right.put(index, character);
		}

		all.put(index, character);
		putCharacterInToOrder(character, orderIndex);
	}

	/**
	 * 获取战斗对象
	 * 
	 * @param index
	 *            索引
	 * @return 战斗对象
	 */
	public BattleCharacter getBattleCharacter(int index) {
		if (all.containsKey(index)) {
			return all.get(index);
		}
		return null;
	}

	/**
	 * 检查回合数
	 * 
	 * @param turn
	 *            回合数
	 * @return 是否匹配
	 */
	public boolean checkTurn(int turn) {
		if (this.turn != turn) {
			return false;
		}
		return true;
	}

	/**
	 * @return the battleId
	 */
	public long getBattleId() {

		return battleId;
	}

	/**
	 * @param battleId
	 *            the battleId to set
	 */
	public void setBattleId(long battleId) {
		this.battleId = battleId;
	}

	/**
	 * @return the turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * @param turn
	 *            the turn to set
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}

	/**
	 * @return the left
	 */
	public Map<Integer, BattleCharacter> getLeft() {
		return left;
	}

	/**
	 * @return the right
	 */
	public Map<Integer, BattleCharacter> getRight() {
		return right;
	}

	/**
	 * @return the waitingTime
	 */
	public long getWaitingTime() {
		return waitingTime;
	}

	/**
	 * @param waitingTime
	 *            the waitingTime to set
	 */
	public void setWaitingTime(long waitingTime) {
		this.waitingTime = waitingTime;
	}

	/**
	 * @return the result
	 */
	public EBattleReuslt getResult() {
		return winner;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(EBattleReuslt result) {
		this.winner = result;
	}

	/**
	 * @return the battleAminInfos
	 */
	public List<BattleAminInfo> getBattleAminInfos() {
		return battleAminInfos;
	}

	/**
	 * @return the buffInfos
	 */
	public Map<EBattleBuffType, ArrayList<BufferAminInfo>> getBuffInfos() {
		return buffInfos;
	}

	/**
	 * @return the all
	 */
	public Map<Integer, BattleCharacter> getAll() {
		return all;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the taskId
	 */
	public int getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the baseMap
	 */
	public BaseMap getBaseMap() {
		return baseMap;
	}

	/**
	 * @param baseMap
	 *            the baseMap to set
	 */
	public void setBaseMap(BaseMap baseMap) {
		this.baseMap = baseMap;
	}

	public int getTrialsId() {
		return trialsId;
	}

	public void setTrialsId(int trialsId) {
		this.trialsId = trialsId;
	}

	/**
	 * @return the monsterOnMap
	 */
	public MonsterOnMap getMonsterOnMap() {
		return monsterOnMap;
	}

	/**
	 * @param monsterOnMap
	 *            the monsterOnMap to set
	 */
	public void setMonsterOnMap(MonsterOnMap monsterOnMap) {
		this.monsterOnMap = monsterOnMap;
	}

	/**
	 * @return the roleToBattleCharacter
	 */
	public Map<Long, Integer> getRoleToBattleCharacter() {
		return roleToBattleCharacter;
	}

	/**
	 * @return the leftNum
	 */
	public int getLeftNum() {
		return leftNum;
	}

	/**
	 * @param leftNum
	 *            the leftNum to set
	 */
	public void setLeftNum(int leftNum) {
		this.leftNum = leftNum;
	}

	/**
	 * @return the rightNum
	 */
	public int getRightNum() {
		return rightNum;
	}

	/**
	 * @param rightNum
	 *            the rightNum to set
	 */
	public void setRightNum(int rightNum) {
		this.rightNum = rightNum;
	}

	public int getCurrentFloorId() {
		return currentFloorId;
	}

	public void setCurrentFloorId(int currentFloorId) {
		this.currentFloorId = currentFloorId;
	}

	/**
	 * @return the battleType
	 */
	public int getBattleType() {
		return battleType;
	}

	/**
	 * @param battleType
	 *            the battleType to set
	 */
	public void setBattleType(int battleType) {
		this.battleType = battleType;
	}

	/**
	 * @return the isAuto
	 */
	public boolean isAuto() {
		return isAuto;
	}

	/**
	 * @param isAuto
	 *            the isAuto to set
	 */
	public void setAuto(boolean isAuto) {
		this.isAuto = isAuto;
	}

	/**
	 * @return the awardType
	 */
	public int getAwardType() {
		return awardType;
	}

	/**
	 * @param awardType
	 *            the awardType to set
	 */
	public void setAwardType(int awardType) {
		this.awardType = awardType;
	}

	/**
	 * @return the leftLeader
	 */
	public Role getLeftLeader() {
		return leftLeader;
	}

	/**
	 * @param leftLeader
	 *            the leftLeader to set
	 */
	public void setLeftLeader(Role leftLeader) {
		this.leftLeader = leftLeader;
	}

	/**
	 * @return the rightLeader
	 */
	public Role getRightLeader() {
		return rightLeader;
	}

	/**
	 * @param rightLeader
	 *            the rightLeader to set
	 */
	public void setRightLeader(Role rightLeader) {
		this.rightLeader = rightLeader;
	}

	/**
	 * @return the monsterGroup
	 */
	public MonsterGroup getMonsterGroup() {
		return monsterGroup;
	}

	/**
	 * @param monsterGroup
	 *            the monsterGroup to set
	 */
	public void setMonsterGroup(MonsterGroup monsterGroup) {
		this.monsterGroup = monsterGroup;
	}

}
