package iyunu.NewTLOL.model.task.res;

import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskCategory;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.task.ETaskType;
import iyunu.NewTLOL.model.task.TaskAwardItem;
import iyunu.NewTLOL.model.task.TaskAwardPartner;
import iyunu.NewTLOL.model.task.instance.BattleOnMapTask;
import iyunu.NewTLOL.model.task.instance.CollectionTask;
import iyunu.NewTLOL.model.task.instance.DialogueTask;
import iyunu.NewTLOL.model.task.instance.GhostTask;
import iyunu.NewTLOL.model.task.instance.JoinGuildTask;
import iyunu.NewTLOL.model.task.instance.KillTask;
import iyunu.NewTLOL.model.task.instance.NPCFightTask;
import iyunu.NewTLOL.model.task.instance.PickTask;
import iyunu.NewTLOL.model.task.instance.YingxiongtieTask;
import iyunu.NewTLOL.util.Translate;

/**
 * 资源类 任务
 * 
 * @author SunHonglei
 * 
 */
public class TaskRes {

	private int id; // 任务编号
	private String name; // 任务名称
	private int precondition; // 前置任务
	private int next; // 后置任务
	private int level; // 需求等级
	private int map; // 所在地图编号
	private ETaskCategory category; // 任务种类
	private ETaskType type; // 任务类型
	private int goal; // 任务目标
	private String desc; // 任务描述
	private String need; // 需要任务物品
	private int sender; // 任务发布NPC
	private int receiver; // 任务接收NPC
	private boolean giveUp; // 是否可以放弃
	private int exp; // 奖励经验
	private int gold; // 奖励银两
	private EOpen reward; // 获得物品奖励类型
	private String item; // 奖励物品及数量
	private int skill; // 奖励技能点
	private String partner; // 伙伴掉落
	private String getDesc; // 接任务对话
	private String finishDesc; // 交任务对话
	private int arriveMap; // 目标地图
	private int arriveX; // 目标x坐标
	private int arriveY; // 目标y坐标
	private String goalDesc; // 目标描述
	private int zone; // 任务区间

