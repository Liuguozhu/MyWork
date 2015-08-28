package iyunu.NewTLOL.model.task;

import iyunu.NewTLOL.json.TaskJson;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.task.instance.BattleOnMapTask;
import iyunu.NewTLOL.model.task.instance.CollectionTask;
import iyunu.NewTLOL.model.task.instance.GhostTask;
import iyunu.NewTLOL.model.task.instance.KillTask;
import iyunu.NewTLOL.model.task.instance.NPCFightTask;
import iyunu.NewTLOL.model.task.instance.PickTask;
import iyunu.NewTLOL.model.task.instance.YingxiongtieTask;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.json.JsonTool;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public abstract class BaseTask {

	protected Role role; // 任务拥有者
	protected ETaskState state; // 任务状态
	protected int id; // 任务编号
	protected String name; // 任务名称
	protected int precondition; // 前置任务
	protected int next; // 后置任务
	protected int level; // 需求等级
	protected int map; // 所在地图编号
	protected ETaskCategory category; // 任务种类
	protected ETaskType type; // 任务类型
	protected int goal; // 任务目标
	protected int sender; // 任务发布者
	protected int receiver; // 任务接收者
	protected int exp; // 奖励经验
	protected int gold; // 奖励绑银
	protected int coin; // 奖励银两
	protected int skill; // 奖励技能点
	protected ArrayList<TaskAwardPartner> partnerList = new ArrayList<TaskAwardPartner>(); // 任务奖励伙伴集合
	protected EOpen reward = EOpen.one; // 获得物品奖励类型
	protected ArrayList<TaskAwardItem> itemList = new ArrayList<TaskAwardItem>(); // 任务奖励物品集合
	protected int arriveMap; // 目标地图
	protected int arriveX; // 目标x坐标
	protected int arriveY; // 目标y坐标
	protected int zone; // 任务区间
	protected long timeOut = -1l; // 到期时间（-1为无限期）
	
	public String awardString(){
		SerializeWriter itemOut = new SerializeWriter();
		JSONSerializer itemSserializer = new JSONSerializer(itemOut);
		itemSserializer.write(this.itemList);
		SerializeWriter partnerOut = new SerializeWriter();
		JSONSerializer partnerSerializer = new JSONSerializer(partnerOut);
		partnerSerializer.write(this.partnerList);
		return itemSserializer.toString() + "#" + partnerSerializer.toString();
	}

	public abstract BaseTask copy();

	public String encode() {
		return this.id + "#" + this.type.ordinal() + "#" + this.state.ordinal() + "#" + this.timeOut + "#" + this.receiver + "#" + awardString();
	}

	public abstract void finishTask();

	@SuppressWarnings("unchecked")
	public static void init(BaseTask newTask, BaseTask oldTask) {
		newTask.setState(oldTask.getState());
		newTask.setId(oldTask.getId());
		newTask.setName(oldTask.getName());
		newTask.setPrecondition(oldTask.getPrecondition());
		newTask.setNext(oldTask.getNext());
		newTask.setLevel(oldTask.getLevel());
		newTask.setMap(oldTask.getMap());
		newTask.setCategory(oldTask.getCategory());
		newTask.setType(oldTask.getType());
		newTask.setGoal(oldTask.getGoal());
		newTask.setSender(oldTask.getSender());
		newTask.setReceiver(oldTask.getReceiver());
		newTask.setExp(oldTask.getExp());
		newTask.setGold(oldTask.getGold());
		newTask.setSkill(oldTask.getSkill());
		newTask.setArriveMap(oldTask.getArriveMap());
		newTask.setArriveX(oldTask.getArriveX());
		newTask.setArriveY(oldTask.getArriveY());
		
		newTask.setReward(oldTask.getReward());
		newTask.setZone(oldTask.getZone());
		
		newTask.setItemList((ArrayList<TaskAwardItem>) oldTask.getItemList().clone());
		newTask.setPartnerList((ArrayList<TaskAwardPartner>) oldTask.getPartnerList().clone());
	}

	/**
	 * 解码成格子对象
	 * 
	 * @param str
	 *            字符串
	 * @return 格子对象
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static HashMap<Integer, BaseTask> decode(String str) {
		HashMap<Integer, BaseTask> tasks = new HashMap<Integer, BaseTask>();
		if (str != null && !"".equals(str)) {
			String[] strs = str.split("!");
			for (String string : strs) {
				String[] taskStrs = string.split("#");

				try {
					ETaskType eTaskType = ETaskType.values()[Translate.stringToInt(taskStrs[1])];
					BaseTask baseTask = null;
					switch (eTaskType) {
					case killTask: // 杀怪
						KillTask killTask = (KillTask) TaskJson.instance().getTask(Translate.stringToInt(taskStrs[0]));
						killTask.setState(ETaskState.getETaskStateByIndex(Translate.stringToInt(taskStrs[2])));

						HashMap<Long, Integer> monsters = JsonTool.decode(taskStrs[3], new TypeReference<HashMap<Long, Integer>>() {
						});
						killTask.setMonsters(monsters);

						HashMap<Long, Integer> finish = JsonTool.decode(taskStrs[4], new TypeReference<HashMap<Long, Integer>>() {
						});
						killTask.setFinish(finish);

						if (taskStrs.length > 5) {
							killTask.setTimeOut(Translate.stringToLong(taskStrs[5]));
						}

						if (taskStrs.length > 6) {
							killTask.setReceiver(Translate.stringToInt(taskStrs[6]));
						}

						if(taskStrs.length > 8){
							ArrayList<TaskAwardItem> itemString = JsonTool.decode(taskStrs[7], new TypeReference<ArrayList<TaskAwardItem>>() {
							});
							killTask.setItemList(itemString);

							ArrayList<TaskAwardPartner> partnerString = JsonTool.decode(taskStrs[8], new TypeReference<ArrayList<TaskAwardPartner>>() {
							});
							killTask.setPartnerList(partnerString);
						}
						
						baseTask = killTask;
						break;
					case npcFightTask: // NPC战斗
						NPCFightTask npcFightTask = (NPCFightTask) TaskJson.instance().getTask(Translate.stringToInt(taskStrs[0]));
						npcFightTask.setState(ETaskState.getETaskStateByIndex(Translate.stringToInt(taskStrs[2])));
						npcFightTask.setNpcId(Translate.stringToInt(taskStrs[3]));

						if (taskStrs.length > 4) {
							npcFightTask.setTimeOut(Translate.stringToLong(taskStrs[4]));
						}

						if (taskStrs.length > 5) {
							npcFightTask.setReceiver(Translate.stringToInt(taskStrs[5]));
						}

						if(taskStrs.length > 7){
							ArrayList<TaskAwardItem> itemString = JsonTool.decode(taskStrs[6], new TypeReference<ArrayList<TaskAwardItem>>() {
							});
							npcFightTask.setItemList(itemString);

							ArrayList<TaskAwardPartner> partnerString = JsonTool.decode(taskStrs[7], new TypeReference<ArrayList<TaskAwardPartner>>() {
							});
							npcFightTask.setPartnerList(partnerString);
						}
						
						baseTask = npcFightTask;
						break;
					case collectionTask: // 收集
						CollectionTask collectionTask = (CollectionTask) TaskJson.instance().getTask(Translate.stringToInt(taskStrs[0]));
						collectionTask.setState(ETaskState.getETaskStateByIndex(Translate.stringToInt(taskStrs[2])));
						HashMap<Integer, Integer> needs = JsonTool.decode(taskStrs[3], new TypeReference<HashMap<Integer, Integer>>() {
						});
						collectionTask.setNeeds(needs);

						HashMap<Integer, Integer> collectionTaskFinish = JsonTool.decode(taskStrs[4], new TypeReference<HashMap<Integer, Integer>>() {
						});
						collectionTask.setFinish(collectionTaskFinish);

						if (taskStrs.length > 5) {
							collectionTask.setTimeOut(Translate.stringToLong(taskStrs[5]));
						}

						if (taskStrs.length > 6) {
							collectionTask.setReceiver(Translate.stringToInt(taskStrs[6]));
						}

						if (taskStrs.length > 8) {
							collectionTask.setChuanshuo(Translate.stringToBoolean(taskStrs[7]));
							collectionTask.setGet(Translate.stringToBoolean(taskStrs[8]));
						}

						if(taskStrs.length > 10){
							ArrayList<TaskAwardItem> itemString = JsonTool.decode(taskStrs[9], new TypeReference<ArrayList<TaskAwardItem>>() {
							});
							collectionTask.setItemList(itemString);

							ArrayList<TaskAwardPartner> partnerString = JsonTool.decode(taskStrs[10], new TypeReference<ArrayList<TaskAwardPartner>>() {
							});
							collectionTask.setPartnerList(partnerString);
						}
						
						baseTask = collectionTask;
						break;
					case pickTask: // 采集
						PickTask pickTask = (PickTask) TaskJson.instance().getTask(Translate.stringToInt(taskStrs[0]));
						pickTask.setState(ETaskState.getETaskStateByIndex(Translate.stringToInt(taskStrs[2])));
						HashMap<Integer, Integer> pickTaskNeeds = JsonTool.decode(taskStrs[3], new TypeReference<HashMap<Integer, Integer>>() {
						});
						pickTask.setNeeds(pickTaskNeeds);

						HashMap<Integer, Integer> pickTaskFinish = JsonTool.decode(taskStrs[4], new TypeReference<HashMap<Integer, Integer>>() {
						});
						pickTask.setFinish(pickTaskFinish);

						if (taskStrs.length > 5) {
							pickTask.setTimeOut(Translate.stringToLong(taskStrs[5]));
						}

						if (taskStrs.length > 6) {
							pickTask.setReceiver(Translate.stringToInt(taskStrs[6]));
						}

						if(taskStrs.length > 8){
							ArrayList<TaskAwardItem> itemString = JsonTool.decode(taskStrs[7], new TypeReference<ArrayList<TaskAwardItem>>() {
							});
							pickTask.setItemList(itemString);

							ArrayList<TaskAwardPartner> partnerString = JsonTool.decode(taskStrs[8], new TypeReference<ArrayList<TaskAwardPartner>>() {
							});
							pickTask.setPartnerList(partnerString);
						}
						
						baseTask = pickTask;
						break;
					case battleOnMapTask: // 地图上杀指定怪物
						BattleOnMapTask battleOnMapTask = (BattleOnMapTask) TaskJson.instance().getTask(Translate.stringToInt(taskStrs[0]));
						battleOnMapTask.setState(ETaskState.getETaskStateByIndex(Translate.stringToInt(taskStrs[2])));
						battleOnMapTask.setUid(Translate.stringToLong(taskStrs[3]));
						battleOnMapTask.setMonsterGroup(Translate.stringToInt(taskStrs[4]));

						if (taskStrs.length > 5) {
							battleOnMapTask.setTimeOut(Translate.stringToLong(taskStrs[5]));
						}

						if (taskStrs.length > 6) {
							battleOnMapTask.setReceiver(Translate.stringToInt(taskStrs[6]));
						}
						
						if(taskStrs.length > 8){
							ArrayList<TaskAwardItem> itemString = JsonTool.decode(taskStrs[7], new TypeReference<ArrayList<TaskAwardItem>>() {
							});
							battleOnMapTask.setItemList(itemString);

							ArrayList<TaskAwardPartner> partnerString = JsonTool.decode(taskStrs[8], new TypeReference<ArrayList<TaskAwardPartner>>() {
							});
							battleOnMapTask.setPartnerList(partnerString);
						}

						baseTask = battleOnMapTask;
						break;
					case ghostTask: // 悬赏任务，江湖追杀令
						GhostTask ghostTask = (GhostTask) TaskJson.instance().getTask(Translate.stringToInt(taskStrs[0]));
						ghostTask.setState(ETaskState.getETaskStateByIndex(Translate.stringToInt(taskStrs[2])));
						ghostTask.setUid(Translate.stringToLong(taskStrs[3]));
						ghostTask.setMonsterGroup(Translate.stringToInt(taskStrs[4]));

						if (taskStrs.length > 5) {
							ghostTask.setTimeOut(Translate.stringToLong(taskStrs[5]));
						}

						if (taskStrs.length > 6) {
							ghostTask.setReceiver(Translate.stringToInt(taskStrs[6]));
						}
						
						if(taskStrs.length > 8){
							ArrayList<TaskAwardItem> itemString = JsonTool.decode(taskStrs[7], new TypeReference<ArrayList<TaskAwardItem>>() {
							});
							ghostTask.setItemList(itemString);

							ArrayList<TaskAwardPartner> partnerString = JsonTool.decode(taskStrs[8], new TypeReference<ArrayList<TaskAwardPartner>>() {
							});
							ghostTask.setPartnerList(partnerString);
						}

						baseTask = ghostTask;
						break;
					case yingxiongtieTask: // 英雄贴
						YingxiongtieTask yingxiongtieTask = (YingxiongtieTask) TaskJson.instance().getTask(Translate.stringToInt(taskStrs[0]));
						yingxiongtieTask.setState(ETaskState.getETaskStateByIndex(Translate.stringToInt(taskStrs[2])));
						yingxiongtieTask.setFinishNum(Translate.stringToInt(taskStrs[3]));

						if (taskStrs.length > 4) {
							yingxiongtieTask.setTimeOut(Translate.stringToLong(taskStrs[4]));
						}

						if (taskStrs.length > 5) {
							yingxiongtieTask.setReceiver(Translate.stringToInt(taskStrs[5]));
						}
						
						if(taskStrs.length > 7){
							ArrayList<TaskAwardItem> itemString = JsonTool.decode(taskStrs[6], new TypeReference<ArrayList<TaskAwardItem>>() {
							});
							yingxiongtieTask.setItemList(itemString);

							ArrayList<TaskAwardPartner> partnerString = JsonTool.decode(taskStrs[7], new TypeReference<ArrayList<TaskAwardPartner>>() {
							});
							yingxiongtieTask.setPartnerList(partnerString);
						}

						baseTask = yingxiongtieTask;
						break;
					default:
						baseTask = TaskJson.instance().getTask(Translate.stringToInt(taskStrs[0]));
						baseTask.setState(ETaskState.getETaskStateByIndex(Translate.stringToInt(taskStrs[2])));

						if (taskStrs.length > 3) {
							baseTask.setTimeOut(Translate.stringToLong(taskStrs[3]));
						}

						if (taskStrs.length > 4) {
							baseTask.setReceiver(Translate.stringToInt(taskStrs[4]));
						}

						if(taskStrs.length > 6){
							ArrayList<TaskAwardItem> itemString = JsonTool.decode(taskStrs[5], new TypeReference<ArrayList<TaskAwardItem>>() {
							});
							baseTask.setItemList(itemString);

							ArrayList<TaskAwardPartner> partnerString = JsonTool.decode(taskStrs[6], new TypeReference<ArrayList<TaskAwardPartner>>() {
							});
							baseTask.setPartnerList(partnerString);
						}
						
						break;
					}
					tasks.put(baseTask.getId(), baseTask);
				} catch (Exception e) {
					LogManager.exception("任务解析异常：" + taskStrs);
					continue;
				}

			}
		}
		return tasks;
	}

	/**
	 * @return the state
	 */
	public ETaskState getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(ETaskState state) {
		this.state = state;
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
	 * @return the items
	 */
	// public Map<Integer, Integer> getItems() {
	// return items;
	// }

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

	public ArrayList<TaskAwardPartner> getPartnerList() {
		return partnerList;
	}

	public void setPartnerList(ArrayList<TaskAwardPartner> partnerList) {
		this.partnerList = partnerList;
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
	 * @return the itemList
	 */
	public ArrayList<TaskAwardItem> getItemList() {
		return itemList;
	}

	/**
	 * @param itemList
	 *            the itemList to set
	 */
	public void setItemList(ArrayList<TaskAwardItem> itemList) {
		this.itemList = itemList;
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

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
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
	 * @return the timeOut
	 */
	public long getTimeOut() {
		return timeOut;
	}

	/**
	 * @param timeOut
	 *            the timeOut to set
	 */
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

}