	public BaseTask toTask() throws Exception {
		BaseTask task = null;

		switch (type) {
		case dialogueTask:
			task = new DialogueTask();
			break;
		case killTask:
			KillTask killTask = new KillTask();

			if (need != null && !need.equals("")) {
				String[] strs = need.split(";");
				for (String string : strs) {
					if (string != null && !string.equals("")) {
						String[] monsters = string.split(":");
						killTask.getMonsters().put(Translate.stringToLong(monsters[0]), Translate.stringToInt(monsters[1]));
					}
				}
			}
			task = killTask;
			break;
		case pickTask:
			PickTask pickTask = new PickTask();
			if (need != null && !need.equals("")) {
				String[] strs = need.split(";");
				for (String string : strs) {
					if (string != null && !string.equals("")) {
						String[] items = string.split(":");
						pickTask.getNeeds().put(Translate.stringToInt(items[0]), Translate.stringToInt(items[1]));
					}
				}
			}
			task = pickTask;
			break;
		case collectionTask:
			CollectionTask collectionTask = new CollectionTask();

			if (need != null && !need.equals("")) {
				String[] strs = need.split(";");
				for (String string : strs) {
					if (string != null && !string.equals("")) {
						String[] items = string.split(":");
						collectionTask.getNeeds().put(Translate.stringToInt(items[0]), Translate.stringToInt(items[1]));
					}
				}
			}
			task = collectionTask;
			break;
		case joinGuildTask:
			task = new JoinGuildTask();
			break;
		case npcFightTask:
			NPCFightTask npcFightTask = new NPCFightTask();
			npcFightTask.setNpcId(Translate.stringToInt(need));
			task = npcFightTask;
			break;
		case battleOnMapTask:
			BattleOnMapTask battleOnMapTask = new BattleOnMapTask();
			battleOnMapTask.setMonsterGroup(Translate.stringToInt(need));
			task = battleOnMapTask;
			break;
		case ghostTask:
			GhostTask ghostTask = new GhostTask();
			ghostTask.setMonsterGroup(Translate.stringToInt(need));
			task = ghostTask;
			break;
		case yingxiongtieTask:
			YingxiongtieTask yingxiongtieTask = new YingxiongtieTask();
			yingxiongtieTask.setNeed(Translate.stringToInt(need));
			task = yingxiongtieTask;
			break;
		default:
			task = type.getObject().newInstance();
			break;
		}

		task.setState(ETaskState.none);
		task.setId(id);
		task.setName(name);
		task.setType(type);
		task.setCategory(category);
		task.setPrecondition(precondition);
		task.setNext(next);
		task.setLevel(level);
		task.setMap(map);
		task.setGoal(goal);
		task.setSender(sender);
		task.setReceiver(receiver);
		task.setExp(exp);
		task.setSkill(skill);
		task.setGold(gold);
		if (partner != null && !partner.equals("")) {
			String[] strs = partner.split(";");
			for (String string : strs) {
				if (string != null && !string.equals("")) {
					String[] partners = string.split(":");
					task.getPartnerList().add(new TaskAwardPartner(Translate.stringToInt(partners[0]), Translate.stringToInt(partners[1]), Translate.stringToInt(partners[2]), Translate.stringToInt(partners[3])));
				}
			}
		}
		task.setArriveMap(arriveMap);
		task.setArriveX(arriveX);
		task.setArriveY(arriveY);
		task.setReward(reward);
		task.setZone(zone);
		if (item != null && !item.equals("")) {
			String[] strs = item.split(";");
			for (String string : strs) {
				if (string != null && !string.equals("")) {
					String[] items = string.split(":");
					task.getItemList().add(new TaskAwardItem(Translate.stringToInt(items[0]), Translate.stringToInt(items[1]), Translate.stringToInt(items[2]), Translate.stringToInt(items[3])));
				}
			}
		}

		return task;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the precondition
	 */
	public int getPrecondition() {
		return precondition;
	}

	/**
	 * @param precondition
	 *            the precondition to set
	 */
	public void setPrecondition(int precondition) {
		this.precondition = precondition;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the map
	 */
	public int getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(int map) {
		this.map = map;
	}

	/**
	 * @return the type
	 */
	public ETaskType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(ETaskType type) {
		this.type = type;
	}

	/**
	 * @return the goal
	 */
	public int getGoal() {
		return goal;
	}

	/**
	 * @param goal
	 *            the goal to set
	 */
	public void setGoal(int goal) {
		this.goal = goal;
	}

	/**
	 * @return the need
	 */
	public String getNeed() {
		return need;
	}

	/**
	 * @param need
	 *            the need to set
	 */
	public void setNeed(String need) {
		this.need = need;
	}

	/**
	 * @return the sender
	 */
	public int getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(int sender) {
		this.sender = sender;
	}

	/**
	 * @return the receiver
	 */
	public int getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver
	 *            the receiver to set
	 */
	public void setReceiver(int receiver) {
		this.receiver = receiver;
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
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the giveUp
	 */
	public boolean isGiveUp() {
		return giveUp;
	}

	/**
	 * @param giveUp
	 *            the giveUp to set
	 */
	public void setGiveUp(boolean giveUp) {
		this.giveUp = giveUp;
	}

	/**
	 * @return the category
	 */
	public ETaskCategory getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(ETaskCategory category) {
		this.category = category;
	}

	/**
	 * @return the getDesc
	 */
	public String getGetDesc() {
		return getDesc;
	}

	/**
	 * @param getDesc
	 *            the getDesc to set
	 */
	public void setGetDesc(String getDesc) {
		this.getDesc = getDesc;
	}

	/**
	 * @return the finishDesc
	 */
	public String getFinishDesc() {
		return finishDesc;
	}

	/**
	 * @param finishDesc
	 *            the finishDesc to set
	 */
	public void setFinishDesc(String finishDesc) {
		this.finishDesc = finishDesc;
	}

	/**
	 * @return the next
	 */
	public int getNext() {
		return next;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(int next) {
		this.next = next;
	}

	/**
	 * @return the skill
	 */
	public int getSkill() {
		return skill;
	}

	/**
	 * @param skill
	 *            the skill to set
	 */
	public void setSkill(int skill) {
		this.skill = skill;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	/**
	 * @return the arriveMap
	 */
	public int getArriveMap() {
		return arriveMap;
	}

	/**
	 * @param arriveMap
	 *            the arriveMap to set
	 */
	public void setArriveMap(int arriveMap) {
		this.arriveMap = arriveMap;
	}

	/**
	 * @return the arriveX
	 */
	public int getArriveX() {
		return arriveX;
	}

	/**
	 * @param arriveX
	 *            the arriveX to set
	 */
	public void setArriveX(int arriveX) {
		this.arriveX = arriveX;
	}

	/**
	 * @return the arriveY
	 */
	public int getArriveY() {
		return arriveY;
	}

	/**
	 * @param arriveY
	 *            the arriveY to set
	 */
	public void setArriveY(int arriveY) {
		this.arriveY = arriveY;
	}

	/**
	 * @return the goalDesc
	 */
	public String getGoalDesc() {
		return goalDesc;
	}

	/**
	 * @param goalDesc
	 *            the goalDesc to set
	 */
	public void setGoalDesc(String goalDesc) {
		this.goalDesc = goalDesc;
	}

	/**
	 * @return the reward
	 */
	public EOpen getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(EOpen reward) {
		this.reward = reward;
	}

	/**
	 * @return the zone
	 */
	public int getZone() {
		return zone;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(int zone) {
		this.zone = zone;
	}

}
